package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean[] marked;
    private Maze m;
    private int s;
    private int t;
    private boolean targetFound;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);

        this.marked = new boolean[m.V()];
        this.m = m;
        this.s = m.xyTo1D(sourceX, sourceY);
        this.t = m.xyTo1D(targetX, targetY);
        this.distTo[s] = 0;
        this.edgeTo[s] = s;
        this.targetFound = false;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        marked[s] = true;
        announce();

        if (s == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        for (int w : maze.adj(s)) {
            if (!marked[w]) {
                edgeTo[w] = s;
                announce();
                distTo[w] = distTo[s] + 1;
                bfs();
                if (targetFound) {
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

