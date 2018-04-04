package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

public class Solver {
    private MinPQ nodes;
    private int moves;
    private Set<WorldState> seen;
    private Set<WorldState> returnvals;

    public Solver(WorldState initial) {
        SearchNode initialSN = new SearchNode(initial, 0, null);
        SNComparator comp = new SNComparator();
        this.nodes = new MinPQ(comp);
        this.seen = new HashSet<>();
        this.returnvals = new HashSet<>();

        nodes.insert(initialSN);
        seen.add(initial);

        solverHelper();
    }

    private void solverHelper() {
        SearchNode curr = (SearchNode) nodes.delMin();
        WorldState currws = curr.returnws();
        returnvals.add(currws);
        if (currws.isGoal()) {
            this.moves = curr.moves;
            return;
        } else {
            for (WorldState n: currws.neighbors()) {
                if (!seen.contains(n)) {
                    SearchNode me = new SearchNode(n, curr.moves + 1, curr);
                    nodes.insert(me);
                }
                seen.add(n);
            }

            solverHelper();
        }
    }

    public int moves() {
      return this.moves;
    }

    public Iterable<WorldState> solution() {
        return returnvals;
    }

    private class SearchNode {
        private WorldState ws;
        private int moves;
        private SearchNode previous;


        public SearchNode(WorldState ws, int moves, SearchNode previous) {
            this.ws = ws;
            this.moves = moves;
            this.previous = previous;
        }

        private int priotiy() {
            WorldState me = returnws();
            return this.moves + me.estimatedDistanceToGoal();
        }

        private WorldState returnws() {
            return this.ws;
        }
    }

    private class SNComparator implements Comparator {
        @Override
        public int compare(Object obj1, Object obj2) {
            Solver.SearchNode me = (Solver.SearchNode) obj1;
            Solver.SearchNode you = (Solver.SearchNode) obj2;
            return me.priotiy() - you.priotiy();
        }
    }

}
