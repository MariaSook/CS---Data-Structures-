import java.util.ArrayList;
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
    private boolean querysuccess;
    private double numImages;
    private double k;
    private double LonDPP;
    private ArrayList x;
    private ArrayList y;
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

        double londpp = raster.lonDPP(122.2104604264636, 122.30410170759153, 1085);
        System.out.print(raster.goalDepth(londpp));


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
        results.put("depth", goalDepth(LonDPP));
        results.put("query_success", true);
        return results;
    }

    private void setValues(Map<String, Double> params) {
        this.ullon = params.get("ullon");
        this.ullat = params.get("ullat");
        this.lrlon = params.get("lrlon");
        this.lrlon = params.get("lrlat");
        this.w = params.get("w");
        this.h = params.get("h");
        this.LonDPP = lonDPP(lrlon, ullon, w);
        this.depth = goalDepth(LonDPP);
    }

    private int goalDepth(double lonDPP) {
        for (int i = 0; i < 8; i++) {
            if (lonDPPDepth(i) <= lonDPP) {
                this.depth = i;
                this.numImages = Math.pow(4, i);
                this.k = Math.pow(2, i) - 1;
                return i;
            }
        }
        this.depth = 7;
        this.numImages = Math.pow(4, 7);
        this.k = Math.pow(2, 7) - 1;
        return 7;
    }

    private double lonDPP(double lrlon, double ullon, double width) {
        return Math.abs((lrlon - ullon) / width);
    }

    private double lonDPPDepth(int nodedepth) {
        //More generally, at the Dth level of zoom, there are 4^D images,
        // with names ranging from dD_x0_y0 to dD_xk_yk, where k is 2^D - 1.
        double worldlon = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double tilelon = worldlon / Math.pow(2, nodedepth);

        return tilelon / 256;
    }


    //total width of world, then dist from left to query
    //.2/.5 *2 (num tiles) = .8 -- then round down
    //

    private void buildBox() {
        double worldLon = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double worldLat = Math.abs(MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT);

        double eachTileLon = worldLon / Math.pow(2, depth);
        double eachTileLat = worldLat / Math.pow(2, depth);

        double queryLonLeft = Math.abs(ullon - MapServer.ROOT_ULLON);
        double queryLonRight = Math.abs(lrlon - MapServer.ROOT_LRLON);
        double queryLatUpper = Math.abs(ullat - MapServer.ROOT_ULLAT);
        double queryLatLower = Math.abs(lrlat - MapServer.ROOT_LRLAT);

        this.startTileLeft = (int) ((queryLonLeft / worldLon) * Math.pow(2, depth));
        this.startTileUpper = (int) ((queryLatUpper / worldLat) * Math.pow(2, depth));
        this.endTileRight = (int) ((queryLonRight / worldLon) * Math.pow(2, depth));
        this.endTileLower = (int) ((queryLatLower / worldLat) * Math.pow(2, depth));

        this.rasterullon = MapServer.ROOT_ULLON + eachTileLon * startTileLeft;
        this.rasterullat = MapServer.ROOT_ULLAT + eachTileLat * startTileUpper;
        this.rasterlrlon = MapServer.ROOT_LRLON + eachTileLon * endTileRight;
        this.rasterlrlat = MapServer.ROOT_ULLON + eachTileLat * endTileLower;
    }

    private String[][] chooseImages() {
        String[][] returnval = new String[(endTileRight-startTileLeft)][(endTileLower-startTileUpper)];

        for (int row = startTileLeft; row < endTileRight; row++) {
            for (int col = startTileUpper; col < endTileLower; col++) {
                returnval[row][col] = "d" + depth + "_x" + row + "_y" + col + ".png";
            }
        }
        return returnval;
    }

}
