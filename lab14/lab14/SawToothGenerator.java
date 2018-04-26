package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int state;
    private int period;

    public SawToothGenerator(int period) {
        this.state = -1;
        this.period = period;
    }

    private int normalize() {
        return 0;
    }


    //next method that returns the next double
    public double next() {
//        state = (state + 1);
//        double period = StdAudio.SAMPLE_RATE / frequency;
//        return Math.sin(state * 2 * Math.PI / period);
        return 0.0;
    }
}
