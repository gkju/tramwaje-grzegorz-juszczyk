package Simulator;

import EventQueue.EventQueue;
import EventQueue.Vector;
import Events.*;
import Logging.ConsoleLogger;
import Main.Losowanie;
import Transit.*;

import java.util.Date;
import java.util.Scanner;

public class Simulator {
    private int simulationDuration = 0;
    private int stopCapacity = 0;
    private int tramCapacity = 0;
    private int stopCount = 0;
    private Stop[] stops;
    private int passengerCount = 0;
    private int lineCount = 0;
    private TramLine[] tramLines;
    private AverageAggregator averageWaitTime = new AverageAggregator();
    private int totalTrips = 0;
    private Passenger[] passengers;
    private ConsoleLogger logger = new ConsoleLogger();

    public Simulator() {
    }

    // TODO: Zastąpić hash mapą, kiedy pojawią się kolekcje na wykładzie.
    public Stop findStop(String name) {
        for (Stop stop : stops) {
            if (stop.getName().equals(name)) {
                return stop;
            }
        }
        return null;
    }

    public void readFromStdio() {
        Scanner sc = new Scanner(System.in);
        simulationDuration = sc.nextInt();
        stopCapacity = sc.nextInt();
        stopCount = sc.nextInt();
        stops = new Stop[stopCount];
        for (int i = 0; i < stopCount; i++) {
            stops[i] = new Stop(sc.next(), stopCapacity);
        }
        passengerCount = sc.nextInt();
        tramCapacity = sc.nextInt();
        lineCount = sc.nextInt();
        tramLines = new TramLine[lineCount];
        int lastCreatedTramNo = 0;
        for (int i = 0; i < lineCount; i++) {
            int tramCount = sc.nextInt();
            int length = sc.nextInt();
            Stop[] lineStops = new Stop[length];
            Integer[] timeBetweenStops = new Integer[length];
            for (int j = 0; j < length; j++) {
                String name = sc.next();
                int timeBetweenStop = sc.nextInt();
                lineStops[j] = findStop(name);
                if(lineStops[j] == null) {
                    throw new IllegalStateException("Invalid stop name.");
                }
                timeBetweenStops[j] = timeBetweenStop;
            }

            TramLine tramLine = new TramLine(i, lineStops, timeBetweenStops);
            Tram[] trams = new Tram[tramCount];
            for (int j = 0; j < tramCount; j++) {
                trams[j] = new Tram(++lastCreatedTramNo, tramLine, tramCapacity, j % 2 == 0 ? Direction.CLOCKWISE : Direction.COUNTERCLOCKWISE);
            }
            tramLine.setTrams(trams);

            tramLines[i] = tramLine;
        }

        passengers = new Passenger[passengerCount];

        for (int i = 0; i < passengerCount; ++i) {
            passengers[i] = new Passenger(stops[Losowanie.losuj(0, stops.length - 1)], "Pasażer numer " + i);
        }
    }

    public AverageAggregator getAverageWaitTime() {
        return averageWaitTime;
    }

    public void simulate() {
        logger.logSimulationData(this);
        for (int i = 0; i < simulationDuration; i++) {
            simulateDay(i);
        }
        logger.logFinalStats(this);
    }

    public void simulateDay(int day) {
        EventQueue eventQueue = new EventQueue();
        for (TramLine tramLine : tramLines) {
            tramLine.startSimulation(eventQueue, day);
        }

        for (int i = 0; i < passengerCount; ++i) {
            passengers[i].handleNewDay(eventQueue, day);
        }

        Event lastEvent = null;
        while(eventQueue.size() > 0) {
            Event event = eventQueue.popNext();
            lastEvent = event;

            if (event instanceof TerminalStopArrivalEvent) {
                handleTerminalStopArrival(eventQueue, (TerminalStopArrivalEvent) event, day);
            } else if (event instanceof HalfwayStopArrivalEvent) {
                handleHalfwayStopArrival(eventQueue, (HalfwayStopArrivalEvent) event, day);
            } else if (event instanceof StopArrivalEvent) {
                handleStopArrival((StopArrivalEvent) event);
            } else if (event instanceof PassengerArrivalEvent) {
                handlePassengerArrival((PassengerArrivalEvent) event);
            }
        }

        for(var stop : stops) {
            stop.clearPassengers(logger, lastEvent);
        }
    }

