import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * User: berdarm
 * Date: 27.09.15
 */
public class FastCollinearPoints {
    private List<LineSegment> segments = new LinkedList<>();

    /**
     * Finds all line segments containing 4 points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("Point array expected");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("Points aren't expected to be null ");
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }

        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        for (Point point : points) {
            Arrays.sort(sortedPoints, point.slopeOrder());

            int streak = 1, i;
            for (i = 2; i < sortedPoints.length; i++) {
                if (sortedPoints[i] == null) break;
                if (sortedPoints[i].slopeTo(point) == sortedPoints[i - 1].slopeTo(point)) {
                    streak++;
                } else {
                    if (streak >= 3) {
                        segments.add(new LineSegment(point, sortedPoints[i - 1]));
                    }
                    streak = 1;
                }
            }
            // to check last streak (because loop finished execution)
            if (streak >= 3) segments.add(new LineSegment(point, sortedPoints[i - 1]));
            sortedPoints[0] = null;
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

}
