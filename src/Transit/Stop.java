package Transit;

import EventQueue.RandomHeap;
import EventQueue.RandomHeapNode;
import Events.Event;
import Logging.Logger;

import java.util.Date;
import java.util.Objects;

public class Stop {
    private String name;
    private int capacity;
    private RandomHeap<PassengerWaiting> passengersWaiting = new RandomHeap<PassengerWaiting>();

    public Stop(String number, int capacity) {
        this.name = number;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public PassengerWaiting getEldestPassenger() {
        return passengersWaiting.findMin().getValue();
    }

    public PassengerWaiting popEldestPassenger() {
        return passengersWaiting.popMin().getValue();
    }

    public void addPassenger(Passenger passenger, Date time) {
        passengersWaiting.insert(new RandomHeapNode<>(new PassengerWaiting(time, passenger)));
    }

    public boolean isEmpty() {
        return passengersWaiting.getSize() == 0;
    }

    public boolean isFull() {
        return passengersWaiting.getSize() == capacity;
    }

    public int getPlacesLeft() {
        return capacity - passengersWaiting.getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return capacity == stop.capacity && Objects.equals(name, stop.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity, passengersWaiting);
    }

    public void clearPassengers(Logger logger, Event event) {
        logger.logPassengersWentHome(this, event);
        passengersWaiting.clear();
    }

    public int getPassengersCount() {
        return passengersWaiting.getSize();
    }
}
