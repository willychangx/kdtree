/* *****************************************************************************
 *  Name: Willy Chang
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {
    private SET<Point2D> pSet; // red-black tree

    /**
     * Initialize red-black tree to contain the points
     */
    public PointSET() {
        pSet = new SET<Point2D>();
    }

    /**
     * Return if the tree is empty
     *
     * @return {@code true} if the tree is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return pSet.isEmpty();
    }

    /**
     * Return the size of the tree
     *
     * @return size of the tree
     */
    public int size() {
        return pSet.size();
    }

    /**
     * Insert the point {@code p} if the tree doesn't have the point
     *
     * @param p {@code Point2D} to insert
     */
    public void insert(Point2D p) {
        // Throw exception if argument is null
        if (p == null)
            throw new IllegalArgumentException("Null argument to insert()");

        // If the tree doesn't contain the point
        if (!contains(p))
            // Add it
            pSet.add(p);
    }

    /**
     * Check if the tree contains the point
     *
     * @param p {@code Point2D} to check
     * @return {@code true} if the point is in the tree; {@code false} otherwise
     */
    public boolean contains(Point2D p) {
        // Throw an exception if the point is null
        if (p == null)
            throw new IllegalArgumentException("Null argument to contains()");

        return pSet.contains(p);
    }

    /**
     * Draw the points in the tree
     */
    public void draw() {
        StdDraw.setPenRadius(0.01);
        for (Point2D point : pSet)
            point.draw();
    }

    /**
     * Finds all the points inside of given rectangle.
     *
     * @param rect {@code RectHV} indicating the region to search
     * @return the points inside of {@code rect}
     */
    public Iterable<Point2D> range(RectHV rect) {
        // Throw an exception if the rectangle is null
        if (rect == null)
            throw new IllegalArgumentException("Null argument to range()");

        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();
        Stack<Point2D> inside = new Stack<Point2D>();

        // Check each point to see if its coordinates are inside the range
        for (Point2D point : pSet) {
            double x = point.x();
            double y = point.y();
            if (xmin <= x && x <= xmax && ymin <= y && y <= ymax)
                inside.push(point);
        }
        return inside;
    }

    /**
     * Locates the nearest point in the tree to the queried point.
     *
     * @param p {@code Point2D} queried point
     * @return {@code Point2D} point in tree closest to {@code p}
     */
    public Point2D nearest(Point2D p) {
        // Throw exception if given null
        if (p == null)
            throw new IllegalArgumentException("Null argument to nearest()");

        // No points closest if there are no nodes in the tree
        if (pSet.isEmpty())
            return null;

        // Search each point and update the closest if the distance to p is shorter
        double distance = Double.POSITIVE_INFINITY;
        Point2D nearP = null;
        double calcDistance = 0;
        for (Point2D point : pSet) {
            calcDistance = p.distanceSquaredTo(point);
            if (calcDistance < distance) {
                distance = calcDistance;
                nearP = point;
            }
        }
        return nearP;
    }

    // Test Cases
    public static void main(String[] args) {
        // Test 1.1: size() & isEmpty()
        String[] input = {
                "input5-1x1.txt", "input50-8x8.txt", "input100-16x16.txt", "input1000-128x128.txt"
        };
        System.out.println("Test 1.1: size() & isEmpty()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            PointSET brute = new PointSET();
            int count = 0;
            while (!in.isEmpty()) {
                count += 1;
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                brute.insert(p);
                double check = StdRandom.uniform(0.0, 1.0);
                if (check > 0.9) {
                    System.out.println("Checking after inserting " + count + " points");
                    System.out.println("size, isEmpty: " + brute.size() + ", " + brute.isEmpty());
                }
            }
        }
        System.out.println("---");

        // Test 1.2: contains()
        input = new String[] {
                "input5-1x1.txt", "input50-4x4.txt", "input100-8x8.txt", "input1000-128x128.txt"
        };
        System.out.println("Test 1.2: contains()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            PointSET brute = new PointSET();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                brute.insert(p);
            }
            in = new In(string);
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                double check = StdRandom.uniform(0.0, 1.0);
                if (check > 0.9) {
                    Point2D p = new Point2D(x, y);
                    System.out.println("Checking " + p.toString() + "");
                    System.out.println("contains: " + brute.contains(p));
                }
            }
        }
        System.out.println("---");

        // Test 1.3: nearest()
        input = new String[] {
                "input10-4x4.txt", "input20-16x16.txt", "input100-32x32.txt"
        };
        System.out.println("Test 1.3: nearest()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            PointSET brute = new PointSET();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                brute.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                Point2D p = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
                System.out.println("Checking " + p.toString() + "");
                System.out.println("nearest: " + brute.nearest(p));
            }
        }
        System.out.println("---");

        // Test 1.4: range()
        input = new String[] {
                "input2-2x2.txt", "input10-4x4.txt", "input20-8x8.txt", "input100-16x16.txt"
        };
        System.out.println("Test 1.4: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            PointSET brute = new PointSET();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                brute.insert(p);
            }
            for (int i = 0; i < 3; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = brute.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 1.5: Before Insertion
        System.out.println("Test 1.5: Before Insertion");
        Point2D p = new Point2D(0, 0);
        RectHV r = new RectHV(0, 0, 0, 0);
        PointSET brute = new PointSET();
        System.out.println("size(): " + brute.size());
        System.out.println("isEmpty(): " + brute.isEmpty());
        System.out.println("nearest(): " + brute.nearest(p));
        System.out.println("range(): " + brute.range(r));
        System.out.println("---");

        // Test 1.6: Null Argument
        System.out.println("Test 1.6: Null Argument");
        try {
            brute.insert(null);
            System.out.println("insert() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("insert() PASSED");
        }
        try {
            brute.contains(null);
            System.out.println("contains() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("contains() PASSED");
        }
        try {
            brute.range(null);
            System.out.println("range() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("range() PASSED");
        }
        try {
            brute.nearest(null);
            System.out.println("nearest() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("nearest() PASSED");
        }
        System.out.println("---");

        // Test 1.7: Intermixed Calls
        System.out.println("Test 1.7: Intermixed Calls");
        In in = new In("input10K-1x1.txt");
        brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            p = new Point2D(x, y);
            double check = StdRandom.uniform(0.0, 1.0);
            if (check < 0.3) {
                System.out.println("insert: " + p.toString());
                brute.insert(p);
            }
            else if (check >= 0.3 && check < 0.4)
                System.out.println("isEmpty: " + brute.isEmpty());
            else if (check >= 0.4 && check < 0.5)
                System.out.println("size: " + brute.size());
            else if (check >= 0.5 && check < 0.6) {
                p = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));
                System.out.println("checking " + p.toString());
                System.out.println("contains: " + brute.contains(p));
            }
            else if (check >= 0.6 && check < 0.8) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);
                r = new RectHV(xmin, ymin, xmax, ymax);
                Iterable<Point2D> it = brute.range(r);
                System.out.println("range: " + r.toString());
                for (Point2D t : it)
                    System.out.println(t.toString());
            }
            else if (check >= 0.8 && check <= 1.0) {
                Point2D np = brute.nearest(p);
                System.out.println("checking " + p.toString());
                if (np != null)
                    System.out.println("nearest: " + brute.nearest(p).toString());
                else
                    System.out.println("no nearest");
            }
        }
    }
}
