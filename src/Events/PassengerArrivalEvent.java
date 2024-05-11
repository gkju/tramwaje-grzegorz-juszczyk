package Events;

import Transit.Passenger;
import Transit.Stop;

import java.util.Date;

public class PassengerArrivalEvent extends Event {
    private Stop stop;
    private Passenger passenger;

    public PassengerArrivalEvent(Date time, Stop stop, Passenger passenger) {
        super(time);
        this.stop = stop;
        this.passenger = passenger;
    }
}
