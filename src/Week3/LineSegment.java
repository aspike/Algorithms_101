package Week3;

/**
 * User: berdarm
 * Date: 27.09.15
 */
public class LineSegment {
    private final Point p;
    private final Point q;

    /**
     * Constructs the line segment between points p and q
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) throw new NullPointerException();
        this.p = p;
        this.q = q;
    }

    /**
     * Draws this line segment
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * @return string representation
     */
    public String toString() {
        return p.toString() + " -- " + q.toString();
    }
}
