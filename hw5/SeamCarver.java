import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private final Picture picture;
    private int width;
    private int height;
    private int xpixplus;
    private int xpixminus;
    private int ypixplus;
    private int ypixminus;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        this.width = width;
        return picture.width();
    }

    // height of current picture
    public int height() {
        this.height = height;
        return picture.height();
    }

    private void valueCheck(int x, int y) {
        if (x == 0) {
            xpixminus = width - 1;
        }
        if (x == width - 1) {
            xpixplus = 0;
        }
        if (y == 0) {
            ypixminus = height - 1;
        }
        if (y == height - 1) {
            ypixplus = 0;
        }
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        this.xpixminus = x - 1;
        this.xpixplus = x + 1;
        this.ypixminus = y - 1;
        this.ypixplus = y + 1;
        valueCheck(x, y);

        Color x1color = picture.get(xpixminus, y);
        Color x2color = picture.get(xpixplus, y);

        Color y1color = picture.get(x, ypixminus);
        Color y2color = picture.get(x, ypixplus);

        int x1blue = x1color.getBlue();
        int x1red = x1color.getRed();
        int x1green = x1color.getGreen();

        int x2blue = x2color.getBlue();
        int x2red = x2color.getRed();
        int x2green = x2color.getGreen();

        int y1blue = y1color.getBlue();
        int y1red = y1color.getRed();
        int y1green = y1color.getGreen();

        int y2blue = y2color.getBlue();
        int y2red = y2color.getRed();
        int y2green = y2color.getGreen();

        int xbluebreak = x2blue - x1blue;
        int xredbreak = x2red - x1red;
        int xgreenbreak = x2green - x1green;

        double xdiff = Math.pow(xbluebreak, 2) +
                Math.pow(xredbreak, 2) +
                Math.pow(xgreenbreak, 2);

        int ybluebreak = y2blue - y1blue;
        int xyredbreak = y2red - y1red;
        int xygreenbreak = x2green - x1green;

        double ydiff = Math.pow(ybluebreak, 2) +
                Math.pow(xyredbreak, 2) +
                Math.pow(xgreenbreak, 2);

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
