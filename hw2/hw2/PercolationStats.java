package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private int N;
    private double[] tDoubleData;
    private Percolation perc;
    private PercolationFactory pf;
    private double percfrac;
    private double mean;
    private double stddev;
    private double conlow;
    private double conhigh;

    // @help from Gabby Schvartsmann
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
            this.perc = pf.make(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                perc.open(row, col);
            }
            percfrac = perc.numberOfOpenSites() / (N * N);
            tDoubleData[i] = percfrac;
        }

    }

    public double mean() {
        // sample mean of percolation threshold
        mean = StdStats.mean(tDoubleData);
        return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        stddev = StdStats.stddev(tDoubleData);
        return stddev;
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        double sqrtT = Math.sqrt(T);
        conlow = mean - ((1.96 * stddev) / sqrtT);
        return conlow;
    }
    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        double sqrtT = Math.sqrt(T);
        conlow = mean + ((1.96 * stddev) / sqrtT);
        return conlow;
    }

}
