import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("provide an input file");

        //        Stopwatch sw = new Stopwatch();

        In in = null;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        try {
            in = new In(args[0]);

            if (!in.isEmpty()) {
                int counter = in.readInt();

                Point[] points = new Point[counter];

                int index = 0;

                while (!in.isEmpty()) {
                    points[index] = new Point(in.readInt(), in.readInt());
                    points[index++].draw();
                }

                for (int i = 0; i < points.length; i++) {
                    for (int j = i + 1; j < points.length; j++) {
                        for (int k = j + 1; k < points.length; k++) {
                            for (int l = k + 1; l < points.length; l++) {

                                double ij = points[i].slopeTo(points[j]);
                                double ik = points[i].slopeTo(points[k]);
                                double il = points[i].slopeTo(points[l]);

                                boolean isCollinear = (ij == ik && ij == il
                                        && ik == il);

                                if (isCollinear) {

                                    Point[] collinear = new
                                            Point[]{points[i], points[j],
                                            points[k], points[l]};

                                    Arrays.sort(collinear);
                                    print(collinear);
                                    draw(collinear);
                                }

                            }
                        }
                    }
                }
            }

        } finally {
            if (in != null)
                in.close();
        }

        //      System.out.print("Total time to run: " + sw.elapsedTime() +
        // "s");
    }

    private static void print(Point... points) {

        for (int i = 0; i < points.length; i++) {
            if (i == points.length - 1)
                System.out.println(points[i]);
            else
                System.out.print(points[i] + " -> ");
        }
    }

    private static void draw(Point... points) {
        points[0].drawTo(points[points.length - 1]);
    }

}