package Transit;

public class Line {
    private String name;
    private int number;
    private Stop[] stops;
    private int[] timeBetweenStops;

    public Line(String name, int number, Stop[] stops, int[] timeBetweenStops) {
        this.name = name;
        this.number = number;
        this.stops = stops;
        this.timeBetweenStops = timeBetweenStops;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Stop[] getStops() {
        return stops;
    }
}
