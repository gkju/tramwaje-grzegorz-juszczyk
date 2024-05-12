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

    public int getTotalDuration() {
        int totalDuration = 0;
        for (int time : getTimeBetweenStops()) {
            totalDuration += time;
        }
        return totalDuration;
    }

    public void startSimulation(EventQueue queue, int day) {
        int tramCnt = trams.length;
        // Mamy 17 godzin po 60 minut, w trakcie których może odjechać tramwaj.
        long delayBetweenTrams = (2L * getTotalDuration()) / tramCnt;

        Date currentDepartureTime = new Date(0, 0, day, 6, 0);
        for (int i = 0; i < tramCnt; i += 2) {
            trams[i].generateEvents(queue, currentDepartureTime);

            if(i + 1 < tramCnt) {
                trams[i + 1].generateEvents(queue, currentDepartureTime);
            }

            currentDepartureTime = new Date(currentDepartureTime.getTime() + delayBetweenTrams * 60 * 1000);
        }
    }
}
