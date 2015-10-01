import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * User: berdarm
 * Date: 27.09.15
 */
public class Point implements Comparable<Point> {
    private int x, y;


    /**
     * Constructs the point (x, y)
     *
     * @param x = x-coordinate
     * @param y = y-coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment from this point to that point
     *
     * @param that - Point that should be connected with this
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * @return string representation
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    /**
     * Compare two points by y-coordinates, breaking ties by x-coordinates
     *
     * @param that - point to compare with
     * @return -1, 0, 1 if less, equal, greater then that
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return Integer.MIN_VALUE;
        else if (this.y > that.y) return Integer.MAX_VALUE;
        else {
            if (this.x > that.x) return 1;
            else if (this.x < that.x) return -1;
            else return 0;
        }
    }

    /**
     * @param that
     * @return the slope between this point and that point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            else return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) return 0d;

        return (((double) that.y) - ((double) this.y)) / (((double) that.x) - ((double) this.x));
    }

    /**
     * @return compare two points by slopes they make with this point
     */
    public Comparator<Point> slopeOrder() {
        return (p, q) -> {
            if (p == null && q == null) return 0;
            else if (q == null) return -1;
            else if (p == null) return 1;
            double pSlope = p.slopeTo(this);
            double qSlope = q.slopeTo(this);

            if (pSlope > qSlope) return 1;
            else if (pSlope < qSlope) return -1;
            else return 0;
//            else {
//                // do this in order to get point with same slope be reverse-sorted with respect to distance from this point
//                double pDistance = distance(p, this);
//                double qDistance = distance(q, this);
//
//                if (pDistance > qDistance) return 1;
//                else if (pDistance < qDistance) return -1;
//                else return 0;
//            }
        };
    }

    private double distance(Point p, Point q) {
        return Math.sqrt((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y));
    }

}
