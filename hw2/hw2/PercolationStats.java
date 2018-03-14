package hw2;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int T;
    private int s;
    private int N;
    private double[] tDoubleData;
    private PercolationFactory pf;
    private double percfrac;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        this.tDoubleData = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            while (!perc.percolates()){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                perc.open(row, col);

            }
            percfrac = perc.numberOfOpenSites()/(N*N);
            tDoubleData[i] = percfrac;
        }

    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(tDoubleData);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(tDoubleData);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        double mean = mean();
        double stddev = stddev();
        double sqrtT = Math.sqrt(T);

        return mean - ((1.96 * stddev) / sqrtT);
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        double mean = mean();
        double stddev = stddev();
        double sqrtT = Math.sqrt(T);

        return mean + ((1.96 * stddev) / sqrtT);
    }
}
