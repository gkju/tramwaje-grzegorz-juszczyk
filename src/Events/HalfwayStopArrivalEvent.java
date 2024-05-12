package Events;

import Transit.Stop;
import Transit.Tram;

import java.util.Date;

public class HalfwayStopArrivalEvent extends StopArrivalEvent {
    public HalfwayStopArrivalEvent(Stop stop, Tram tram, Date time, int i, boolean travellingToDecreasingStops) {
        super(stop, tram, time, i, travellingToDecreasingStops);
    }
}
