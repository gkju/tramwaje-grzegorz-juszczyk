package Transit;

import EventQueue.EventQueue;

import java.util.Date;

public class TramLine extends Line {
    private Tram[] trams;

    public TramLine(int number, Stop[] stops, Integer[] timeBetweenStops) {
        super(number, stops, timeBetweenStops);
    }

    public void setTrams(Tram[] trams) {
        this.trams = trams;
    }

    public void startSimulation(EventQueue queue, int day) {
        int tramCnt = trams.length;
        // Mamy 16 godzin po 60 minut, w trakcie których
        int delayBetweenTrams = 16 * 60 / (tramCnt / 2);

        Date currentDepartureTime = new Date(day, 0, day, 6, 0);
        for (int i = 0; i < tramCnt; i += 2) {
            trams[i].generateEvents(queue, currentDepartureTime, Direction.CLOCKWISE);

            if(i + 1 < tramCnt) {
                trams[i + 1].generateEvents(queue, currentDepartureTime, Direction.COUNTERCLOCKWISE);
            }

            currentDepartureTime.setMinutes(currentDepartureTime.getMinutes() + delayBetweenTrams);
        }
    }
}
