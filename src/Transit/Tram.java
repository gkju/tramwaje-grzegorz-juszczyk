package Transit;

import java.util.ArrayList;
import java.util.Date;
import EventQueue.EventQueue;
import Events.Event;
import Events.HalfwayStopArrivalEvent;
import Events.StopArrivalEvent;
import Events.TerminalStopArrivalEvent;
import Logging.ConsoleLogger;
import Main.Losowanie;
import Utils.Utils;
import EventQueue.RandomHeap;
import EventQueue.RandomHeapNode;
import EventQueue.Vector;

public class Tram {
    private int serialNumber;
    private TramLine line;
    private int capacity;
    private Direction direction;
    private RandomHeap<PassengerWaiting> passengers = new RandomHeap<PassengerWaiting>();

    public Tram(int serialNumber, TramLine line, int capacity, Direction direction) {
        this.serialNumber = serialNumber;
        this.line = line;
        this.capacity = capacity;
        this.direction = direction;
    }

    private Date insertArrivalEvent(EventQueue queue, Stop[] stops, Integer[] timeBetweenStops, Date currentTime, int i, boolean halfway, Direction dir, boolean travellingToDecreasingStops) {
        if(halfway) {
            queue.insert(new HalfwayStopArrivalEvent(stops[i], this, currentTime, dir == Direction.COUNTERCLOCKWISE ? stops.length - i - 1 : i, travellingToDecreasingStops));
        } else {
            queue.insert(new StopArrivalEvent(stops[i], this, currentTime, dir == Direction.COUNTERCLOCKWISE ? stops.length - i - 1 : i, travellingToDecreasingStops));
        }

        return new Date(currentTime.getTime() + timeBetweenStops[i] * 60 * 1000);
    }

    private void shiftTimesForward(Integer[] timeBetweenStops) {
        Integer last = timeBetweenStops[timeBetweenStops.length - 1];
        for(int i = timeBetweenStops.length - 1; i > 0; --i) {
            timeBetweenStops[i] = timeBetweenStops[i - 1];
        }
        timeBetweenStops[0] = last;
    }

    private void shiftTimesBackward(Integer[] timeBetweenStops) {
        Integer first = timeBetweenStops[0];
        for(int i = 0; i < timeBetweenStops.length - 1; ++i) {
            timeBetweenStops[i] = timeBetweenStops[i + 1];
        }
        timeBetweenStops[timeBetweenStops.length - 1] = first;
    }

    public void generateEvents(EventQueue queue, Date departureTime) {
        Stop[] stops = line.getStops();
        Integer[] timeBetweenStops = line.getTimeBetweenStops();

        if(stops.length == 0) {
            return;
        }

        if(direction == Direction.COUNTERCLOCKWISE) {
            Utils.reverseArray(stops);
            Utils.reverseArray(timeBetweenStops);
            shiftTimesBackward(timeBetweenStops);
        }

        Date currentTime = departureTime;
        boolean travellingToDecreasingStops = direction == Direction.COUNTERCLOCKWISE;
        for (int i = 0; i < stops.length; i++) {
            if(i == stops.length - 1) {
                currentTime = insertArrivalEvent(queue, stops, timeBetweenStops, currentTime, i, true, direction, travellingToDecreasingStops);
                continue;
            }

            currentTime = insertArrivalEvent(queue, stops, timeBetweenStops, currentTime, i, false, direction, travellingToDecreasingStops);
        }

        travellingToDecreasingStops ^= true;

        shiftTimesForward(timeBetweenStops);
        for (int i = stops.length - 1; i > 0; i--) {
            currentTime = insertArrivalEvent(queue, stops, timeBetweenStops, currentTime, i, false, direction, travellingToDecreasingStops);
        }
        shiftTimesBackward(timeBetweenStops);

        queue.insert(new TerminalStopArrivalEvent(stops[0], this, currentTime, direction, direction == Direction.COUNTERCLOCKWISE ? stops.length - 1 : 0, travellingToDecreasingStops));

        if(direction == Direction.COUNTERCLOCKWISE) {
            shiftTimesForward(timeBetweenStops);
            Utils.reverseArray(stops);
            Utils.reverseArray(timeBetweenStops);
        }
    }

    public TramLine getLine() {
        return line;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void insertPassengerAndChooseStop(Passenger passenger, Date date, StopArrivalEvent event) {
        if(passengers.getSize() >= capacity) {
            throw new IllegalStateException("Tram is full.");
        }
        var tram = event.getTram();
        var stops = tram.getLine().getStops();
        int nextStopIndex = 0;
        if(event.isTravellingToDecreasingStops()) {
            nextStopIndex = Losowanie.losuj(0, event.getStopIndex() - 1);
        } else {
            nextStopIndex = Losowanie.losuj(event.getStopIndex() + 1, stops.length - 1);
        }
        passenger.setDestinationStop(stops[nextStopIndex]);
        passenger.setEnteredTramAt(event.getDate());
        passengers.insert(new RandomHeapNode<>(new PassengerWaiting(date, passenger)));
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isFull() {
        return passengers.getSize() >= capacity;
    }

    public ArrayList<RandomHeapNode<PassengerWaiting>> getPassengers() {
        return passengers.getNodes();
    }

    public int getPassengersCount() {
        return passengers.getSize();
    }

    public void removePassenger(RandomHeapNode<PassengerWaiting> passengerNode) {
        passengers.delete(passengerNode);
    }

    public void clearPassengers(ConsoleLogger logger, Event event) {
        logger.logForcedPassengersOffTram(this, event);
        passengers.clear();
    }
}
