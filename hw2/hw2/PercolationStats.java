package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private int N;
    private double[] tDoubleData;
    private PercolationFactory pf;
    private double percfrac;
    private double m;
    private double s;
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
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                perc.open(row, col);
            }
            percfrac = perc.numberOfOpenSites() / (N * N);
            tDoubleData[i] = percfrac;
            T--;
        }

    }

    public double mean() {
        // sample mean of percolation threshold
        m = StdStats.mean(tDoubleData);
        return m;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        s = StdStats.stddev(tDoubleData);
        return s;
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        double sqrtT = Math.sqrt(T);
        conlow = m - ((1.96 * s) / sqrtT);
        return conlow;
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        double sqrtT = Math.sqrt(T);
        conhigh = m + ((1.96 * s) / sqrtT);
        return conhigh;
    }
}
