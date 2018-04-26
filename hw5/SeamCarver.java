import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

import java.util.Stack;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private int xpixplus;
    private int xpixminus;
    private int ypixplus;
    private int ypixminus;
    private double[][] energyMap;
    private double[][] minCost;
    private Stack returnValsVert;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        this.energyMap = setEnergyMap();
        this.minCost = setMinCost();
        this.returnValsVert = new Stack();
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
        if (x < 0 || y < 0 || x > height - 1 || y > width - 1) {
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

        return xdiff + ydiff;
    }

    private double[][] setEnergyMap() {
        double[][] energymap = new double[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                energymap[x][y] = energy(x, y);
            }
        }
        return energymap;
    }

    private double minCostHelper(int x, int y) {
        if (y == 0) {
            double min2 = minCost[x][y - 1];
            double min3 = minCost[x + 1][y - 1];

            return Math.min(min2, min3);
        } else if (y == width - 1) {
            double min1 = minCost[x - 1][y - 1];
            double min2 = minCost[x][y - 1];

            return Math.min(min2, min1);
        } else {
            double min1 = minCost[x - 1][y - 1];
            double min2 = minCost[x][y - 1];
            double min3 = minCost[x + 1][y - 1];

            double smallest = min1;
            if (smallest > min2) {
                smallest = min2;
            }
            if (smallest > min3) {
                smallest = min3;
            }
            return smallest;
        }
    }

    private double[][] setMinCost() {
        double[][] minCost = new double[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (x == 0) {
                    minCost[x][y] = energyMap[x][y];
                } else {
                    double mincostval = minCostHelper(x, y);
                    minCost[x][y] = energyMap[x][y] + mincostval;
                }
            }
        }
        return minCost;
    }

    private int findMinBottom() {
        int yValStart = Integer.MAX_VALUE;
        double minEnergy = Double.MAX_VALUE;
        for (int y = 0; y < width; y++) {
            if (minCost[height - 1][y] < minEnergy) {
                yValStart = y;
                minEnergy = minCost[height - 1][y];
            }
        }
        return yValStart;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] ints = new int[4];
        return ints;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int yValStart = findMinBottom();
        this.returnValsVert.push(yValStart);

        int xcurr = height - 2;
        int ycurr = yValStart;

        while (xcurr > -1) {
            if (ycurr == 0) {
                double min2 = minCost[xcurr][ycurr - 1];
                double min3 = minCost[xcurr + 1][ycurr - 1];

                double minVal = Math.min(min2, min3);

                if (min2 > min3) {
                    returnValsVert.push(min2);
                    ycurr -= 1;
                } else if (min3 > min2) {
                    returnValsVert.push(min3);
                    ycurr -= 1;
                    xcurr += 1;
                }
            } else if (ycurr == width - 1) {
                double min1 = minCost[xcurr - 1][ycurr - 1];
                double min2 = minCost[xcurr][ycurr - 1];

                double minVal = Math.min(min2, min2);

                if (min2 > min2) {
                    returnValsVert.push(min2);
                    ycurr -= 1;
                } else if (min2 > min2) {
                    returnValsVert.push(min2);
                    ycurr -= 1;
                    xcurr -= 1;
                }
            } else {
                double min1 = minCost[xcurr - 1][ycurr - 1];
                double min2 = minCost[xcurr][ycurr - 1];
                double min3 = minCost[xcurr + 1][ycurr - 1];

                if (min1 < min2 && min1 < min3) {
                    returnValsVert.push(min1);
                    ycurr -= 1;
                    xcurr -= 1;
                } else if (min2 < min1 && min2 < min3) {
                    returnValsVert.push(min2);
                    ycurr -= 1;
                } else if (min3 < min2 && min3 < min1) {
                    returnValsVert.push(min3);
                    ycurr -= 1;
                    xcurr += 1;
                }
            }

        }
        int[] returnColVals = new int[returnValsVert.size()];

        int index = 0;
        while (!returnValsVert.empty()) {
            returnColVals[index] = (int) returnValsVert.pop();
            index += 1;
        }

        return returnColVals;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
    }
}
