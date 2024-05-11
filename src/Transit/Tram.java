package Transit;

public class Tram {
    private int serialNumber;
    private TramLine line;
    private int capacity;

    public Tram(int serialNumber, TramLine line, int capacity) {
        this.serialNumber = serialNumber;
        this.line = line;
        this.capacity = capacity;
    }
}
