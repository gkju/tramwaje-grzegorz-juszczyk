package Logging;

import Events.Event;
import Events.PassengerArrivalEvent;
import Events.StopArrivalEvent;
import Simulator.Simulator;
import Transit.Passenger;
import Transit.Stop;
import Transit.Tram;

public class ConsoleLogger extends Logger {
    public ConsoleLogger() {
        super();
    }

    StringBuilder dayStats = new StringBuilder();

    @Override
    public void printArrival(StopArrivalEvent arrivalEvent) {
        var tram = arrivalEvent.getTram();
        var line = tram.getLine();
        System.out.printf("%d, %02d:%02d: Tramwaj o numerze bocznym %d, linii numer %d, przyjechał na przystanek" +
                " %s, w tramwaju jest %d osób, a na przystanku %d miejsc\n", arrivalEvent.getDate().getDate() - 1, arrivalEvent.getDate().getHours(), arrivalEvent.getDate().getMinutes(), tram.getSerialNumber(), line.getNumber(), arrivalEvent.getStop().getName(), tram.getPassengers().length, arrivalEvent.getStop().getPlacesLeft());
    }

    @Override
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
    public void logPassengerArrivedAtStop(Passenger passenger, Stop stop, PassengerArrivalEvent event) {
        System.out.printf("%d, %02d:%02d: Pasażer %s przyszedł na przystanek %s \n", event.getDate().getDate() - 1, event.getDate().getHours(), event.getDate().getMinutes(), passenger.getName(), stop.getName());
    }

    @Override
    public void logPassengerTramBoarding(Passenger passenger, Tram tram, StopArrivalEvent event) {
        System.out.printf("%d, %02d:%02d: Pasażer %s wsiadł do tramwaju o numerze bocznym %d, linii numer %d, na przystanku %s z zamiarem dojechania na przystanek %s\n", event.getDate().getDate() - 1, event.getDate().getHours(), event.getDate().getMinutes(), passenger.getName(), tram.getSerialNumber(), tram.getLine().getNumber(), event.getStop().getName(), passenger.getDestinationStop().getName());

    }

    @Override
    public void logPassengerLeftTram(Passenger passenger, StopArrivalEvent event, Stop stop) {
        System.out.printf("%d, %02d:%02d: Pasażer %s wysiadł na przystanku %s \n", event.getDate().getDate() - 1, event.getDate().getHours(), event.getDate().getMinutes(), passenger.getName(), event.getStop().getName());
    }

    @Override
    public void logForcedPassengersOffTram(Tram tram, Event event) {
        System.out.printf("%d, %02d:%02d: Pod koniec dnia zmuszono %d pasażerów do opuszczenia tramwaju o numerze bocznym %d \n", event.getDate().getDate() - 1, event.getDate().getHours(), event.getDate().getMinutes(), tram.getPassengersCount(), tram.getSerialNumber());
    }

    @Override
    public void logPassengersWentHome(Stop stop, Event event) {
        System.out.printf("%d, %02d:%02d: Pod koniec dnia z przystanku %s wróciło %d pasażerów \n", event.getDate().getDate() - 1, event.getDate().getHours(), event.getDate().getMinutes(), stop.getName(), stop.getPassengersCount());
    }

    @Override
    public void logFinalStats(Simulator simulator) {
        System.out.printf("--------------Statystyki dla całej symulacji--------------\n");
        System.out.printf("Łącznie odbyło się %d przejazdów\n", simulator.getTotalTrips());
        System.out.printf("Średni czas oczekiwania na przystanku wynosił %f minut\n", simulator.getAverageWaitTime().getAverage());
        System.out.printf("Czekano na przystankach łącznie %d razy\n", simulator.getAverageWaitTime().getCount());
        System.out.printf("Średni czas przejazdu wynosił %f minut\n", simulator.getAverageTripTime().getAverage());
        System.out.printf("Nie udało się podróżować %d pasażerom\n", simulator.getFailedTrips());

        System.out.print(dayStats.toString());
    }

    @Override
    public void logDayStats(Simulator simulator, Event event) {
        var prompt = String.format("--------------Statystyki dla dnia %d--------------\n", event.getDate().getDate() - 1);
        dayStats.append(prompt);
        System.out.printf(prompt);
        prompt = String.format("Dnia %d odbyło się %d przejazdów i czekano %d minut na przystankach.\n", event.getDate().getDate() - 1, simulator.getDayTrips(), simulator.getDayWaitTime());
        System.out.printf(prompt);
        dayStats.append(prompt);
        prompt = String.format("Średni czas oczekiwania na przystanku wynosił %f minut\n", simulator.getLastDayAverageWaitTime().getAverage());
        System.out.printf(prompt);
        dayStats.append(prompt);
        prompt = String.format("Czekano na przystankach łącznie %d razy\n", simulator.getLastDayAverageWaitTime().getCount());
        System.out.printf(prompt);
        dayStats.append(prompt);
        prompt = String.format("Średni czas przejazdu wynosił %f minut\n", simulator.getLastDayAverageTripTime().getAverage());
        System.out.printf(prompt);
        dayStats.append(prompt);
        prompt = String.format("Nie udało się podróżować %d pasażerom\n", simulator.getFailedTrips());
        System.out.printf(prompt);
        dayStats.append(prompt);
    }

    @Override
    public void logPassengerFailedToStayAtStop(Passenger passenger, Stop stop, PassengerArrivalEvent event) {
        System.out.printf("%d, %02d:%02d: Pasażer %s nie dał rady przyjść na przystanek %s\n", event.getDate().getDate() - 1, event.getDate().getHours(), event.getDate().getMinutes(), passenger.getName(), stop.getName());
    }
}
