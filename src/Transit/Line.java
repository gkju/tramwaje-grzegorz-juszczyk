package Transit;

public class Line {
    private int number;
    private Stop[] stops;
    private Integer[] timeBetweenStops;

    public Line(int number, Stop[] stops, Integer[] timeBetweenStops) {
        this.number = number;
        this.stops = stops;
        this.timeBetweenStops = timeBetweenStops;
    }

    public int getNumber() {
        return number;
    }

    public Integer[] getTimeBetweenStops() {
        return timeBetweenStops;
    }

    public Stop[] getStops() {
        return stops;
    }
}
