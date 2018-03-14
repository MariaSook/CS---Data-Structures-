package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private double[] tDoubleData;
    private Percolation percolation;
    private double mean;
    private double std;
    private int N;

    // @help from Gabby Schvartsmann
    public PercolationStats(PercolationFactory pf, int N, int T) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        this.T = T;
        this.tDoubleData = new double[T];
        this.N = N;

        for (int simulation = 0; simulation < T; simulation++) {
            this.percolation = pf.make(N);
            while (!this.percolation.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                this.percolation.open(row, col);
            }
            this.tDoubleData[simulation] = (double) percolation.numberOfOpenSites() / (N * N);
        }

    }

    public double mean() {
        this.mean = StdStats.mean(tDoubleData);
        return this.mean;
    }

    public double stddev() {
        this.std = StdStats.stddev(tDoubleData);
        return this.std;
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
