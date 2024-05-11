package Transit;

import java.util.Date;
import EventQueue.EventQueue;
import Events.StopArrivalEvent;
import Events.TerminalStopArrivalEvent;
import Utils.Utils;

public class Tram {
    private int serialNumber;
    private TramLine line;
    private int capacity;

    public Tram(int serialNumber, TramLine line, int capacity) {
        this.serialNumber = serialNumber;
        this.line = line;
        this.capacity = capacity;
    }

    private void insertArrivalEvent(EventQueue queue, Stop[] stops, Integer[] timeBetweenStops, Date currentTime, int i) {
        queue.insert(new StopArrivalEvent(stops[i], this, currentTime));
        currentTime = new Date(currentTime.getTime() + timeBetweenStops[i] * 60 * 1000);
    }

    public void generateEvents(EventQueue queue, Date departureTime, Direction direction) {
        Stop[] stops = line.getStops();
        Integer[] timeBetweenStops = line.getTimeBetweenStops();

        if(stops.length == 0) {
            return;
        }

        if(direction == Direction.COUNTERCLOCKWISE) {
            Utils.reverseArray(stops);
            Utils.reverseArray(timeBetweenStops);
        }

        Date currentTime = departureTime;
        for (int i = 0; i < stops.length; i++) {
            insertArrivalEvent(queue, stops, timeBetweenStops, currentTime, i);
        }

        for (int i = stops.length - 2; i > 0; i--) {
            insertArrivalEvent(queue, stops, timeBetweenStops, currentTime, i);
        }

        queue.insert(new TerminalStopArrivalEvent(stops[0], this, currentTime));
    }
}
