import java.util.LinkedList;
import java.util.List;

/**
 * User: berdarm
 * Date: 27.09.15
 */
public class BruteCollinearPoints {
    private List<LineSegment> segments = new LinkedList<>();

    /**
     * Finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("Point array expected");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("Points aren't expected to be null ");
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }


        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                double pq = p.slopeTo(q);
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double pr = p.slopeTo(r);
                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];

                        if (pq == pr && pr == p.slopeTo(s)) {
                            segments.add(new LineSegment(minPoint(p, q, r, s), maxPoint(p, q, r, s)));
                        }
                    }
                }
            }
        }
    }

    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] tempSegm = new LineSegment[segments.size()];
        for (int i = 0; i < tempSegm.length; i++) tempSegm[i] = segments.get(i);
        return tempSegm;
    }

    private static Point maxPoint(Point... points) {
        Point max = null;
        for (Point point : points) {
            if (max == null) max = point;
            if (point.compareTo(max) > 0) max = point;
        }
        return max;
    }

    private static Point minPoint(Point... points) {
        Point min = null;
        for (Point point : points) {
            if (min == null) min = point;
            if (point.compareTo(min) < 0) min = point;
        }
        return min;
    }
}
