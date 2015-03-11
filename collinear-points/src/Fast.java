import java.util.Arrays;

public class Fast {

    public static void main(String[] args) {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("provide an input file");


        In in = null;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        try {
            in = new In(args[0]);

            if (!in.isEmpty()) {
                int counter = in.readInt();

                Point[] points = new Point[counter];
                Point[] refPoints = new Point[counter];

                int index = 0;

                while (!in.isEmpty()) {
                    points[index] = new Point(in.readInt(), in.readInt());
                    refPoints[index] = points[index];
                    points[index++].draw();
                }

                Point[] collinear;

                for (int i = 0; i < refPoints.length; i++) {
                    collinear = new Point[points.length];

                    Point first = refPoints[i];

                    double refSlope = 0;

                    Arrays.sort(points, first.SLOPE_ORDER);

                    int j = 0;
                    while (j < points.length) {

                        int cnt = 0;
                        Point second = points[j];
                        refSlope = first.slopeTo(second);

                        if (refSlope == Double.NEGATIVE_INFINITY) {
                            j++;
                            continue;
                        }

                        int k = j;
                        while (k < points.length && first.slopeTo(points[k])
                                <= refSlope) {

                            if (first.slopeTo(points[k]) == refSlope)
                                collinear[cnt++] = points[k];

                            k++;
                        }

                        if (cnt >= 3) {

                            collinear[cnt] = first;
                            Point[] cleaned = Arrays.copyOf(collinear, cnt + 1);
                            Arrays.sort(cleaned);

                            if (first == cleaned[0]) {
                                print(cleaned);
                                draw(cleaned);
                            }
                        }

                        if (cnt == 0)
                            j++;
                        else
                            j += cnt;
                    }
                }
            }

        } finally {
            if (in != null)
                in.close();
        }
    }

    private static void print(int length, Point... points) {

        for (int i = 0; i < length; i++) {

            System.out.print(points[i]);

            if (i < length - 1)
                System.out.print(" -> ");
        }

        System.out.println();
    }

    private static void print(Point... points) {
        print(points.length, points);
    }

    private static void draw(Point... points) {
        points[0].drawTo(points[points.length - 1]);
    }
}