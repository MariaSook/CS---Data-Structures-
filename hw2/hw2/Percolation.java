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
        this.wquf = new WeightedQuickUnionUF(N*N);
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                world[x][y] = false;
            }
        }
    }

    private void unionTopBottom(int row, int col, int me) {
        if (row == 0) {
            wquf.union(me, virtualTopSite);
        } else if (row == N-1) {
            wquf.union(me, virtualBottomSite);
        }
    }

    private void unionMiddle(int row, int col, int me) {
        if (row != 0 && row != N-1 && col != 0 && col != N-1) {
            if (isOpen(row + 1, col)) {
                wquf.union(me, xyTo1D(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                wquf.union(me, xyTo1D(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                wquf.union(me, xyTo1D(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                wquf.union(me, xyTo1D(row, col - 1));
            }
        }
    }

    private void unionCols(int row, int col, int me) {
        if (me != 0 && me != N-1 && me != (N-1)*N && me != (N*N)-1) {
            if (col == 0) {
                if (isOpen(row + 1, col)) {
                    wquf.union(me, xyTo1D(row + 1, col));
                }
                if (isOpen(row - 1, col)) {
                    wquf.union(me, xyTo1D(row - 1, col));
                }
                if (isOpen(row, col - 1)) {
                    wquf.union(me, xyTo1D(row, col - 1));
                }
            } else if (col == N - 1) {
                if (isOpen(row - 1, col)) {
                    wquf.union(me, xyTo1D(row - 1, col));
                }
                if (isOpen(row + 1, col)) {
                    wquf.union(me, xyTo1D(row + 1, col));
                }
                if (isOpen(row, col + 1)) {
                    wquf.union(me, xyTo1D(row, col + 1));
                }
            }
        }
    }

    private void unionEdgeCases(int row, int col, int me) {
        if (me == 0) {
            if (isOpen(row+1, col)) {
                wquf.union(me, xyTo1D(row + 1, col));
            }
            if (isOpen(row, col+1)){
                wquf.union(me, xyTo1D(row, col+1));
            }
        } else if (me == N-1) {
            if (isOpen(row+1, col)) {
                wquf.union(me, xyTo1D(row + 1, col));
            }
            if (isOpen(row, col-1)){
                wquf.union(me, xyTo1D(row, col-1));
            }
        } else if (me == (N-1)*N) {
            if (isOpen(row-1, col)) {
                wquf.union(me, xyTo1D(row - 1, col));
            }
            if (isOpen(row, col+1)){
                wquf.union(me, xyTo1D(row, col+1));
            }
        } else if (me == (N*N)-1){
            if (isOpen(row-1, col)) {
                wquf.union(me, xyTo1D(row - 1, col));
            }
            if (isOpen(row, col-1)){
                wquf.union(me, xyTo1D(row, col-1));
            }
        }

    }

    private void unionRows(int row, int col, int me) {
        if (me != 0 && me != N-1 && me != (N-1)*N && me != (N*N)-1) {
            if (row == 0) {
                if (isOpen(row, col - 1)) {
                    wquf.union(me, xyTo1D(row, col - 1));
                }
                if (isOpen(row, col + 1)) {
                    wquf.union(me, xyTo1D(row, col + 1));
                }
                if (isOpen(row + 1, col)) {
                    wquf.union(me, xyTo1D(row + 1, col));
                }
                wquf.union(me, virtualTopSite);
            } else if (row == N - 1) {
                if (isOpen(row, col + 1)) {
                    wquf.union(me, xyTo1D(row, col + 1));
                }
                if (isOpen(row, col - 1)) {
                    wquf.union(me, xyTo1D(row, col - 1));
                }
                if (isOpen(row - 1, col)) {
                    wquf.union(me, xyTo1D(row - 1, col));
                }
                wquf.union(me, virtualBottomSite);
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
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Out of bounds");
        } else if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        world[row][col] = true;
        numopen += 1;

        unionNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        } else if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        return world[row][col];
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        int me = xyTo1D(row, col);
        if (row >= N || col >= N) {
            throw new IndexOutOfBoundsException("Index Out of bounds");
        } else if (row < 0 || col < 0) {
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
        System.out.print(wquf.connected(virtualTopSite, virtualBottomSite));
        return wquf.connected(virtualTopSite, virtualBottomSite);
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        perc.open(0, 2);
        perc.open(1,2);
        perc.open(2, 2);
        perc.open(3, 2);
        perc.open(4, 2);
        perc.percolates();
    }
}
