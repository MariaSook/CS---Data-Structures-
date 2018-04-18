import example.CSCourseDB;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;


/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */

//will read in the Open Street Map dataset and store it as a
// graph. Each node in the graph will represent a single
// intersection, and each edge will represent a road. How you
// store your graph is up to you.

public class GraphDB {
    /**
     * Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc.
     */
    protected HashMap<Long, Node> nodes = new HashMap<>();
    protected HashMap<Long, Edge> edges = new HashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    void addNode(long id, double lat, double lon, HashMap parent, HashMap childId) {
        Node newNode = new Node(id, lat, lon, parent, childId);
        nodes.put(id, newNode);
    }

    void addEdge(long id) {
        Edge newEdge = new Edge(id);
        edges.put(id, newEdge);
    }

    void removeNode(long id) {
        if (nodes.containsKey(id)) {
            nodes.remove(id);
        }
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */

    protected void clean() {
        Set nodeSet = (Set) nodes.keySet();
        Node[] nodeArray = (Node[]) nodeSet.toArray();
        for (Node n : nodeArray) {
            if (n.neighbors == null) {
                nodes.remove(n.id);
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        ArrayList returnVals = new ArrayList();
        Set nodeSet = (Set) nodes.keySet();
        Node[] nodeArray = (Node[]) nodeSet.toArray();
        for (Node n : nodeArray) {
            returnVals.add(n.id);
        }
        return returnVals;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */

    //fix
    Iterable<Long> adjacent(long v) {
        return nodes.get(v).neighbors;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        Node smallestNode = null;
        double smallestDist = Double.POSITIVE_INFINITY;

        Set nodeSet = (Set) nodes.keySet();
        Node[] nodeArray = (Node[]) nodeSet.toArray();

        for (Node n : nodeArray) {
            double bearingval = bearing(lon, lat, n.lon, n.lat);
            if (bearingval < smallestDist) {
                smallestNode = n;
                smallestDist = bearingval;
            }
        }
        return smallestNode.id;
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(v).lat;
    }

    private class Node {
        long id;
        double lat;
        double lon;
        HashMap parent;
        HashMap child;
        ArrayList neighbors = new ArrayList();
        String name;

        private Node(long id, double lat, double lon, HashMap parent, HashMap child) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.parent = parent;
            this.child = child;
            long cId = (long) child.get("id");
            long pId = (long) parent.get("id");
            addNeighbor(pId, cId);
        }

        private void addNeighbor(long id1, long id2) {
            neighbors.add(id1);
            neighbors.add(id2);
        }
    }

    private class Edge {
        private long id;
        private HashSet nodes;
        private HashMap<String, String> tags;

        private Edge(long id) {
            this.id = id;
            this.nodes = new HashSet();
            this.tags = new HashMap<>();
        }
    }
}
