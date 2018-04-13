import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    //input an upper left latitude and longitude, a lower right
    // latitude and longitude, a window width, and a window height.
    // Using these six numbers, it will produce a 2D array of filenames
    // corresponding to the files to be rendered

    private String[][] render_grid;
    private Map params;
    private double ullon;
    private double ullat;
    private double lrlon;
    private double lrlat;
    private double depth;
    private double w;
    private double h;
    private boolean query_success;
    private double LonDPP;


    //returns String[][] that corresponds to the files that should
    // be displayed in response to this query.

    //LonDPP = (lower right lat - upper left long)/width of image (or box) in pixels

    //images in String[][] must include any region of query box
    //have greatest LonDPP that is less than or equal to the LonDPP
    // of the query box (as zoomed out as possible). If the requested
    // LonDPP is less than what is available in the data files, you should
    // use the lowest LonDPP available instead (i.e. depth 7 images).

    //nput an upper left latitude and longitude, a lower right
    // latitude and longitude, a window width, and a window height.

    public Rasterer() {

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        setValues(params);
        //this.render_grid = calculateFiles(params);
        Map<String, Object> results = new HashMap<>();

        return results;
    }

    private void setValues(Map<String, Double> params) {
        this.ullon = params.get("ullon");
        this.ullat = params.get("ullat");
        this.lrlon = params.get("lrlon");
        this.lrlon = params.get("lrlat");
        this.w = params.get("w");
        this.h = params.get("h");
        this.LonDPP = (lrlat - ullon)/w;
    }

//    private String[][] calculateFiles(Map<String, Double> params) {
//
//    }

    private int findDepth(String name) {
        String s = "" + name.charAt(1);
        return Integer.parseInt(s);
    }

    private int returnNumValFromString(String name, char val) {
        ArrayList array = new ArrayList();
        boolean wall = false;
        for (int i = 0; i < name.length(); i ++) {
            if (name.charAt(i) == '_') {
                wall = false;
            }

            if (name.charAt(i) == val) {
                wall = true;
            }
            else if (wall == true) {
                array.add(name.charAt(i));
            }
        }
        int returnval = arrayToInt(array);
        return returnval;
    }

    private int arrayToInt(ArrayList list) {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += list.get(i);
        }
        return Integer.parseInt(s);
    }

    private int numTiles(int width, int height) {
        int num1 = (int) (width/256);
        int num2 = (int) (height/256) + 1;

        return num1*num2;
    }

    public static void main(String[] args) {
        Rasterer raster = new Rasterer();

        String image = "d8_x11111_y2";

        //w=1085.0, h=566.0,

        int width = 1085;
        int height = 566;

        System.out.println(raster.numTiles(width, height));



    }



}
