package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;
    }

    public double next() {
        if (state == period) {
            state = 0;
            return normalize(state);
        }
        state += 1;
        return normalize(state);
    }

    private double normalize(double normalized) {
        normalized = 2 * (normalized / period) - 1;
        if (normalized == -1) {
            period = (int) (period * factor);
            period = Math.max(period, 1);
        }
        if (state == 0) {
            period = (int) (period * factor);
            period = Math.max(period, 1);
        }
        return normalized;
    }
}