    private void handlePassengerArrival(PassengerArrivalEvent event) {
        var stop = event.getStop();
        var passenger = event.getPassenger();

        stop.addPassenger(passenger, event.getDate());
    }

    private void dumpPassengers(StopArrivalEvent event) {
        var timeArrived = event.getDate();
        var tram = event.getTram();
        var stop = event.getStop();
        Vector<Passenger> passengersWhoLeft = new Vector<>(Passenger[].class);
        preDumpPassengersAtStop(tram, stop, passengersWhoLeft);
        finalizeDumpingPassengers(event, passengersWhoLeft, stop);
    }

    private void handleHalfwayStopArrival(EventQueue eventQueue, HalfwayStopArrivalEvent event, int day) {
        logger.printArrival(event);
        dumpPassengers(event);
    }

    private void handleTerminalStopArrival(EventQueue queue, TerminalStopArrivalEvent event, int day) {
        logger.printArrival(event);
        dumpPassengers(event);
        var timeArrived = event.getDate();
        var tram = event.getTram();
        var timeToLeave = new Date(timeArrived.getTime() + tram.getLine().getTimeAtTerminalStop() * 60 * 1000);
        if(timeToLeave.getHours() == 23 || timeToLeave.getDay() != day) {
            // TODO: wykopywanie wszystkich na pętle
            tram.clearPassengers(logger, event);
            return;
        }

        tram.generateEvents(queue, timeToLeave);
    }

    private void handleStopArrival(StopArrivalEvent event) {
        logger.printArrival(event);
        var tram = event.getTram();
        var stop = event.getStop();
        Vector<Passenger> passengersWhoLeft = new Vector<>(Passenger[].class);
        preDumpPassengersAtStop(tram, stop, passengersWhoLeft);

        while (!tram.isFull() && !stop.isEmpty()) {
            var newPassenger = stop.popEldestPassenger();
            averageWaitTime.add(((double) (event.getDate().getTime() - newPassenger.getStartedWaitingAt().getTime())) / (60.0 * 1000.0));
            tram.insertPassengerAndChooseStop(newPassenger.getPassenger(), event.getDate(), event);
            ++totalTrips;
            logger.logPassengerTramBoarding(newPassenger.getPassenger(), tram, event);
        }

        finalizeDumpingPassengers(event, passengersWhoLeft, stop);
    }

    private void finalizeDumpingPassengers(StopArrivalEvent event, Vector<Passenger> passengersWhoLeft, Stop stop) {
        for (Passenger passenger : passengersWhoLeft.toArray()) {
            logger.logPassengerLeftTram(passenger, event, stop);
            stop.addPassenger(passenger, event.getDate());
        }
    }

    private void preDumpPassengersAtStop(Tram tram, Stop stop, Vector<Passenger> passengersWhoLeft) {
        for (var passengerNode : tram.getPassengers()) {
            var passenger = passengerNode.getValue().getPassenger();
            if (passenger.getDestinationStop().equals(stop) && stop.getPlacesLeft() > passengersWhoLeft.size()) {
                boolean managedToExit = passenger.exitTram(stop, passengerNode, tram);
                if(managedToExit) {
                    passengersWhoLeft.pushBack(passenger);
                }
            }
        }
    }

    public TramLine[] getTramLines() {
        return tramLines;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }

    public Stop[] getStops() {
        return stops;
    }

    public int getTotalTrips() {
        return totalTrips;
    }
}
