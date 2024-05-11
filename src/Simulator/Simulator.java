package Simulator;

import EventQueue.EventQueue;
import Main.Losowanie;
import Transit.Stop;
import Transit.Tram;
import Transit.TramLine;

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
                    throw new IllegalStateException("Nieporawna nazwa przystanku.");
                }
                timeBetweenStops[j] = timeBetweenStop;
            }

            TramLine tramLine = new TramLine(i, lineStops, timeBetweenStops);
            Tram[] trams = new Tram[tramCount];
            for (int j = 0; j < tramCount; j++) {
                trams[j] = new Tram(Losowanie.losuj(1, 1000), tramLine, tramCapacity);
            }
            tramLine.setTrams(trams);

            tramLines[i] = tramLine;
        }
    }

    public void simulate() {
        for (int i = 0; i < simulationDuration; i++) {
            simulateDay(i);
        }
    }

    public void simulateDay(int day) {
        EventQueue eventQueue = new EventQueue();
        for (TramLine tramLine : tramLines) {
            tramLine.startSimulation(eventQueue, day);
        }
    }
}
