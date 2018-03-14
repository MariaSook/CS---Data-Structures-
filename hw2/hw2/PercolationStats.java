package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private double[] tDoubleData;
    private double percfrac;
    private Percolation percolation;

    // @help from Gabby Schvartsmann
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        this.T = T;
        this.tDoubleData = new double[T];

        for (int i = 0; i < T; i++) {
            percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                percolation.open(row, col);
            }
            percfrac = percolation.numberOfOpenSites() / (N * N);
            tDoubleData[i] = percfrac;
        }

    }

    public double mean() {
        return StdStats.mean(tDoubleData);
    }

    public double stddev() {
        return StdStats.stddev(tDoubleData);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        double sqrtT = Math.sqrt(T);
        return mean() - ((1.96 * stddev()) / sqrtT);
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        double sqrtT = Math.sqrt(T);
        return mean() + ((1.96 * stddev()) / sqrtT);
    }
}
