package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private int[][] board;
    private int N;

    public Board(int[][] tiles) {
    //Constructs a board from an
        // N-by-N array of tiles where//
        // tiles[i][j] = tile at row i, column j

        this.N = tiles.length;
        this.board = new int[N][N];
        for (int row = 0; row < N; row ++) {
            for (int column = 0; column < N; column ++) {
                board[row][column] = tiles[row][column];
            }
        }
    }

    public int tileAt(int i, int j) {
    //Returns value of tile at row i, column j (or 0 if blank)
        if (i < 0 || j < 0  || i > N-1 || j > N-1) {
        throw new java.lang.IndexOutOfBoundsException();
        }

        if (board[i][j] == 0) {
            return 0;
        }

        else {
            return board[i][j];
        }
    }

    public int size() {
        return N*N - 1;
    }

    // @source http://joshh.ug/neighbors.html
    public Iterable<WorldState> neighbors() {
    //Returns the neighbors of the current board
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
    //Hamming estimate described below
        return 0;
    }

    public int manhattan() {
    //Manhattan estimate described below
        return 0;
    }

    public int estimatedDistanceToGoal() {
    //Estimated distance to goal. This method should
        //simply return the results of manhattan() when submitted to
        //              Gradescope.
        return manhattan();
    }

    public boolean equals(Object y) {
    //Returns true if this board's tile values are the same
        //              position as y's

        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
