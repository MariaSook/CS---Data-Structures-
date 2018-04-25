import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

//import java.util.ArrayList;
//import java.util.HashSet;
import java.util.HashMap;


public class SeamCarver {
    private final Picture picture;
    private int width;
    private int height;
    private int xpixplus;
    private int xpixminus;
    private int ypixplus;
    private int ypixminus;
    private HashMap energyMap;


    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        this.energyMap = new HashMap();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private int xvalueCheck(int value) {
        if (value < 0) {
            return width - 1;
        }
        if (value > width - 1) {
            return 0;
        }
        return value;
    }

    private int yValueCheck(int value) {
        if (value < 0) {
            return height - 1;
        }
        if (value > height - 1) {
            return 0;
        }
        return value;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > width - 1 || y > height - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        this.xpixminus = xvalueCheck(x - 1);
        this.xpixplus = xvalueCheck(x + 1);

        Color x1color = picture.get(xpixminus, y);
        Color x2color = picture.get(xpixplus, y);

        int x1blue = x1color.getBlue();
        int x1red = x1color.getRed();
        int x1green = x1color.getGreen();

        int x2blue = x2color.getBlue();
        int x2red = x2color.getRed();
        int x2green = x2color.getGreen();

        int xbluebreak = x2blue - x1blue;
        int xredbreak = x2red - x1red;
        int xgreenbreak = x2green - x1green;

        double xdiff = (xbluebreak * xbluebreak)
                + (xredbreak * xredbreak)
                + (xgreenbreak * xgreenbreak);

        this.ypixminus = yValueCheck(y - 1);
        this.ypixplus = yValueCheck(y + 1);

        Color y1color = picture.get(x, ypixminus);
        Color y2color = picture.get(x, ypixplus);

        int y1blue = y1color.getBlue();
        int y1red = y1color.getRed();
        int y1green = y1color.getGreen();

        int y2blue = y2color.getBlue();
        int y2red = y2color.getRed();
        int y2green = y2color.getGreen();

        int ybluebreak = y2blue - y1blue;
        int yredbreak = y2red - y1red;
        int ygreenbreak = y2green - y1green;

        double ydiff = (ybluebreak * ybluebreak)
                + (yredbreak * yredbreak)
                + (ygreenbreak * ygreenbreak);

        String label = "" + x + "," + y + "";
        energyMap.put(label, (xdiff + ydiff));
        return xdiff + ydiff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] ints = new int[4];
        return ints;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] ints = new int[4];
        return ints;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
    }
}
