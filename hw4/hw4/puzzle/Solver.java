package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Set;

public class Solver {
    private MinPQ nodes;
    private int moves;
    private Set<WorldState> seen;

    public Solver(WorldState initial) {
        SearchNode initialSN = new SearchNode(initial, 0, null);
        //this.nodes = new MinPQ(SNComparator<SearchNode>);
        this.nodes = new MinPQ();

        nodes.insert(initialSN);

        solverHelper();
    }

    private void solverHelper() {
        SearchNode curr = (SearchNode) nodes.delMin();
        WorldState currws = (WorldState) curr.returnws();
        seen.add(currws);
        if (currws.isGoal()) {
            this.moves = curr.moves;
            return;
        } else {
            for (WorldState n: currws.neighbors()) {
                if (!seen.contains(n)) {
                    SearchNode me = new SearchNode(n, curr.moves + 1, curr);
                    nodes.insert(me);
                }
            }
            solverHelper();
        }
    }

    public int moves() {
      return this.moves;
    }

    public Iterable<WorldState> solution() {
        return null;
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
            SearchNode me = (SearchNode) obj1;
            SearchNode you = (SearchNode) obj2;

            return me.priotiy() - you.priotiy();
        }
    }
}
