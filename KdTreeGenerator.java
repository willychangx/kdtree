/******************************************************************************
 *  Compilation:  javac KdTreeGenerator.java
 *  Execution:    java KdTreeGenerator n
 *  Dependencies:
 *
 *  Creates n random points in the unit square and print to standard output.
 *
 *  % java KdTreeGenerator 5
 *  0.195080 0.938777
 *  0.351415 0.017802
 *  0.556719 0.841373
 *  0.183384 0.636701
 *  0.649952 0.237188
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.HashMap;

public class KdTreeGenerator {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        if (k == 0) {
            HashMap<Double, Boolean> xMap = new HashMap<Double, Boolean>();
            HashMap<Double, Boolean> yMap = new HashMap<Double, Boolean>();
            for (int i = 0; i < n; i++) {
                double x = StdRandom.uniform(0.0, m) / m;
                double y = StdRandom.uniform(0.0, m) / m;
                while (xMap.containsKey(x) || yMap.containsKey(y)) {
                    if (xMap.containsKey(x))
                        x = StdRandom.uniform(0.0, m) / m;
                    if (yMap.containsKey(y))
                        y = StdRandom.uniform(0.0, m) / m;
                }
                xMap.put(x, true);
                yMap.put(y, true);
                StdOut.printf("%8.6f %8.6f\n", x, y);
            }
        }
        else if (k == 1) {
            HashMap<Point2D, Boolean> points = new HashMap<Point2D, Boolean>();
            for (int i = 0; i < n; i++) {
                double x = StdRandom.uniform(0.0, m) / m;
                double y = StdRandom.uniform(0.0, m) / m;
                while (points.containsKey(new Point2D(x, y))) {
                    x = StdRandom.uniform(0.0, m) / m;
                    y = StdRandom.uniform(0.0, m) / m;
                }
                points.put(new Point2D(x, y), true);
                StdOut.printf("%8.6f %8.6f\n", x, y);
            }
        }
        else {
            for (int i = 0; i < n; i++) {
                double x = StdRandom.uniform(0.0, m) / m;
                double y = StdRandom.uniform(0.0, m) / m;
                StdOut.printf("%8.6f %8.6f\n", x, y);
            }
        }
    }
}
