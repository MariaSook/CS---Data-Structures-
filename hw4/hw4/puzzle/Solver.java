package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Solver {
    protected MinPQ nodes;
    protected Set<WorldState> seen;
    protected Stack<WorldState> returnvals;


    public Solver(WorldState initial) {
        SearchNode initialSN = new SearchNode(initial, 0, null);
        SNComparator comp = new SNComparator();
        this.nodes = new MinPQ(comp);
        this.seen = new HashSet<>();
        this.returnvals = new Stack();

        nodes.insert(initialSN);
        seen.add(initial);
        solverHelper();
    }

    private void findPrevious(SearchNode curr) {
        if (curr.previous == null) {
            returnvals.push(curr.ws);
            return;
        }
        returnvals.push(curr.ws);
        findPrevious(curr.previous);
    }

    private void solverHelper() {
        SearchNode curr = (SearchNode) nodes.delMin();
        WorldState currws = curr.returnws();
        if (currws.isGoal()) {
            findPrevious(curr);
            return;
        } else {
            for (WorldState n : currws.neighbors()) {
                if (!seen.contains(n) && !n.equals(curr.previous)) {
                    SearchNode me = new SearchNode(n, curr.moves + 1, curr);
                    nodes.insert(me);
                }
                seen.add(n);
            }

            solverHelper();
        }
    }

    public int moves() {
        return returnvals.size() - 1;
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
            return moves + ws.estimatedDistanceToGoal();
        }

        private WorldState returnws() {
            return ws;
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
