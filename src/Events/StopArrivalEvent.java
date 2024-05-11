package Events;

import Transit.Stop;
import Transit.Tram;

import java.util.Date;

public class StopArrivalEvent extends Event {
    private Stop stop;
    private Tram tram;

    public StopArrivalEvent(Stop stop, Tram tram, Date time) {
        super(time);
        this.stop = stop;
        this.tram = tram;
    }

    public Stop getStop() {
        return stop;
    }

    public Tram getTram() {
        return tram;
    }
}
