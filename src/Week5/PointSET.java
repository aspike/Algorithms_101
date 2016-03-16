package Week5;

import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by berdar on 11.03.16.
 */
public class PointSET {
    private Node root;

    private static class Node {
        private static final boolean RED = true;
        private static final boolean BLACK = false;
        private final Point2D point;
        private Node left = null, right = null;
        private boolean color = RED;
        private int size = 1;

        Node(Point2D point) {
            this.point = point;
        }
    }

    /**
     * construct an empty set of points
     */
    public PointSET() {
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
        root = insert(p, root);
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
     * draw all points to standard draw
     */
    public void draw() {
        double stdRad = StdDraw.getPenRadius();
        StdDraw.setPenRadius(.01);
        getAllPoints().forEach(Point2D::draw);
        StdDraw.setPenRadius(stdRad);
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rect - rectangle to examine
     * @return iterable of points in rect
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        return getAllPoints().stream().filter(rect::contains).collect(Collectors.toList());
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p - point to examine
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        List<Point2D> points = getAllPoints();
        if (points.isEmpty()) return null;
        return points.stream()
                .min((p1, p2) -> Double.compare(p1.distanceTo(p), p2.distanceTo(p)))
                .get();
    }

    private Node insert(Point2D p, Node node) {
        if (node == null) return new Node(p);
        int cmp = node.point.compareTo(p);

        if (cmp > 0) node.left = insert(p, node.left);
        else if (cmp < 0) node.right = insert(p, node.right);

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.right) && isRed(node.left)) swapColors(node);

        node.size = nodeSize(node);
        return node;
    }

    private static Node rotateLeft(Node node) {
        assert isRed(node.right);

        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = Node.RED;
        x.size = node.size;
        node.size = nodeSize(node);
        return x;
    }

    private static Node rotateRight(Node node) {
        assert isRed(node.left);

        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = Node.RED;
        x.size = node.size;
        node.size = nodeSize(node);
        return x;
    }

    private static void swapColors(Node node) {
        assert !isRed(node);
        assert isRed(node.right);
        assert isRed(node.left);

        node.color = Node.RED;
        node.left.color = Node.BLACK;
        node.right.color = Node.BLACK;
    }

    private static boolean contains(Point2D p, Node node) {
        if (node == null) return false;
        switch (node.point.compareTo(p)) {
            case 0:
                return true;
            case 1:
                return contains(p, node.left);
            case -1:
                return contains(p, node.right);
        }
        return false;
    }

    private List<Point2D> getAllPoints() {
        ArrayList<Point2D> points = new ArrayList<>(root == null ? 0 : root.size);
        getPoints(root, points);
        return points;
    }

    private static int nodeSize(Node node) {
        return (node.left == null ? 0 : node.left.size) + (node.right == null ? 0 : node.right.size) + 1;
    }

    private static void getPoints(Node node, List<Point2D> accumList) {
        if (node == null) return;
        accumList.add(node.point);
        if (node.left != null) getPoints(node.left, accumList);
        if (node.right != null) getPoints(node.right, accumList);
    }

    private static boolean isRed(Node node) {
        return node != null && node.color == Node.RED;
    }


    private static void levelOrder(Node node, HashMap<Integer, ArrayList<Node>> map, int level) {
        if (node == null) return;
        ArrayList<Node> l = map.get(level);
        if (l == null) {
            l = new ArrayList<>();
            map.put(level, l);
        }
        if (isRed(node) || true) l.add(node);
        levelOrder(node.left, map, level + 1);
        levelOrder(node.right, map, level + 1);
    }


    /**
     * unit testing of the methods
     */
    public static void main(String[] args) {
        int[] arr = {29, 25, 85, 10, 27, 55, 97, 54, 76, 89};
        PointSET s = new PointSET();
        for (int i : arr) {
            s.insert(new Point2D(i, 0));
        }
        HashMap<Integer, ArrayList<Node>> map = new HashMap<>();
        levelOrder(s.root, map, 0);
        for (Integer integer : map.keySet()) {
            for (Node node : map.get(integer)) System.out.print(node.point.x() + " ");
            System.out.println();
        }
        System.out.println();
        s.insert(new Point2D(33, 0));
        s.insert(new Point2D(84, 0));
        s.insert(new Point2D(67, 0));
        map.clear();
        levelOrder(s.root, map, 0);
        for (Integer integer : map.keySet()) {
            for (Node node : map.get(integer)) System.out.print(((int) node.point.x()) + " ");
//            System.out.println();
        }

    }
}

