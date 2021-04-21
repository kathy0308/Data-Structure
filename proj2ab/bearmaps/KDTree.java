package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private static class Node {
        private boolean orientation;
        Point p;
        Node downChild;
        Node upChild;

        Node(Point p, boolean orientation) {
            this.orientation = orientation;
            this.p = p;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    private Node add(Point p, Node node, boolean orientation) {
        if (node == null) {
            return new Node(p, orientation);
        } else if (p.equals(node.p)) {
            return node;
        }

        int cmp = comparePoints(p, node.p, orientation);

        if (cmp < 0) {
            node.downChild = add(p, node.downChild, !orientation);
        } else {
            node.upChild = add(p, node.upChild, !orientation);
        }
        return node;
    }

    private int comparePoints(Point x1, Point y1, boolean orientation) {
         if (orientation = HORIZONTAL) {
              return Double.compare(x1.getX(), y1.getY());
         } else {
             return Double.compare(y1.getX(), x1.getY());
         }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root.p);

    }

    private Point nearest(Node node, Point goal, Point best) {
        if (node == null) {
            return best;
        } else if ((Point.distance(goal, node.p)) < (Point.distance(goal, best))) {
            best = node.p;
        }

        int cmp = comparePoints(goal, node.p, node.orientation);

        Node good;
        Node bad;

        if (cmp < 0) {
            good = node.downChild;
            bad = node.upChild;
        } else {
            good = node.upChild;
            bad = node.downChild;
        }
        best = nearest(good, goal, best);
        if (nearestHelper(node, goal, best)) {
            best = nearest(bad, goal, best);
        }
        return best;
    }

    private boolean nearestHelper(Node node, Point goal, Point best) {
        double badDistance;
        double bestDistance = Point.distance(goal, best);

        if (node.orientation == HORIZONTAL) {
            badDistance = Point.distance(new Point(goal.getX(), node.p.getY()), goal);
        } else {
            badDistance = Point.distance(new Point(node.p.getY(), goal.getX()), goal);
        }

        return badDistance < bestDistance;

    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
