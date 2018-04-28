package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int state;
    private int period;

    public SawToothGenerator(int period) {
        this.state = 0;
        this.period = period;
    }

    private double normalize(int normalized) {
        normalized = 2 * (normalized / period) - 1;
        return normalized;
    }

    //next method that returns the next double
    public double next() {
        if (state == period) {
            state = 0;
            return normalize(state);
        }
        state += 1;
        return normalize(state);
    }
}
