package Transit;

import java.util.Date;

public class PassengerWaiting implements Comparable<PassengerWaiting> {
    private Date startedWaitingAt;
    private Passenger passenger;

    public PassengerWaiting(Date startedWaitingAt, Passenger passenger) {
        this.startedWaitingAt = startedWaitingAt;
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Date getStartedWaitingAt() {
        return startedWaitingAt;
    }

    @Override
    public int compareTo(PassengerWaiting o) {
        return startedWaitingAt.compareTo(o.startedWaitingAt);
    }
}
