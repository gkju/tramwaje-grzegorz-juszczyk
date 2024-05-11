package Transit;

public class TramLine extends Line {
    private Tram[] trams;

    public TramLine(String name, int number, Stop[] stops, int[] timeBetweenStops, Tram[] trams) {
        super(name, number, stops, timeBetweenStops);
        this.trams = trams;
    }
}
