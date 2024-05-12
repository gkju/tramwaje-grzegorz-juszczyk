package Transit;

import EventQueue.EventQueue;
import EventQueue.RandomHeapNode;
import Events.PassengerArrivalEvent;
import Main.Losowanie;

import java.util.Date;

public class Passenger {
    private Stop homeStop;
    private Stop destinationStop;
    private String name;
    private Date enteredTramAt;

    public Passenger(Stop homeStop, String name) {
        this.name = name;
        this.homeStop = homeStop;
    }

    public void handleNewDay(EventQueue eventQueue, int day) {
        Date currentDate = new Date(0, 0, day, 6, 0);
        long offset = Losowanie.losuj(0, 6 * 60);
        Date leaveAt = new Date(currentDate.getTime() + offset * 60 * 1000);

        eventQueue.insert(new PassengerArrivalEvent(leaveAt, homeStop, this));
    }

    public void setDestinationStop(Stop destinationStop) {
        this.destinationStop = destinationStop;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }

    public boolean exitTram(Stop stop, RandomHeapNode<PassengerWaiting> node, Tram tram) {
        tram.removePassenger(node);
        return true;
    }

    public Date getEnteredTramAt() {
        return enteredTramAt;
    }

    public void setEnteredTramAt(Date enteredTramAt) {
        this.enteredTramAt = enteredTramAt;
    }

    public String getName() {
        return name;
    }
}
