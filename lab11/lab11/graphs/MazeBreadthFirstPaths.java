package lab11.graphs;

import java.util.Queue;
import java.util.ArrayDeque;

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
    private ArrayDeque fringe;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        this.m = m;
        this.s = m.xyTo1D(sourceX, sourceY);
        this.t = m.xyTo1D(targetX, targetY);
        this.distTo[s] = 0;
        this.edgeTo[s] = s;

        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
       solverBfs(s);
    }
/*
    marked[v] = true;
    announce();

        if (v == t) {
        targetFound = true;
    }

        if (targetFound) {
        return;
    }

        for (int w : maze.adj(v)) {
        if (!marked[w]) {
            edgeTo[w] = v;
            announce();
            distTo[w] = distTo[v] + 1;
            dfs(w);
            if (targetFound) {
                return;
            }
        }
    }

    */
    private void solverBfs(int v) {
        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        fringe.add(v);
        marked[v] = true;
        /*
        while (!fringe.isEmpty()) {
            int vert = (int) fringe.removeLast();
            for (int w: m.adj(vert)); {
                if(!marked[w]) {
                    fringe.add(w);
                    marked[w] = true;
                    distTo[w] = distTo[vert] + 1;
                    solverBfs(w);
                    if (targetFound) {
                        return;
                    }
                }
            }
        }
        */
    }


    @Override
    public void solve() {
        bfs();
    }
}

