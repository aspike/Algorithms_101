package Week3;

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
        if (points == null) throw new NullPointerException("Week3.Point array expected");
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
            int streakBegin = -1;

            /* <= length  because otherwise we would write else block once more after loop
             to handle last possible streak in array including last element */
            for (int i = 2; i <= sortedPoints.length; i++) {
                if (i != sortedPoints.length
                        && sortedPoints[i].slopeTo(point) == sortedPoints[i - 1].slopeTo(point)) {
                    if (streakBegin == -1) streakBegin = i - 1;
                } else {
                    if (streakBegin != -1 && i - streakBegin >= 3) {
                        Arrays.sort(sortedPoints, streakBegin, i);
                        if (point.compareTo(sortedPoints[i - 1]) < 0) {
                            streakBegin = -1;
                            continue;
                        }
                        segments.add(new LineSegment(point, sortedPoints[streakBegin]));
                    }
                    streakBegin = -1;
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
        for (int i = 0; i < tempSegm.length; i++) {
            tempSegm[i] = segments.get(i);
        }
        return tempSegm;
    }
}