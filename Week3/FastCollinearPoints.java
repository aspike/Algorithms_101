package Week3;

import java.util.Arrays;

/**
 * User: berdarm
 * Date: 27.09.15
 */
public class FastCollinearPoints {
    //    private List<Week3.LineSegment> segments = new LinkedList<>();
    private LineSegment[] segments;
    private int index = 0;

    /**
     * Finds all line segments containing 4 points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("Week3.Point array expected");

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        for (int i = 1; i < sortedPoints.length; i++) {
            if (sortedPoints[i] == null) throw new NullPointerException("Points aren't expected to be null ");
            if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) throw new IllegalArgumentException();
        }
        segments = new LineSegment[points.length * points.length];


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
                        segments[index++] = new LineSegment(point, sortedPoints[streakBegin]);
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
        return index;
    }

    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOfRange(segments, 0, index);
    }
}