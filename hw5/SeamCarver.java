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


        return xdiff + ydiff;
    }


    private double[][] setEnergyMap() {
        double[][] energymap = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                energymap[row][col] = energy(col, row);
            }
        }
        return energymap;
    }


    private double minCostHelper(int row, int col) {
        if (col == 0) {
            double min2 = minCost[row - 1][col];
            double min3 = minCost[row - 1][col + 1];

            return Math.min(min2, min3);
        } else if (col == width - 1) {
            double min1 = minCost[row - 1][col - 1];
            double min2 = minCost[row - 1][col];

            return Math.min(min2, min1);
        } else {
            double min1 = minCost[row - 1][col - 1];
            double min2 = minCost[row - 1][col];
            double min3 = minCost[row - 1][col + 1];

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
        this.minCost = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0) {
                    minCost[row][col] = energyMap[row][col];
                } else {
                    double mincostval = minCostHelper(row, col);
                    minCost[row][col] = energyMap[row][col] + mincostval;
                }
            }
        }
        return minCost;
    }

    private int findMinBottom() {
        int colValStart = Integer.MAX_VALUE;
        double minEnergy = Double.MAX_VALUE;
        for (int col = 0; col < width; col++) {
            if (minCost[height - 1][col] < minEnergy) {
                colValStart = col;
                minEnergy = minCost[height - 1][col];
            }
        }
        return colValStart;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] ints = new int[4];
        return ints;
    }


    private void rowZeroCheck(int rowcurr, int colcurr) {
        if (colcurr == 0) {
            double min2 = minCost[rowcurr][colcurr];
            double min3 = minCost[rowcurr][colcurr + 1];

            if (min2 > min3) {
                //System.out.println(colcurr + 1);
                returnValsVert.push(colcurr + 1);
            } else if (min3 > min2) {
                //System.out.println(colcurr);
                returnValsVert.push(colcurr);
            }
        } else if (colcurr == width - 1) {
            double min1 = minCost[rowcurr][colcurr];
            double min2 = minCost[rowcurr][colcurr - 1];

            if (min2 > min1) {
                //System.out.println(colcurr);
                returnValsVert.push(colcurr);
            } else if (min1 > min2) {
                //System.out.println(colcurr - 1);
                returnValsVert.push(colcurr - 1);
            }
        } else {
            double min1 = minCost[rowcurr][colcurr - 1];
            double min2 = minCost[rowcurr][colcurr];
            double min3 = minCost[rowcurr][colcurr + 1];

            if (min2 > min1 && min3 > min1) {
                //System.out.println(colcurr - 1);
                returnValsVert.push(colcurr - 1);
            } else if (min1 > min2 && min3 > min2) {
                //System.out.println(colcurr);
                returnValsVert.push(colcurr);
            } else if (min2 > min3 && min1 > min3) {
                //System.out.println(colcurr + 1);
                returnValsVert.push(colcurr + 1);
            }
        }
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int yValStart = findMinBottom();
        this.returnValsVert.push(yValStart);

        int rowcurr = height - 2;
        int colcurr = yValStart;

        while (rowcurr > -1) {
            if (rowcurr == 0) {
                rowZeroCheck(rowcurr, colcurr);
                break;
            }

            if (colcurr == 0) {
                double min2 = minCost[rowcurr - 1][colcurr];
                double min3 = minCost[rowcurr - 1][colcurr + 1];

                if (min2 > min3) {
                    //System.out.println(colcurr + 1);
                    returnValsVert.push(colcurr + 1);
                    rowcurr -= 1;
                } else if (min3 > min2) {
                    //System.out.println(colcurr);
                    returnValsVert.push(colcurr);
                    colcurr += 1;
                    rowcurr += 1;
                }
            } else if (colcurr == width - 1) {
                double min1 = minCost[rowcurr - 1][colcurr];
                double min2 = minCost[rowcurr - 1][colcurr - 1];

                if (min2 > min1) {
                    //System.out.println(colcurr);
                    returnValsVert.push(colcurr);
                    rowcurr -= 1;
                } else if (min1 > min2) {
                    //System.out.println(colcurr - 1);
                    returnValsVert.push(colcurr - 1);
                    colcurr -= 1;
                    rowcurr -= 1;
                }
            } else {
                double min1 = minCost[rowcurr - 1][colcurr - 1];
                double min2 = minCost[rowcurr - 1][colcurr];
                double min3 = minCost[rowcurr - 1][colcurr + 1];

                if (min2 > min1 && min3 > min1) {
                    //System.out.println(colcurr - 1);
                    returnValsVert.push(colcurr - 1);
                    colcurr -= 1;
                    rowcurr -= 1;
                } else if (min1 > min2 && min3 > min2) {
                    //System.out.println(colcurr);
                    returnValsVert.push(colcurr);
                    rowcurr -= 1;
                } else if (min2 > min3 && min1 > min3) {
                    //System.out.println(colcurr + 1);
                    returnValsVert.push(colcurr + 1);
                    colcurr += 1;
                    rowcurr -= 1;
                }
            }

        }

        int[] returnColVals = new int[returnValsVert.size()];

        int index = 0;
        while (!returnValsVert.empty()) {
            int popval = (int) returnValsVert.pop();

            System.out.println(popval);
            returnColVals[index] = popval;
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
