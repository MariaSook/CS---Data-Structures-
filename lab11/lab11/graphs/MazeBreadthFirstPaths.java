package lab11.graphs;

/**
 * @author Josh Hug
 */
//public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
//    public Maze maze;
////    public int s;
////    public int t;
////    private boolean targetFound;
////    private ArrayDeque fringe;
////    private boolean[] marked;
////
////    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
////        super(m);
////        this.maze = m;
////        this.s = maze.xyTo1D(sourceX, sourceY);
////        this.t = maze.xyTo1D(targetX, targetY);
////        distTo[this.s] = 0;
////        edgeTo[this.s] = s;
////
////        // Add more variables here!
////    }


public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    public int s;
    public int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        solverBfs(s);
    }

    private void solverBfs(int v) {
        /*if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        fringe.add(v);
        marked[v] = true;

        while (!fringe.isEmpty()) {
            int vert = (int) fringe.removeLast();
            for (int w: maze.adj(vert)); {
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

    }
    */
    }


        @Override
        public void solve () {
            bfs();
        }
    }




