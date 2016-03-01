package Week3;

import Week3.FastCollinearPoints;
import Week3.LineSegment;
import Week3.Point;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * User: berdarm
 * Date: 30.09.15
 */
public class PointsMain {
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
//        Week3.BruteCollinearPoints collinear = new Week3.BruteCollinearPoints(points);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        LineSegment[] segments1 = collinear.segments();
        System.out.println(segments1.length);
        for (LineSegment segment : segments1) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
