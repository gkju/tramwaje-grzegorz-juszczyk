package Events;

import Transit.Stop;
import Transit.Tram;

import java.util.Date;

public class StopArrivalEvent extends Event {
    private Stop stop;
    int stopIndex = 0;
    private Tram tram;
    private boolean travellingToDecreasingStops;

    public StopArrivalEvent(Stop stop, Tram tram, Date time, int stopIndex, boolean travellingToDecreasingStops) {
        super(time);
        this.stop = stop;
        this.tram = tram;
        this.stopIndex = stopIndex;
        this.travellingToDecreasingStops = travellingToDecreasingStops;
    }

    public Stop getStop() {
        return stop;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public Tram getTram() {
        return tram;
    }

    public boolean isTravellingToDecreasingStops() {
        return travellingToDecreasingStops;
    }
}
