package Transit;

import EventQueue.RandomHeap;
import EventQueue.RandomHeapNode;

import java.util.Date;

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
}
