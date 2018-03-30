package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Set;
import java.util.HashSet;

public class Solver {
    private MinPQ mpq;
    private Set<WorldState> words;
    private Set<WorldState> smallest;
    private WorldState initial;
    private int moves;

    public Solver(WorldState initial) {
    /*Constructor which solves the puzzle, computing
      everything necessary for moves() and solution() to
      not have to solve the problem again. Solves the
      puzzle using the A* algorithm. Assumes a solution exists.*/
        this.mpq = new MinPQ();
        this.initial = initial;
        this.moves = 0;
        SearchNode sn = new SearchNode(initial, 0, null);
        mpq.insert(sn);
//        words.add(initial);
        smallest.add(initial);

        helper(initial);

       /*Remove the search node with minimum priority.
       Let’s call this node X. If it is the goal node,
       then we’re done. Otherwise, for each neighbor of X’s world
       state, create a new search node that obeys the description
       above and insert it into the priority queue.*/

        //The A* algorithm can also be thought of as “Given a
        // state, pick a neighbor state such that (distance so
        // far + estimated distance to goal) is minimized. Repeat
        // until the goal is seen”.

        //world state
        //estimateddistancetogoal
        //is goal
        ///** Provides an iterable of all the neighbors of this WorldState. */
        //    Iterable<WorldState> neighbors();


    }

    private void helper(WorldState world) {
        Iterable<WorldState> neighbors = initial.neighbors();
        moves += 1;
        SearchNode minsn = (SearchNode) mpq.delMin();
        WorldState minneighbor = null;
        if (minsn.returnws().isGoal()) {
            return;
        } else {

            for (WorldState w : neighbors) {
                if (w.isGoal()) {
                    minneighbor = w;
                    smallest.add(w);
                    return;
                } else {
                    if (priority(w) < priority(minneighbor)) {
                        minneighbor = w;
                    }
                    //words.add(w);
                }
            }
            SearchNode prev = (SearchNode) mpq.min();
            SearchNode curr = new SearchNode(minneighbor, moves, prev);
            mpq.insert(curr);
            smallest.add(curr.returnws());
            helper(curr.returnws());
        }
    }

    private int priority(WorldState me) {
        //priority = the number of moves made to reach this world state from the initial state +
        // the WorldState’s estimatedDistanceToGoal
        int dist = me.estimatedDistanceToGoal();
        int m = moves;
        return dist + m;
    }

    public int moves() {
        /* Returns the m111inimum number of moves to solve the puzzle starting
        at the initial WorldState.*/
        return moves;
    }

    public Iterable<WorldState> solution() {
       /*Returns a sequence of WorldStates from the initial WorldState
        to the solution.*/
       //An Iterable is a simple representation of a series of elements
        // that can be iterated over. It does not have any iteration
        // state such as a "current element".
        return smallest;
    }

    private class SearchNode {
        private WorldState ws;
        private int moves;
        private SearchNode previous;

        private SearchNode(WorldState ws, int moves, SearchNode previous) {
            this.moves = moves;
            this.ws = ws;
            this.previous = previous;
        }

        private WorldState returnws() {
            return ws;
        }

    }


}
