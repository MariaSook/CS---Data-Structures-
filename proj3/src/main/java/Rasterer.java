import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */

public class Rasterer {
    private String[][] rendergrid;
    private double ullon;
    private double ullat;
    private double lrlon;
    private double lrlat;
    private double depth;
    private double w;
    private double h;
    private double LonDPP;
    private double rasterullon;
    private double rasterullat;
    private double rasterlrlon;
    private double rasterlrlat;
    private int startTileLeft;
    private int startTileUpper;
    private int endTileRight;
    private int endTileLower;

    public Rasterer() {
    }

    public static void main(String[] args) {
        Rasterer raster = new Rasterer();

        HashMap<String, Double> params = new HashMap<>();
        params.put("lrlon", -122.2104604264636);
        params.put("ullon", -122.30410170759153);
        params.put("w", 1085.0);
        params.put("h", 566.0);
        params.put("ullat", 37.870213571328854);
        params.put("lrlat", 37.8318576119893);

        raster.getMapRaster(params);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     * <li>The tiles collected must cover the most longitudinal distance per pixel
     * (LonDPP) possible, while still covering less than or equal to the amount of
     * longitudinal distance per pixel in the query box for the user viewport size. </li>
     * <li>Contains all tiles that intersect the query bounding box that fulfill the
     * above condition.</li>
     * <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        setValues(params);
        goalDepth(LonDPP);
        buildBox();
        rendergrid = chooseImages();

        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", rendergrid);
        results.put("raster_ul_lon", rasterullon);
        results.put("raster_ul_lat", rasterullat);
        results.put("raster_lr_lon", rasterlrlon);
        results.put("raster_lr_lat", rasterlrlat);
        results.put("depth", (int) depth);
        results.put("query_success", querySuccess());

        return results;
    }

    private void setValues(Map<String, Double> params) {
        this.ullon = params.get("ullon");
        this.ullat = params.get("ullat");

        this.lrlon = params.get("lrlon");
        this.lrlat = params.get("lrlat");
        this.w = params.get("w");
        this.h = params.get("h");
        this.LonDPP = lonDPP(lrlon, ullon, w);
        System.out.println(params.values());
    }

    private boolean querySuccess() {
        if (ullon < MapServer.ROOT_ULLON || lrlon > MapServer.ROOT_LRLON
                || ullat > MapServer.ROOT_ULLAT || lrlat < MapServer.ROOT_LRLAT) {
            return false;
        }
        if (ullon > lrlon || ullat < lrlat) {
            return false;
        }
        return true;
    }

    private void goalDepth(double londpp) {
        for (int i = 0; i < 7; i++) {
            if (lonDPPDepth(i) <= londpp) {
                System.out.println("for loop tracker: " + i);
                this.depth = i;
                break;
            }
        }
        if (lonDPPDepth(7) >= londpp || lonDPPDepth(8) >= londpp) {
            this.depth = 8;
        }
    }

    private double lonDPP(double lrLon, double ulLon, double width) {
        System.out.println("lrlon: " + lrLon);
        System.out.println("ulLon: " + ulLon);
        System.out.println("width: " + width);
        return (lrLon - ulLon) / width;
    }

    private double lonDPPDepth(int nodedepth) {
        double worldlon = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double tilelon = worldlon / Math.pow(2, nodedepth) / 256;

        System.out.println("lonDPPDepth (nodepath): " + tilelon);
        return tilelon;
    }

    private void buildBox() {
        double worldLon = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double worldLat = Math.abs(MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT);
        double numTilesWide = Math.pow(2, depth);

        double lonTileLength = worldLon / numTilesWide;
        double latTileLength = worldLat / numTilesWide;

        double queryLonLeft = Math.abs(ullon - MapServer.ROOT_ULLON);
        double queryLonRight = Math.abs(lrlon - MapServer.ROOT_ULLON);

        double queryLatUpper = Math.abs(ullat - MapServer.ROOT_ULLAT);
        double queryLatLower = Math.abs(lrlat - MapServer.ROOT_ULLAT);

        this.startTileLeft = (int) ((queryLonLeft / worldLon) * numTilesWide);
        this.endTileRight = (int) ((queryLonRight / worldLon) * numTilesWide);

        this.startTileUpper = (int) ((queryLatUpper / worldLat) * numTilesWide);
        this.endTileLower = (int) ((queryLatLower / worldLat) * numTilesWide);

        valueCheck();

        this.rasterullon = MapServer.ROOT_ULLON + (lonTileLength * startTileLeft); //wrong
        this.rasterlrlon = MapServer.ROOT_ULLON + (lonTileLength * endTileRight); //wrong

        this.rasterullat = MapServer.ROOT_ULLAT + (latTileLength * startTileUpper); //wrong
        this.rasterlrlat = MapServer.ROOT_ULLAT + (latTileLength * endTileLower); //wrong
    }

    private void valueCheck() {
        if (startTileLeft > Math.pow(2, depth) - 1) {
            startTileLeft = (int) (Math.pow(2, depth) - 1);
        }
        if (startTileUpper > Math.pow(2, depth) - 1) {
            startTileUpper = (int) (Math.pow(2, depth) - 1);
        }
        if (endTileRight > Math.pow(2, depth) - 1) {
            endTileRight = (int) (Math.pow(2, depth) - 1);
        }
        if (endTileLower > Math.pow(2, depth) - 1) {
            endTileLower = (int) (Math.pow(2, depth) - 1);
        }

        if (startTileLeft < 0) {
            startTileLeft = 0;
        }
        if (startTileUpper < 0) {
            startTileUpper = 0;
        }
        if (endTileRight < 0) {
            endTileRight = 0;
        }
        if (endTileLower < 0) {
            endTileLower = 0;
        }
    }

    private String[][] chooseImages() {
        int cols = Math.abs(endTileRight - startTileLeft) + 1; //x-val
        int rows = Math.abs(endTileLower - startTileUpper) + 1; //y-val

        System.out.println("depth: " + depth);

        String[][] returnval = new String[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                returnval[row][col] = "d" + (int) (depth) + "_x"
                        + (col + startTileLeft) + "_y" + (row + startTileUpper) + ".png";
            }
        }
        return returnval;
    }

}
