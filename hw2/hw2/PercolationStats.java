package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private double[] tDoubleData;
    private double percfrac;
    private double mean;
    private double stddev;
    private double conlow;
    private double conhigh;
    private double sumpercfrac;

    // @help from Gabby Schvartsmann
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        this.T = T;
        this.tDoubleData = new double[T];
        this.sumpercfrac = 0;

        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                percolation.open(row, col);
            }
            percfrac = percolation.numberOfOpenSites() / (N * N);
            tDoubleData[i] = percfrac;
            sumpercfrac += percfrac;
        }

    }

    public double mean() {
        // sample mean of percolation threshold
        mean = sumpercfrac / T;
        //mean = StdStats.mean(tDoubleData);
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
        conhigh = mean + ((1.96 * stddev) / sqrtT);
        return conhigh;
    }

}
