package Logging;

import Events.Event;
import Events.StopArrivalEvent;
import Simulator.Simulator;
import Transit.Passenger;
import Transit.Stop;
import Transit.Tram;

public class ConsoleLogger extends Logger {
    public ConsoleLogger() {
        super();
    }

    @Override
    public void printArrival(StopArrivalEvent arrivalEvent) {
        var tram = arrivalEvent.getTram();
        var line = tram.getLine();
        System.out.printf("%d, %02d:%02d: Tramwaj o numerze bocznym %d, linii numer %d, przyjechał na przystanek" +
                " %s, w tramwaju jest %d osób, a na przystanku %d miejsc\n", arrivalEvent.getDate().getDay(), arrivalEvent.getDate().getHours(), arrivalEvent.getDate().getMinutes(), tram.getSerialNumber(), line.getNumber(), arrivalEvent.getStop().getName(), tram.getPassengers().size(), arrivalEvent.getStop().getPlacesLeft());
    }

    public void logSimulationData(Simulator simulator) {
        System.out.println("Rozpoczęto symulację");
        System.out.printf("Liczba linii tramwajowych: %d\n", simulator.getTramLines().length);
        System.out.printf("Liczba pasażerów: %d\n", simulator.getPassengerCount());
        System.out.printf("Czas trwania symulacji: %d dni\n", simulator.getSimulationDuration());
        var stops = simulator.getStops();
        var lines = simulator.getTramLines();
        for (var stop : stops) {
            System.out.printf("Przystanek %s: %d miejsc\n", stop.getName(), stop.getCapacity());
        }
        for (var line : lines) {
            System.out.printf("Linia %d: Liczba przystanków - %d\n", line.getNumber(), line.getStops().length);
            for(var stop : line.getStops()) {
                System.out.printf("Przystanek %s\n", stop.getName());
            }
        }
    }

    @Override
    public void logPassengerTramBoarding(Passenger passenger, Tram tram, StopArrivalEvent event) {
        System.out.printf("%d, %02d:%02d: Pasażer %s wsiadł do tramwaju o numerze bocznym %d, linii numer %d, na przystanku %s z zamiarem dojechania na przystanek %s\n", event.getDate().getDay(), event.getDate().getHours(), event.getDate().getMinutes(), passenger.getName(), tram.getSerialNumber(), tram.getLine().getNumber(), event.getStop().getName(), passenger.getDestinationStop().getName());

    }

    public void logPassengerLeftTram(Passenger passenger, StopArrivalEvent event, Stop stop) {
        System.out.printf("%d, %02d:%02d: Pasażer %s wysiadł na przystanku %s \n", event.getDate().getDay(), event.getDate().getHours(), event.getDate().getMinutes(), passenger.getName(), event.getStop().getName());
    }

    public void logForcedPassengersOffTram(Tram tram, Event event) {
        System.out.printf("%d, %02d:%02d: Pod koniec dnia zmuszono %d pasażerów do opuszczenia tramwaju o numerze bocznym %d \n", event.getDate().getDay(), event.getDate().getHours(), event.getDate().getMinutes(), tram.getPassengersCount(), tram.getSerialNumber());
    }

    @Override
    public void logPassengersWentHome(Stop stop, Event event) {
        System.out.printf("%d, %02d:%02d: Pod koniec dnia z przystanku %s wróciło %d pasażerów \n", event.getDate().getDay(), event.getDate().getHours(), event.getDate().getMinutes(), stop.getName(), stop.getPassengersCount());
    }

    public void logFinalStats(Simulator simulator) {
        System.out.printf("Łącznie odbyło się %d przejazdów", simulator.getTotalTrips());
        System.out.printf("Średni czas oczekiwania na przystanku wynosił %f minut", simulator.getAverageWaitTime().getAverage());
    }
}
