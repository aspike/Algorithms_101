/**
 * User: berdarm
 * Date: 27.09.15
 */
public class LineSegment {
    private Point p, q;

    /**
     * Constructs the line segment between points p and q
     */
    public LineSegment(Point p, Point q) {
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
