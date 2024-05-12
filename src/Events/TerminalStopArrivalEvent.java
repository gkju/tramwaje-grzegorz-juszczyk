package Events;

import Transit.Direction;
import Transit.Stop;
import Transit.Tram;

import java.util.Date;

public class TerminalStopArrivalEvent extends StopArrivalEvent {
    private Direction travelDirection;

    public TerminalStopArrivalEvent(Stop stop, Tram tram, Date time, Direction direction, int stopIndex, boolean isTravellingToDecreasingStops) {
        super(stop, tram, time, stopIndex, isTravellingToDecreasingStops);
        this.travelDirection = direction;
    }
}
