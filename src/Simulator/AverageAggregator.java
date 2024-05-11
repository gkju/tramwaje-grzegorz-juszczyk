package Simulator;

public class AverageAggregator {
    private double sum;
    private int count;

    public AverageAggregator() {
        sum = 0;
        count = 0;
    }

    public void add(double value) {
        sum += value;
        count++;
    }

    public double getAverage() {
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }
}
