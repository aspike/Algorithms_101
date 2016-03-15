package Week5;

import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berdar on 13.03.16.
 */
public class KdTree {

    private static class Node {
        private static final boolean HORIZONTAL = false;
        private static final boolean VERTICAL = true;
        private final RectHV rect;
        private final boolean orientation;
        private Point2D point;
        private Node left, right;
        private int size = 1;

        public Node(Point2D point, boolean orientation, RectHV rect) {
            this.point = point;
            this.orientation = orientation;
            this.rect = rect;
        }
    }

    private Node root;

    /**
     * construct an empty set of points
     */
    public KdTree() {
    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return root == null ? 0 : root.size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p - point to add
     */
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        root = insert(p, root, null);
    }

    /**
     * does the set contain point p?
     *
     * @param p - Point to find
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(p, root);
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p - point to examine
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Node nearest = null;
        if (root != null) nearest = new Node(root.point, false, null);
        nearest(p, root, nearest);
        return nearest == null ? null : nearest.point;
    }


    /**
     * draw all points to standard draw
     */
    public void draw() {
        getAllNodes().forEach(node -> {
            double stdRad = StdDraw.getPenRadius();
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(Color.BLACK);
            node.point.draw();
            StdDraw.setPenRadius(stdRad);
            if (isVertical(node)) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
            }
        });
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rect - rectangle to examine
     * @return iterable of points in rect
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("Rect should not be null");
        ArrayList<Point2D> pointsInRect = new ArrayList<>();
        range(rect, root, pointsInRect);
        return pointsInRect;
    }

    private static void range(final RectHV rect, final Node node, List<Point2D> pointList) {
        if (node != null && node.rect.intersects(rect)) {
            if (rect.contains(node.point)) pointList.add(node.point);
            range(rect, node.left, pointList);
            range(rect, node.right, pointList);
        }
    }


    private void nearest(final Point2D p, final Node node, Node nearest) {
        if (node == null || Double.compare(nearest.point.distanceTo(p), node.rect.distanceTo(p)) < 0) return;
        Node s = new Node(node.point, node.orientation, node.rect);
        if (Double.compare(node.point.distanceTo(p), nearest.point.distanceTo(p)) < 0) {
            nearest.point = node.point;
        }

        if ((isVertical(node) && Double.compare(node.point.x(), p.x()) > 0)
                || (!isVertical(node) && Double.compare(node.point.y(), p.y()) > 0)) {
            nearest(p, node.left, nearest);
            nearest(p, node.right, nearest);
        } else {
            nearest(p, node.right, nearest);
            nearest(p, node.left, nearest);
        }
    }

    private static boolean isVertical(final Node node) {
        return node.orientation == Node.VERTICAL;
    }


    private Node insert(final Point2D p, final Node node, Node parent) {
        // found place to insert
        if (node == null) {
            if (parent == null) {
                return new Node(p, Node.VERTICAL, new RectHV(0d, 0d, 1d, 1d));
            } else {
                RectHV rect, parentRect = parent.rect;
                if (isVertical(parent)) {
                    if (Double.compare(parent.point.x(), p.x()) > 0) {          // left son (left rect)
                        rect = new RectHV(parentRect.xmin(), parentRect.ymin(), parent.point.x(), parentRect.ymax());
                    } else {                                                    // right son (right rect)
                        rect = new RectHV(parent.point.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
                    }
                } else {
                    if (Double.compare(parent.point.y(), p.y()) > 0) {          // left (lower rect)
                        rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.point.y());
                    } else {                                                    // right (upper rect)
                        rect = new RectHV(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(), parent.rect.ymax());
                    }
                }
                return new Node(p, !parent.orientation, rect);
            }
        }

        // searching for place to insert
        if (p.equals(node.point)) return node;
        int cmp = isVertical(node) ?
                Double.compare(node.point.x(), p.x()) :
                Double.compare(node.point.y(), p.y());

        if (cmp > 0) node.left = insert(p, node.left, node);
        else if (cmp <= 0) node.right = insert(p, node.right, node);

        node.size = nodeSize(node);
        return node;
    }

    private static int nodeSize(Node node) {
        return (node.left == null ? 0 : node.left.size) + (node.right == null ? 0 : node.right.size) + 1;
    }

    private boolean contains(Point2D p, Node node) {
        if (node == null) return false;

        if (p.equals(node.point)) return true;

        int cmp = isVertical(node) ?
                Double.compare(node.point.x(), p.x()) :
                Double.compare(node.point.y(), p.y());

        if (cmp > 0) return contains(p, node.left);
        else return contains(p, node.right);
    }

    private List<Node> getAllNodes() {
        ArrayList<Node> list = new ArrayList<>();
        getNodes(root, list);
        return list;
    }

    private static void getNodes(Node node, List<Node> list) {
        if (node == null) return;
        list.add(node);
        getNodes(node.left, list);
        getNodes(node.right, list);
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
    }
}

