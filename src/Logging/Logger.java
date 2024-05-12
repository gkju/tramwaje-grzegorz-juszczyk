package Logging;

import Events.Event;
import Events.PassengerArrivalEvent;
import Events.StopArrivalEvent;
import Simulator.Simulator;
import Transit.Passenger;
import Transit.Stop;
import Transit.Tram;

public abstract class Logger {
    public Logger() {

    }

    public void printArrival(StopArrivalEvent arrivalEvent) {

    }

    public abstract void logSimulationData(Simulator simulator);

    public abstract void logPassengerArrivedAtStop(Passenger passenger, Stop stop, PassengerArrivalEvent event);

    public void logPassengerTramBoarding(Passenger passenger, Tram tram, StopArrivalEvent event) {

    }

    public abstract void logPassengerLeftTram(Passenger passenger, StopArrivalEvent event, Stop stop);

    public abstract void logForcedPassengersOffTram(Tram tram, Event event);

    public void logPassengersWentHome(Stop stop, Event event) {
    }

    public abstract void logFinalStats(Simulator simulator);

    public abstract void logDayStats(Simulator simulator, Event event);
}
