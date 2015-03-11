import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER;

    private final int x;

    private final int y;

    // construct the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new SlopeComparator(this.x, this.y);
    }

    // draw this point
    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    // draw the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // is this point lexicographically smaller than that point?
    public int compareTo(Point that) {
        if (that == null)
            throw new NullPointerException("that cant be null");

        int result;

        result = compareTo(this.y, that.y);

        if (result == 0)
            result = compareTo(this.x, that.x);

        return result;
    }

    private int compareTo(int a, int b) {
        if (a < b)
            return -1;
        else if (a > b)
            return 1;
        else
            return 0;
    }


    // the slope between this point and that point
    public double slopeTo(Point that) {
        return slope(this.x, this.y, that.x, that.y);
    }

    public static void main(String[] args) {
        /*System.out.println(new Point(0, 500).slopeTo(new Point(0, 500)));
        System.out.println(new Point(128, 392).slopeTo(new Point(128, 392)));
        System.out.println(new Point(0, 7).slopeTo(new Point(0, 7)));


        Point p = new Point(433, 27);
        Point q = new Point(433, 27);
        Point r = new Point(433, 27);

        System.out.println(p.SLOPE_ORDER.compare(q, r));

        assert p.SLOPE_ORDER.compare(q, r) == 0;
        assert p.slopeTo(q) == 0.0;
        assert p.slopeTo(r) == 0.0;

        p = new Point(8192, 25088);
        q = new Point(4096, 25088);
        r = new Point(7168, 25088);

        System.out.println(p.slopeTo(q));
        System.out.println(p.slopeTo(r));
        System.out.println(p.SLOPE_ORDER.compare(q, r));*/

    }

    private static double slope(int x0, int y0, int x1, int y1) {
        if (x0 == x1 && y0 == y1)
            return Double.NEGATIVE_INFINITY;
        else if (x0 == x1)
            return Double.POSITIVE_INFINITY;
        else if (y0 == y1)
            return 0;

        return new Double(y1 - y0) / (x1 - x0);
    }

    private static class SlopeComparator implements Comparator<Point> {

        private final int x;

        private final int y;

        private SlopeComparator(int x, int y) {
            this.x =  x;
            this.y =  y;
        }

        @Override
        public int compare(Point q, Point r) {
            if (q == null || r == null)
                throw new NullPointerException();

            double pq = slope(this.x, this.y, q.x, q.y);
            double pr = slope(this.x, this.y, r.x, r.y);

            if (pq < pr)
                return -1;
            else if (pq > pr)
                return 1;
            else
                return 0;
        }
    }


}