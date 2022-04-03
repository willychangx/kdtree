/* *****************************************************************************
 *  Name: Willy Chang
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {
    private Node root; // starting node for the kdtree
    private int size; // keeps track of the number of nodes in the tree

    /**
     * Define the orientations of the bars that can be given to a node.
     */
    enum Orientation {
        VERTICAL, HORIZONTAL
    }

    /**
     * Class definition for Node.
     */
    public class Node {
        private Point2D point; // point node contains
        private Node left; // the left node of the current node
        private Node right; // the right node of the current node
        private Orientation bar; // split of the node

        /**
         * Initialize a Node.
         *
         * @param p the point the node will contain
         * @param b the orientation of the bar
         */
        public Node(Point2D p, Orientation b) {
            this.point = p;
            this.bar = b;
            this.left = null;
            this.right = null;
        }

        /**
         * Setter for {@code bar}.
         *
         * @param bar an {@code Orientation}
         */
        public void setBar(Orientation bar) {
            this.bar = bar;
        }

        /**
         * Returns the {@code bar}.
         *
         * @return {@code bar}
         */
        public Orientation bar() {
            return this.bar;
        }

        /**
         * Setter for the left {@code Node}.
         *
         * @param left {@code Node}
         */
        public void setLeft(Node left) {
            this.left = left;
        }

        /**
         * Returns {@code left}.
         *
         * @return {@code left}
         */
        public Node left() {
            return this.left;
        }

        /**
         * Setter for the right {@code Node}
         *
         * @param right {@code Node}
         */
        public void setRight(Node right) {
            this.right = right;
        }

        /**
         * Return {@code right}.
         *
         * @return {@code right}
         */
        public Node right() {
            return this.right;
        }

        /**
         * Return {@code point}.
         *
         * @return {@code point}
         */
        public Point2D point() {
            return this.point;
        }
    }

    /**
     * Initialize the kdtree with no nodes.
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * Check to see if the kdtree is empty.
     *
     * @return {@code true} if there are no nodes in the tree; otherwise, {@code false}
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns the {@code size} of the tree.
     *
     * @return {@code size}
     */
    public int size() {
        return size;
    }

    /**
     * Inserts the Point2D {@code p} into the kdtree as a Node if the kdtree does not already
     * contain that point.
     *
     * @param p {@code Point2D}
     */
    public void insert(Point2D p) {
        // Throw an exception if the point is null
        if (p == null)
            throw new IllegalArgumentException("Null argument to insert()");

        // Make the point a root node if there are no other points in the tree
        if (root == null) {
            size += 1;
            root = new Node(p, Orientation.VERTICAL);
            return;
        }

        // If the point is not already in the tree
        if (!contains(p)) {
            size += 1;
            Node traverse = root;
            // Find where to insert the point
            while (traverse != null) {
                // If the current traversed node is horizontal
                if (traverse.bar() == Orientation.HORIZONTAL) {
                    // If the y coordinate of the new point is greater
                    if (traverse.point().y() < p.y()) {
                        // Insert the point on the left side of this node unless theres more nodes to traverse
                        if (traverse.left() == null) {
                            traverse.setLeft(new Node(p, Orientation.VERTICAL));
                            return;
                        }
                        traverse = traverse.left();
                    }
                    // If the y coordinate of the new point is smaller or equal to
                    else {
                        // Insert the point on the right side of this node unless theres more nodes to traverse
                        if (traverse.right() == null) {
                            traverse.setRight(new Node(p, Orientation.VERTICAL));
                            return;
                        }
                        traverse = traverse.right();
                    }
                }
                // If the current traversed node is vertical
                if (traverse.bar() == Orientation.VERTICAL) {
                    // If the x coordinate of the new point is greater
                    if (traverse.point().x() < p.x()) {
                        // Insert the point on the left side of this node unless theres more nodes to traverse
                        if (traverse.left() == null) {
                            traverse.setLeft(new Node(p, Orientation.HORIZONTAL));
                            return;
                        }
                        traverse = traverse.left();
                    }
                    // If the x coordinate of the new point is smaller or equal to
                    else {
                        // Insert the point on the right side of this node unless theres more nodes to traverse
                        if (traverse.right() == null) {
                            traverse.setRight(new Node(p, Orientation.HORIZONTAL));
                            return;
                        }
                        traverse = traverse.right();
                    }
                }
            }
        }
    }

    /**
     * Check if the point given exists in the kdtree.
     *
     * @param p {@code Point2D} point to check exists
     * @return {@code true} if the point is in the tree; {@code false} otherwise
     */
    public boolean contains(Point2D p) {
        // Throw exception if point given is null
        if (p == null)
            throw new IllegalArgumentException("Null argument to contains()");

        Node traverse = root;
        // Traverse the tree searching for the node
        while (traverse != null && !traverse.point().equals(p)) {
            // If the current node has a horizontal bar
            if (traverse.bar() == Orientation.HORIZONTAL) {
                // Check if the y coordinate of p is greater than the current point
                if (traverse.point().y() < p.y())
                    // Search left if it is
                    traverse = traverse.left();
                else
                    // Search right otherwise
                    traverse = traverse.right();
            }
            else {
                // Check if the x coordinate of p is greater than the current point
                if (traverse.point().x() < p.x())
                    // Search left if it is
                    traverse = traverse.left();
                else
                    // Search right otherwise
                    traverse = traverse.right();
            }
        }
        // Point is either found or not
        return (traverse != null);
    }

    /**
     * Draw the kdtree.
     */
    public void draw() {
        preorder(this.root, 0, 0, 1, 1);
    }

    /**
     * Helper function to traverse the kdtree in a preorder fashion.
     *
     * @param node {@code Node} of the current Node visited
     * @param xmin the min x coordinate that the last node bounds
     * @param ymin the max x coordinate that the last node bounds
     * @param xmax the min y coordinate that the last node bounds
     * @param ymax the max y coordinate that the last node bounds
     */
    public void preorder(Node node, double xmin, double ymin, double xmax, double ymax) {
        // Exit if there are no nodes to draw
        if (node == null)
            return;

        // Draw the current point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point().draw();

        // To draw a horizontal line
        if (node.bar() == Orientation.HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(xmin, node.point().y(), xmax, node.point().y());
            // Draw the left Node
            preorder(node.left(), xmin, node.point().y(), xmax, ymax);
            // Draw the right Node
            preorder(node.right(), xmin, ymin, xmax, node.point().y());
        }
        // To draw a vertical line
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.point().x(), ymin, node.point().x(), ymax);
            // Draw the right Node
            preorder(node.right(), xmin, ymin, node.point().x(), ymax);
            // Draw the left Node
            preorder(node.left(), node.point().x(), ymin, xmax, ymax);
        }
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

        Stack<Point2D> inside = new Stack<Point2D>();
        // Add all of the points inside of rect
        searchForPoints(inside, this.root, new RectHV(0, 0, 1, 1), rect);
        return inside;
    }

    /**
     * Helper function to traverse the kdtree for points inside of the {@code query}.
     *
     * @param stack {@code Stack<Point2D>} that contains all points inside the {@code query}
     * @param node  {@code Node}
     * @param rect  {@code RectHV} bounding box that contains {@code node} generated from previous
     *              node
     * @param query {@code RectHV} range indicated by the user
     */
    public void searchForPoints(Stack<Point2D> stack, Node node, RectHV rect, RectHV query) {
        // No more nodes to check
        if (node == null)
            return;

        // Check if the current point is inside the rectangle
        if (query.contains(node.point()))
            // Add it if it is
            stack.push(node.point());

        // If the bar is horizontal
        if (node.bar() == Orientation.HORIZONTAL) {
            RectHV box = new RectHV(rect.xmin(), node.point().y(), rect.xmax(), rect.ymax());
            // Check to see if the left node's rectangle intersects with the query rectangle
            if (box.intersects(query))
                // Search left node if it does
                searchForPoints(stack, node.left(), box, query);
            box = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point().y());
            // Check to see if the right node's rectangle intersects with the query rectangle
            if (box.intersects(query))
                // Search right node if it does
                searchForPoints(stack, node.right(), box, query);
        }
        else {
            RectHV box = new RectHV(rect.xmin(), rect.ymin(), node.point().x(), rect.ymax());
            // Check to see if the right node's rectangle intersects with the query rectangle
            if (box.intersects(query))
                // Search right node if it does
                searchForPoints(stack, node.right(), box, query);
            box = new RectHV(node.point().x(), rect.ymin(), rect.xmax(), rect.ymax());
            // Check to see if the left node's rectangle intersects with the query rectangle
            if (box.intersects(query))
                // Search left node if it does
                searchForPoints(stack, node.left(), box, query);
        }
    }

    /**
     * Locates the nearest point in the kdtree to the queried point.
     *
     * @param p {@code Point2D} queried point
     * @return {@code Point2D} point in kdtree closest to {@code p}
     */
    public Point2D nearest(Point2D p) {
        // Throw exception if given null
        if (p == null)
            throw new IllegalArgumentException("Null argument to nearest()");

        // No points closest if there are no nodes in the tree
        if (this.size == 0)
            return null;

        // Return closest point
        return searchNearest(root, p, root.point(), Double.POSITIVE_INFINITY,
                             new RectHV(0, 0, 1, 1));
    }

    /**
     * Helper function to prune kdtree for closest node.
     *
     * @param node   {@code Node}
     * @param target {@code Point2D} target specified by the user
     * @param best   {@code Point2D} closest point to the {@code target} thus far
     * @param bestD  closest distance to {@code target}
     * @param rect   {@code RectHV} bounding box from previous nodes and contains current {@code
     *               node}
     * @return closest node to the {@code target}
     */
    public Point2D searchNearest(Node node, Point2D target, Point2D best, double bestD,
                                 RectHV rect) {
        // No more nodes to search
        if (node == null)
            return best;

        // Update best node if the distance is closer
        double currD = node.point().distanceSquaredTo(target);
        if (currD < bestD) {
            best = node.point();
            bestD = currD;
        }

        // If the bar is horizontal
        if (node.bar() == Orientation.HORIZONTAL) {
            RectHV box = new RectHV(rect.xmin(), node.point().y(), rect.xmax(), rect.ymax());
            // Check if the left rectangle is closer than the best node
            if (bestD >= box.distanceSquaredTo(target))
                // Search that rectangle if it is
                best = searchNearest(node.left(), target, best, bestD, box);
            box = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point().y());
            // Check if the right rectangle is closer than the best node
            if (best.distanceSquaredTo(target) >= box.distanceSquaredTo(target))
                // Search that rectangle if it is
                best = searchNearest(node.right(), target, best, best.distanceSquaredTo(target),
                                     box);
        }
        // If the bar is vertical
        else {
            RectHV box = new RectHV(rect.xmin(), rect.ymin(), node.point().x(), rect.ymax());
            // Check if the right rectangle is closer than the best node
            if (bestD >= box.distanceSquaredTo(target))
                // Search that rectangle if it is
                best = searchNearest(node.right(), target, best, bestD, box);
            box = new RectHV(node.point().x(), rect.ymin(), rect.xmax(), rect.ymax());
            // Check if the left rectangle is closer than the best node
            if (best.distanceSquaredTo(target) >= box.distanceSquaredTo(target))
                // Search that rectangle if it is
                best = searchNearest(node.left(), target, best, best.distanceSquaredTo(target),
                                     box);
        }

        return best;
    }

    // Test Cases
    public static void main(String[] args) {
        // Test 2.1a: size() & isEmpty()
        String[] input = {
                "input0.txt", "input1.txt", "input5.txt", "input10.txt"
        };
        System.out.println("Test 2.1a: size() & isEmpty()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            int count = 0;
            while (!in.isEmpty()) {
                count += 1;
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
                double check = StdRandom.uniform(0.0, 1.0);
                if (check > 0.9) {
                    System.out.println("Checking after inserting " + count + " points");
                    System.out.println("size, isEmpty: " + tree.size() + ", " + tree.isEmpty());
                }
            }
        }
        System.out.println("---");

        // Test 2.1b: size() & isEmpty()
        input = new String[] {
                "input1-1x1-nd.txt", "input5-8x8-nd.txt", "input10-16x16-nd.txt",
                "input50-128x128-nd.txt", "input500-1024x1024-nd.txt"
        };
        System.out.println("Test 2.1b: size() & isEmpty()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            int count = 0;
            while (!in.isEmpty()) {
                count += 1;
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
                double check = StdRandom.uniform(0.0, 1.0);
                if (check > 0.9) {
                    System.out.println("Checking after inserting " + count + " points");
                    System.out.println("size, isEmpty: " + tree.size() + ", " + tree.isEmpty());
                }
            }
        }
        System.out.println("---");

        // Test 2.1c: size() & isEmpty()
        input = new String[] {
                "input1-1x1-d.txt", "input10-8x8-d.txt", "input20-16x16-d.txt"
        };
        System.out.println("Test 2.1c: size() & isEmpty()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            int count = 0;
            while (!in.isEmpty()) {
                count += 1;
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
                double check = StdRandom.uniform(0.0, 1.0);
                if (check > 0.9) {
                    System.out.println("Checking after inserting " + count + " points");
                    System.out.println("size, isEmpty: " + tree.size() + ", " + tree.isEmpty());
                }
            }
        }
        System.out.println("---");

        // Test 2.1d: size() & isEmpty()
        input = new String[] {
                "input5-1x1.txt", "input10-4x4.txt", "input50-8x8.txt"
        };
        System.out.println("Test 2.1d: size() & isEmpty()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            int count = 0;
            while (!in.isEmpty()) {
                count += 1;
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
                double check = StdRandom.uniform(0.0, 1.0);
                if (check > 0.9) {
                    System.out.println("Checking after inserting " + count + " points");
                    System.out.println("size, isEmpty: " + tree.size() + ", " + tree.isEmpty());
                }
            }
        }
        System.out.println("---");

        // Test 2.2a: contains()
        input = new String[] {
                "input0.txt", "input1.txt", "input5.txt", "input10.txt"
        };
        System.out.println("Test 2.2a: contains()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                double randX = StdRandom.uniform(0.0, 1.0);
                double randY = StdRandom.uniform(0.0, 1.0);
                Point2D p = new Point2D(randX, randY);
                System.out.println("Checking " + p.toString() + "");
                System.out.println("contains: " + tree.contains(p));
            }
        }
        System.out.println("---");

        // Test 2.2b: contains()
        input = new String[] {
                "input1-1x1-nd.txt", "input5-8x8-nd.txt", "input10-16x16-nd.txt",
                "input20-32x32-nd.txt", "input500-1024x1024-nd.txt"
        };
        System.out.println("Test 2.2b: contains()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                double randX = StdRandom.uniform(0.0, 1.0);
                double randY = StdRandom.uniform(0.0, 1.0);
                Point2D p = new Point2D(randX, randY);
                System.out.println("Checking " + p.toString() + "");
                System.out.println("contains: " + tree.contains(p));
            }
        }
        System.out.println("---");

        // Test 2.2c: contains()
        input = new String[] {
                "input1-1x1-d.txt", "input10-4x4-d.txt",
                "input20-8x8-d.txt", "input10K-128x128-d.txt"
        };
        System.out.println("Test 2.2c: contains()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                double randX = StdRandom.uniform(0.0, 1.0);
                double randY = StdRandom.uniform(0.0, 1.0);
                Point2D p = new Point2D(randX, randY);
                System.out.println("Checking " + p.toString() + "");
                System.out.println("contains: " + tree.contains(p));
            }
        }
        System.out.println("---");

        // Test 2.2d: contains()
        input = new String[] {
                "input10K-1x1.txt", "input10K-16x16.txt", "input10K-128x128.txt"
        };
        System.out.println("Test 2.2d: contains()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                double randX = StdRandom.uniform(0.0, 1.0);
                double randY = StdRandom.uniform(0.0, 1.0);
                Point2D p = new Point2D(randX, randY);
                System.out.println("Checking " + p.toString() + "");
                System.out.println("contains: " + tree.contains(p));
            }
        }
        System.out.println("---");

        // Test 2.3a: range()
        input = new String[] {
                "input0.txt", "input1.txt", "input5.txt", "input10.txt"
        };
        System.out.println("Test 2.3a: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 3; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.3b: range()
        input = new String[] {
                "input1-2x2-nd.txt", "input5-8x8-nd.txt", "input10-16x16-nd.txt",
                "input20-32x32-nd.txt", "input500-1024x1024-nd.txt"
        };
        System.out.println("Test 2.3b: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 3; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.3c: range()
        input = new String[] {
                "input2-2x2-d.txt", "input10-4x4-d.txt", "input20-8x8-d.txt",
                "input100-16x16-d.txt",
                "input1000-64x64-d.txt"
        };
        System.out.println("Test 2.3c: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 3; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.3d: range()
        input = new String[] {
                "input5000-16x16.txt", "input5000-128x128.txt"
        };
        System.out.println("Test 2.3d: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 3; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.3e: range()
        input = new String[] {
                "input5-2x2.txt", "input10-4x4.txt", "input20-8x8.txt", "input5000-65536x65536.txt"
        };
        System.out.println("Test 2.3e: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            in = new In(string);
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                RectHV p = new RectHV(x - 0.001, y - 0.001, x + 0.001, y + 0.001);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.4a: range()
        input = new String[] {
                "input5.txt", "input10.txt"
        };
        System.out.println("Test 2.4a: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 3; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.4b: range()
        input = new String[] {
                "input3-4x4-nd.txt", "input6-8x8-nd.txt", "input10-16x16-nd.txt"
        };
        System.out.println("Test 2.4b: range()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 1000; i++) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);

                RectHV p = new RectHV(xmin, ymin, xmax, ymax);
                System.out.println("Checking rectangle " + p.toString());
                Iterable<Point2D> res = tree.range(p);
                for (Point2D r : res)
                    System.out.println(r.toString());
            }
        }
        System.out.println("---");

        // Test 2.5a: nearest()
        input = new String[] {
                "input0.txt", "input1.txt", "input5.txt", "input10.txt"
        };
        System.out.println("Test 2.5a: nearest()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                Point2D p = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
                System.out.println("Checking " + p.toString() + "");
                System.out.println("nearest: " + tree.nearest(p));
            }
        }
        System.out.println("---");

        // Test 2.5b: nearest()
        input = new String[] {
                "input5-8x8-nd.txt", "input10-16x16-nd.txt", "input20-32x32-nd.txt",
                "input30-64x64-nd.txt"
        };
        System.out.println("Test 2.5b: nearest()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                Point2D p = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
                System.out.println("Checking " + p.toString() + "");
                System.out.println("nearest: " + tree.nearest(p));
            }
        }
        System.out.println("---");

        // Test 2.5c: nearest()
        input = new String[] {
                "input10-4x4-d.txt", "input15-8x8-d.txt", "input20-16x16-d.txt"
        };
        System.out.println("Test 2.5c: nearest()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                Point2D p = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
                System.out.println("Checking " + p.toString() + "");
                System.out.println("nearest: " + tree.nearest(p));
            }
        }
        System.out.println("---");

        // Test 2.5d: nearest()
        input = new String[] {
                "input10K-16x16.txt"
        };
        System.out.println("Test 2.5d: nearest()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                Point2D p = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
                System.out.println("Checking " + p.toString() + "");
                System.out.println("nearest: " + tree.nearest(p));
            }
        }
        System.out.println("---");

        // Test 2.6a: nearest()
        input = new String[] {
                "input5.txt", "input10.txt"
        };
        System.out.println("Test 2.6a: nearest()");
        for (String string : input) {
            System.out.println(string);
            In in = new In(string);
            KdTree tree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                tree.insert(p);
            }
            for (int i = 0; i < 5; i++) {
                Point2D p = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
                System.out.println("Checking " + p.toString() + "");
                System.out.println("nearest: " + tree.nearest(p));
            }
        }
        System.out.println("---");

        // Test 2.7: Before Insertion
        System.out.println("Test 2.7: Before Insertion");
        Point2D p = new Point2D(0, 0);
        RectHV r = new RectHV(0, 0, 0, 0);
        KdTree tree = new KdTree();
        System.out.println("size(): " + tree.size());
        System.out.println("isEmpty(): " + tree.isEmpty());
        System.out.println("nearest(): " + tree.nearest(p));
        System.out.println("range(): " + tree.range(r));
        System.out.println("---");

        // Test 2.8: Null Argument
        System.out.println("Test 2.8: Null Argument");
        try {
            tree.insert(null);
            System.out.println("insert() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("insert() PASSED");
        }
        try {
            tree.contains(null);
            System.out.println("contains() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("contains() PASSED");
        }
        try {
            tree.range(null);
            System.out.println("range() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("range() PASSED");
        }
        try {
            tree.nearest(null);
            System.out.println("nearest() accepted null argument");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("nearest() PASSED");
        }
        System.out.println("---");

        // Test 2.9a: Intermixed Calls
        System.out.println("Test 2.9a: Intermixed Calls");
        In in = new In("input20K-1x1-nd.txt");
        tree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            p = new Point2D(x, y);
            double check = StdRandom.uniform(0.0, 1.0);
            if (check < 0.3) {
                System.out.println("insert: " + p.toString());
                tree.insert(p);
            }
            else if (check >= 0.3 && check < 0.35)
                System.out.println("isEmpty: " + tree.isEmpty());
            else if (check >= 0.35 && check < 0.4)
                System.out.println("size: " + tree.size());
            else if (check >= 0.4 && check < 0.6) {
                p = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));
                System.out.println("contains: " + tree.contains(p));
            }
            else if (check >= 0.6 && check < 0.8) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);
                r = new RectHV(xmin, ymin, xmax, ymax);
                Iterable<Point2D> it = tree.range(r);
                System.out.println("range: " + r.toString());
                if (it != null) {
                    for (Point2D t : it)
                        System.out.println(t.toString());
                }
            }
            else if (check >= 0.8 && check <= 1.0) {
                Point2D np = tree.nearest(p);
                System.out.println("checking " + p.toString());
                if (np != null)
                    System.out.println("nearest: " + tree.nearest(np).toString());
                else
                    System.out.println("no nearest");
            }
        }

        // Test 2.9b: Intermixed Calls
        System.out.println("Test 2.9b: Intermixed Calls");
        in = new In("input20K-1x1-d.txt");
        tree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            p = new Point2D(x, y);
            double check = StdRandom.uniform(0.0, 1.0);
            if (check < 0.3) {
                System.out.println("insert: " + p.toString());
                tree.insert(p);
            }
            else if (check >= 0.3 && check < 0.35)
                System.out.println("isEmpty: " + tree.isEmpty());
            else if (check >= 0.35 && check < 0.4)
                System.out.println("size: " + tree.size());
            else if (check >= 0.4 && check < 0.6) {
                p = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));
                System.out.println("checking " + p.toString());
                System.out.println("contains: " + tree.contains(p));
            }
            else if (check >= 0.6 && check < 0.8) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);
                r = new RectHV(xmin, ymin, xmax, ymax);
                Iterable<Point2D> it = tree.range(r);
                System.out.println("range: " + r.toString());
                if (it != null) {
                    for (Point2D t : it)
                        System.out.println(t.toString());
                }
            }
            else if (check >= 0.8 && check <= 1.0) {
                Point2D np = tree.nearest(p);
                System.out.println("checking " + p.toString());
                if (np != null)
                    System.out.println("nearest: " + tree.nearest(np).toString());
                else
                    System.out.println("no nearest");
            }
        }

        // Test 2.9c: Intermixed Calls
        System.out.println("Test 2.9c: Intermixed Calls");
        in = new In("input20K-1x1.txt");
        tree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            p = new Point2D(x, y);
            double check = StdRandom.uniform(0.0, 1.0);
            if (check < 0.3) {
                System.out.println("insert: " + p.toString());
                tree.insert(p);
            }
            else if (check >= 0.3 && check < 0.35)
                System.out.println("isEmpty: " + tree.isEmpty());
            else if (check >= 0.35 && check < 0.4)
                System.out.println("size: " + tree.size());
            else if (check >= 0.4 && check < 0.6) {
                p = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));
                System.out.println("checking " + p.toString());
                System.out.println("contains: " + tree.contains(p));
            }
            else if (check >= 0.6 && check < 0.8) {
                double xmin = StdRandom.uniform(0.0, 1.0);
                double xmax = StdRandom.uniform(xmin, 1.0);
                double ymin = StdRandom.uniform(0.0, 1.0);
                double ymax = StdRandom.uniform(ymin, 1.0);
                r = new RectHV(xmin, ymin, xmax, ymax);
                Iterable<Point2D> it = tree.range(r);
                System.out.println("range: " + r.toString());
                if (it != null) {
                    for (Point2D t : it)
                        System.out.println(t.toString());
                }
            }
            else if (check >= 0.8 && check <= 1.0) {
                Point2D np = tree.nearest(p);
                System.out.println("checking " + p.toString());
                if (np != null)
                    System.out.println("nearest: " + tree.nearest(np).toString());
                else
                    System.out.println("no nearest");
            }
        }
    }
}
