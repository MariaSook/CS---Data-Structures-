//import com.sun.tools.jdeps.Graph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    private static PriorityQueue fringe = new PriorityQueue();
    private static HashMap<Long, Double> best = new HashMap();
    private Stack returnvals = new Stack();
    private static HashSet seen = new HashSet();
    GraphDB g;


    public Router() {
    }

    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        class SearchNode implements Comparator {
            long id;
            double lat;
            double lon;
            HashSet neighbors;
            GraphDB g;
            double priority;
            SearchNode parent;

            private SearchNode(GraphDB g, long id, SearchNode parent,
                               double priority) {
                this.g = g;
                this.id = id;
                this.lat = g.lat(id);
                this.lon = g.lon(id);
                this.neighbors = (HashSet) g.adjacent(id);
                this.parent = parent;
                this.priority = priority;
            }

            @Override
            public int compare(Object obj1, Object obj2) {
                SearchNode me = (SearchNode) obj1;
                SearchNode you = (SearchNode) obj2;

                if (me.priority > you.priority) {
                    return -1;
                } else if (me.priority < you.priority) {
                    return 1;
                }
                return 0;
            }
        }
        long startIndex = g.closest(stlon, stlat);
        long endIndex = g.closest(destlon, destlat);
        double startPriority  = heuristic(g, destlon, destlat, startIndex);

        SearchNode first = new SearchNode(g, startIndex, null, startPriority);
        fringe.add(first);
        seen.add(first);
        best.put(startIndex, startPriority);

        SearchNode curr = (SearchNode) fringe.poll();
        long currid = curr.id;

        while (curr.id != endIndex) {
            for (long id: g.adjacent(currid)) {
                if (!seen.contains(id) || this path is shorter than what you currently have) {
                    relax(curr.id, id, stlon, stlat, destlon, destlat, g);
                    double priority = (double) best.get(id);
                    SearchNode me = new SearchNode(g, id, curr, priority);
                    fringe.add(me);
                    seen.add(me);
                }
            }
            curr = (SearchNode) fringe.poll();
            currid = curr.id;
        }


    }


    private static double priority(GraphDB g, double stlon, double stlat,
                                   double destlon, double destlat, long vMe,
                                   long vYou) {
        double meLon = g.lon(vMe);
        double meLat = g.lat(vMe);
        double youLon = g.lon(vYou);
        double youLat = g.lat(vYou);

        double distStart = g.distance(stlon, stlat, meLon, meLat);
        double distMetoYou = g.distance(meLon, meLat, youLon, youLat);
        double heuristic = heuristic(g, destlon, destlat, vYou);

        return distStart + distMetoYou + heuristic;
    }

    private static double heuristic(GraphDB g, double destlon, double
                                    destlat, long v) {
        double meLon = g.lon(v);
        double meLat = g.lat(v);

        double heuristic = g.distance(meLon, meLat, destlon, destlat);
        return heuristic;
    }

    private static void relax(long v, long w, double stlon, double stlat, double destlon,
                              double destlat, GraphDB g) {
        double lonvOne = g.lon(v);
        double latvOne= g.lat(v);

        double lonvTwo = g.lon(w);
        double latvTwo = g.lat(w);

        double distStartToOne = g.distance(stlon, stlat, lonvOne, latvOne);
        double distCurrToNext = g.distance(lonvOne, latvOne, lonvTwo, latvTwo);

        double newBest = distCurrToNext + distStartToOne;

        if (newBest < best.get(w)) {
            best.get(w) = newBest;

            double priority = priority(g, stlon, stlat, destlon, destlat, v, w);
            fringe.put(w);
        }
    }

    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);

        }



    }
}
