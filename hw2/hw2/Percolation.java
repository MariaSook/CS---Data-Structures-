package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] world;
    private int numopen = 0;
    private int N;
    private int virtualTopSite;
    private int virtualBottomSite;
    private WeightedQuickUnionUF w;
    private WeightedQuickUnionUF wfull;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        this.N = N;
        if (N <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        this.world = new boolean[N][N];
        this.w = new WeightedQuickUnionUF(N * N + 2);
        this.wfull = new WeightedQuickUnionUF(N * N + 2);
        this.virtualTopSite = N * N;
        this.virtualBottomSite = N * N + 1;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                world[x][y] = false;
            }
        }
    }

    private void unionTopBottom(int row, int col, int me) {
        if (row == 0) {
            w.union(me, virtualTopSite);
            wfull.union(me, virtualTopSite);
        } else if (row == N - 1) {
            w.union(me, virtualBottomSite);
        }
    }

    private void unionMiddle(int row, int col, int me) {
        if (row != 0 && row != N - 1 && col != 0 && col != N - 1) {
            if (isOpen(row + 1, col)) {
                w.union(me, xyTo1D(row + 1, col));
                wfull.union(me, xyTo1D(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                w.union(me, xyTo1D(row - 1, col));
                wfull.union(me, xyTo1D(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                w.union(me, xyTo1D(row, col + 1));
                wfull.union(me, xyTo1D(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                w.union(me, xyTo1D(row, col - 1));
                wfull.union(me, xyTo1D(row, col - 1));
            }
        }
    }

    private void unionCols(int row, int col, int me) {
        if (me != 0 && me != N - 1 && me != (N - 1) * N && me != (N * N) - 1) {
            if (col == 0) {
                if (isOpen(row - 1, col)) {
                    w.union(me, xyTo1D(row - 1, col));
                    wfull.union(me, xyTo1D(row - 1, col));
                }
                if (isOpen(row + 1, col)) {
                    w.union(me, xyTo1D(row + 1, col));
                    wfull.union(me, xyTo1D(row + 1, col));
                }
                if (isOpen(row, col + 1)) {
                    w.union(me, xyTo1D(row, col + 1));
                    wfull.union(me, xyTo1D(row, col + 1));
                }
            } else if (col == N - 1) {
                if (isOpen(row + 1, col)) {
                    w.union(me, xyTo1D(row + 1, col));
                    wfull.union(me, xyTo1D(row + 1, col));
                }
                if (isOpen(row - 1, col)) {
                    w.union(me, xyTo1D(row - 1, col));
                    wfull.union(me, xyTo1D(row - 1, col));
                }
                if (isOpen(row, col - 1)) {
                    w.union(me, xyTo1D(row, col - 1));
                    wfull.union(me, xyTo1D(row, col - 1));
                }
            }
        }
    }

    private void unionRows(int row, int col, int me) {
        if (me != 0 && me != N - 1 && me != (N - 1) * N && me != (N * N) - 1) {
            if (row == 0) {
                if (isOpen(row, col - 1)) {
                    w.union(me, xyTo1D(row, col - 1));
                    wfull.union(me, xyTo1D(row, col - 1));
                }
                if (isOpen(row, col + 1)) {
                    w.union(me, xyTo1D(row, col + 1));
                    wfull.union(me, xyTo1D(row, col + 1));
                }
                if (isOpen(row + 1, col)) {
                    w.union(me, xyTo1D(row + 1, col));
                    wfull.union(me, xyTo1D(row + 1, col));
                }
                w.union(me, virtualTopSite);
            } else if (row == N - 1) {
                if (isOpen(row, col + 1)) {
                    w.union(me, xyTo1D(row, col + 1));
                    wfull.union(me, xyTo1D(row, col + 1));
                }
                if (isOpen(row, col - 1)) {
                    w.union(me, xyTo1D(row, col - 1));
                    wfull.union(me, xyTo1D(row, col - 1));
                }
                if (isOpen(row - 1, col)) {
                    w.union(me, xyTo1D(row - 1, col));
                    wfull.union(me, xyTo1D(row - 1, col));
                }
                w.union(me, virtualBottomSite);
            }
        }
    }

    private void unionEdgeCases(int row, int col, int me) {
        if (me == 0) {
            if (isOpen(row + 1, col)) {
                w.union(me, xyTo1D(row + 1, col));
                wfull.union(me, xyTo1D(row + 1, col));
            }
            if (isOpen(row, col + 1)) {
                w.union(me, xyTo1D(row, col + 1));
                wfull.union(me, xyTo1D(row, col + 1));
            }
        } else if (me == N - 1) {
            if (isOpen(row + 1, col)) {
                w.union(me, xyTo1D(row + 1, col));
                wfull.union(me, xyTo1D(row + 1, col));
            }
            if (isOpen(row, col - 1)) {
                w.union(me, xyTo1D(row, col - 1));
                wfull.union(me, xyTo1D(row, col - 1));
            }
        } else if (me == (N - 1) * N) {
            if (isOpen(row - 1, col)) {
                w.union(me, xyTo1D(row - 1, col));
                wfull.union(me, xyTo1D(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                w.union(me, xyTo1D(row, col + 1));
                wfull.union(me, xyTo1D(row, col + 1));
            }
        } else if (me == (N * N) - 1) {
            if (isOpen(row - 1, col)) {
                w.union(me, xyTo1D(row - 1, col));
                wfull.union(me, xyTo1D(row - 1, col));
            }
            if (isOpen(row, col - 1)) {
                w.union(me, xyTo1D(row, col - 1));
                wfull.union(me, xyTo1D(row, col - 1));
            }
        }

    }

    private void unionNeighbors(int row, int col) {
        int me = xyTo1D(row, col);

        unionTopBottom(row, col, me);
        unionEdgeCases(row, col, me);
        unionMiddle(row, col, me);
        unionRows(row, col, me);
        unionCols(row, col, me);

    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        int me = xyTo1D(row, col);
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Out of bounds");
        } else if (row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (!isOpen(row, col)) {
            world[row][col] = true;
            numopen += 1;
            if (N > 2) {
                unionNeighbors(row, col);
            } else if (N == 2) {
                unionTopBottom(row, col, me);
                unionEdgeCases(row, col, me);
            } else if (N == 1) {
                w.union(me, virtualTopSite);
                w.union(me, virtualBottomSite);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        } else if (row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        }
        return world[row][col];
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        int me = xyTo1D(row, col);
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        } else if (row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        }

        if (wfull.connected(me, virtualTopSite)) {
            return true;
        }
        return false;
    }

    private int xyTo1D(int row, int col) {
        return N * row + col;
    }

    public int numberOfOpenSites() {
        return numopen;
    }

    public boolean percolates() {
        // does the system percolate?

        return w.connected(virtualBottomSite, virtualTopSite);
    }

    public static void main(String[] args) {

    }
}

