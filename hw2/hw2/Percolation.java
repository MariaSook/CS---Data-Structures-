package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public boolean[][] world;
    public int numopen;
    public int N;
    public int virtualTopSite = N*N;
    public int virtualBottomSite = N*N + 1;
    public WeightedQuickUnionUF wquf;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        this.N = N;
        this.world = new boolean[N][N];
        this.wquf = new WeightedQuickUnionUF(N);
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                world[x][y] = false;
            }
        }
    }

    private void unionMiddle(int row, int col, int me) {
        if (isOpen(row - 1, col)) {
            wquf.union(me, xyTo1D(row-1, col));
        }
        if (isOpen(row + 1, col)) {
            wquf.union(me, xyTo1D(row+1, col));
        }
        if (isOpen(row, col + 1)) {
            wquf.union(me, xyTo1D(row, col+1));
        }
        if (isOpen(row, col - 1)) {
            wquf.union(me, xyTo1D(row, col-1));
        }
    }

    private void unionColsLeft(int row, int col, int me) {
        if (isOpen(row - 1, col)) {
            wquf.union(me, xyTo1D(row - 1, col));
        }
        if (isOpen(row + 1, col)) {
            wquf.union(me, xyTo1D(row + 1, col));
        }
        if (isOpen(row, col+1)) {
            wquf.union(me, xyTo1D(row, col+1));
        }
    }

    private void unionColsRight(int row, int col, int me) {
        if (isOpen(row - 1, col)) {
            wquf.union(me, xyTo1D(row-1, col));
        }
        if (isOpen(row + 1, col)) {
            wquf.union(me, xyTo1D(row + 1, col));
        }
        if (isOpen(row, col-1)) {
            wquf.union(me, xyTo1D(row, col-1));
        }
    }

    private void unionRowsTop(int row, int col, int me) {
        if (isOpen(row-1, col)) {
            wquf.union(me, xyTo1D(row-1, col));
        }
        if (isOpen(row, col+1)) {
            wquf.union(me, xyTo1D(row, col+1));
        }
        if (isOpen(row, col-1)) {
            wquf.union(me, xyTo1D(row, col-1));
        }
    }

    private void unionRowsBottom(int row, int col, int me) {
        if (isOpen(row+1, col)) {
            wquf.union(me, xyTo1D(row-1, col));
        }
        if (isOpen(row, col+1)) {
            wquf.union(me, xyTo1D(row, col+1));
        }
        if (isOpen(row, col-1)) {
            wquf.union(me, xyTo1D(row, col-1));
        }
    }

    private void unionEdgeCases(int row, int col, int me){
        if (isOpen(row, col-1)) {
            wquf.union(me, xyTo1D(row, col-1));
        } else if (isOpen(row+1, col)) {
            wquf.union(me, xyTo1D(row+1, col));
        } else if (isOpen(row-1, col)) {
            wquf.union(me, xyTo1D(row-1, col));
        } else if (isOpen(row, col+1)) {
            wquf.union(me, xyTo1D(row, col+1));
        }
    }

    private void unionNeighbors(int row, int col) {
        int me = xyTo1D(row, col);

        if (row != 0 && row != N - 1 && col != 0 && col != N-1) {
            unionMiddle(row, col, me);
        } else if (col == 0 && (row != 0 || row != N-1)) {
            unionColsLeft(row, col, me);
        } else if (col == N-1 && (row != 0 || row != N-1)) {
            unionColsRight(row, col, me);
        } else if (row == 0 && (col != 0 && col != N-1)) {
            unionRowsTop(row, col, me);
        } else if (row == N-1 && (col != 0 && col != N-1)) {
            unionRowsBottom(row, col, me);
        }
        unionEdgeCases(row, col, me);
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        int me = xyTo1D(row, col);
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Out of bounds");
        } else if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        if (row == 0) {
            wquf.union(me, virtualTopSite);
        } else if (row == N-1) {
            wquf.union(me, virtualBottomSite);
        }

        world[row][col] = true;
        numopen += 1;
        unionNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        } else if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        return world[row][col];
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        int me = xyTo1D(row, col);
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        } else if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
       return wquf.connected(me, virtualTopSite);
    }

    private int xyTo1D(int row, int col) {
        return N * row + col;
    }

    public int numberOfOpenSites() {
        return numopen;
    }

    public boolean percolates() {
        // does the system percolate?
        return wquf.connected(virtualTopSite, virtualBottomSite);
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        perc.open(2, 2);
        perc.open(3,3);
        perc.open(1, 4);
    }
}

