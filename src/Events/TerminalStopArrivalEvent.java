package Events;

import Transit.Stop;
import Transit.Tram;

import java.util.Date;

public class TerminalStopArrivalEvent extends StopArrivalEvent {
    public TerminalStopArrivalEvent(Stop stop, Tram tram, Date time) {
        super(stop, tram, time);
    }
}
