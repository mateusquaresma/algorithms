import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by mateus on 4/2/16.
 */
public class SeamCarver {

    private static final double BORDER_ENERGY = 1000;
    private Picture picture;


    private int height;
    private int width;
    private int length;

    private double[] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new NullPointerException("Picture cannot be null");

        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        this.length = width * height;

        energy = new double[length];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // same as Vertex.index()
                energy[x + y * width] = energy(x, y);
            }
        }

    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {

        if (x < 0 || x >= width()) throw new IndexOutOfBoundsException("x is out of bounds");
        if (y < 0 || y >= height()) throw new IndexOutOfBoundsException("y is out of bounds");

        if (x == 0 || y == 0) return BORDER_ENERGY;
        if (x == width() - 1 || y == height() - 1) return BORDER_ENERGY;

        Color rightColor = picture.get(x + 1, y);
        Color leftColor = picture.get(x - 1, y);

        int rx = rightColor.getRed() - leftColor.getRed();
        int gx = rightColor.getGreen() - leftColor.getGreen();
        int bx = rightColor.getBlue() - leftColor.getBlue();

        double xDelta = Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);

        Color upperColor = picture.get(x, y - 1);
        Color lowerColor = picture.get(x, y + 1);

        int ry = lowerColor.getRed() - upperColor.getRed();
        int gy = lowerColor.getGreen() - upperColor.getGreen();
        int by = lowerColor.getBlue() - upperColor.getBlue();

        double yDelta = Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2);

        return Math.sqrt(xDelta + yDelta);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[width];

        double[] distTo = new double[length];
        Vertex[] edgeTo = new Vertex[length];

        //List<Vertex> rightMost = new ArrayList<>();

        //Queue<Vertex> q = new Queue<>();
        Queue<Vertex> pq = new PriorityQueue<>();


        for (int i = 0; i < length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        for (int y = 0; y < height; y++) {
            Vertex v = new Vertex(0, y, width, height);
            v.accEnergy = energy[v.index()];
            distTo[v.index()] = energy[v.index()];
            pq.offer(v);
        }

        Vertex target = null;

        while (!pq.isEmpty()) {
            Vertex vertex = pq.poll();

            if (vertex.atRightCorner()) {

                target = vertex;

                break;

//                if (!rightMost.contains(vertex)) {
//
//                    rightMost.add(vertex);
//
//                    if (rightMost.size() == height)
//                        break;
//                }
                //continue;
            }

            //Vertex min = null;
            for (Vertex v : vertex.horizontalAdjacent()) {
                //if (min == null || energy(min.x, min.y) > energy(v.x, v.y))
                //    min = v;

                //if (v != null) {

                if (distTo[v.index()] > distTo[vertex.index()] + energy[v.index()]) {
                    distTo[v.index()] = distTo[vertex.index()] + energy[v.index()];
                    v.accEnergy = distTo[v.index()];
                    edgeTo[v.index()] = vertex;
                }
                pq.offer(v);
                //}
            }
        }


//        double shortest = Double.POSITIVE_INFINITY;
//        Vertex shortestVertex = null;
//        for (Vertex v : rightMost) {
//            if (shortest > distTo[v.index()]) {
//                shortest = distTo[v.index()];
//                shortestVertex = v;
//            }
//        }

        int seamIndex = width - 1;

        for (Vertex v = target; v != null; v = edgeTo[v.index()]) {
            //System.out.println(v);
            seam[seamIndex--] = v.y;
        }

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] seam = new int[height()];

        double[] distTo = new double[length];
        Vertex[] edgeTo = new Vertex[length];

        Queue<Vertex> pq = new PriorityQueue<>(length);

        //List<Vertex> bottomRow = new ArrayList<>();

        Vertex target = null;

        for (int i = 0; i < length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        for (int x = 0; x < width(); x++) {
            Vertex v = new Vertex(x, 0, width, height);
            v.accEnergy = energy[v.index()];
            distTo[v.index()] = energy[v.index()];
            pq.offer(v);
        }

        while (!pq.isEmpty()) {
            Vertex vertex = pq.poll();

            if (vertex.atBottomRow()) {

                target = vertex;
                break;
//                if (!bottomRow.contains(vertex)) {
//
//                    bottomRow.add(vertex);
//
////                    if (bottomRow.size() == width)
////                        break;
//                }
                //continue;
                //break;
            }

            for (Vertex v : vertex.verticalAdjacent()) {
                //if (v != null) {
                //v.energy = energy[v.index()];

                if (distTo[v.index()] > distTo[vertex.index()] + energy[v.index()]) {
                    distTo[v.index()] = distTo[vertex.index()] + energy[v.index()];
                    v.accEnergy = distTo[v.index()];
                    edgeTo[v.index()] = vertex;
                }

                pq.offer(v);
                //}
            }
        }

//        double shortest = Double.POSITIVE_INFINITY;
//        Vertex shortestVertex = null;
//        for (Vertex v : bottomRow) {
//            if (shortest > distTo[v.index()]) {
//                shortest = distTo[v.index()];
//                shortestVertex = v;
//            }
//        }

        int seamIndex = height - 1;

        for (Vertex v = target; v != null; v = edgeTo[v.index()]) {
            //System.out.println(v);
            seam[seamIndex--] = v.x;
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new NullPointerException("Seam cannot be null");
        if (height() <= 1) throw new IllegalArgumentException("");


        if (seam.length < picture.width())
            throw new IllegalArgumentException("Invalid horizontal seam size");

        int prev = seam[0];
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > picture.height() - 1)
                throw new IllegalArgumentException(seam[i] + " not between 0 and " + (picture.height() - 1));
            if (Math.abs(seam[i] - prev) > 1)
                throw new IllegalArgumentException("Difference bigger than 1");

            prev = seam[i];
        }

        Picture p = new Picture(picture.width(), picture.height() - 1);

        for (int x = 0; x < p.width(); x++) {
            int delta = 0;
            for (int y = 0; y < p.height(); y++) {
                //try {
                    if (y == seam[x])
                        delta++;

                    if (y + delta < picture.height())
                        p.set(x, y, picture.get(x, y + delta));

//                } catch (ArrayIndexOutOfBoundsException e) {
//                    System.out.println(e.getMessage() + "; x = " + x + "; y = " + y + "; delta = " + delta);
//                    throw new RuntimeException(e);
//                }
            }
        }

//        /*int i = 0;
//        while (i < p.width()) {
//            int j = 0;
//            while (j < p.height()) {
//
//                if (j == seam[i])
//                    p.set(i, j, picture.get(i, j + 1));
//                else
//                    p.set(i, j, picture.get(i, j));
//
//                j++;
//            }
//            i++;
//        }*/

        picture = p;

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {


        if (seam == null) throw new NullPointerException("Seam cannot be null");
        if (width() <= 1) throw new IllegalArgumentException("");

        if (seam.length < picture.height())
            throw new IllegalArgumentException("Invalid vertical seam size");

        int prev = seam[0];
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > picture.width() - 1)
                throw new IllegalArgumentException(seam[i] + " not between 0 and " + (picture.width() - 1));
            if (Math.abs(seam[i] - prev) > 1)
                throw new IllegalArgumentException("Difference bigger than 1");

            prev = seam[i];
        }

        Picture p = new Picture(picture.width() - 1, picture.height());

        for (int y = 0; y < p.height(); y++) {
            int delta = 0;
            for (int x = 0; x < p.width(); x++) {
                //try {
                    if (x == seam[y])
                        delta++;

                    if (x + delta < picture.width())
                        p.set(x, y, picture.get(x + delta, y));

//                } catch (ArrayIndexOutOfBoundsException e) {
//                    System.out.println(e.getMessage() + "; x = " + x + "; y = " + y + "; delta = " + delta);
//                    throw new RuntimeException(e);
//                }
            }
        }


//        int i = 0;
//        while (i < p.width()) {
//            int j = 0;
//            while (j < p.height()) {
//
//                if (i == seam[j])
//                    p.set(i, j, picture.get(i + 1, j));
//                else
//                    p.set(i, j, picture.get(i, j));
//
//                j++;
//            }
//            i++;
//        }


        picture = p;
    }

    public static void main(String[] args) {

//        String root = "/Users/mateus/Documents/workspace/algorithms/seam-carving/src/";
//        SeamCarver sc;
//        int[] verticalSeam;
//        sc = new SeamCarver(new Picture(root + "3x4.png"));
//        System.out.println(sc.energy(0, 0));
//        System.out.println(sc.energy(2, 0));
//
//        System.out.println(sc.energy(0, 3));
//        System.out.println(sc.energy(2, 3));
//
//        System.out.println(sc.energy(1, 1));
//        System.out.println(sc.energy(1, 2));
//
//        print(sc.findVerticalSeam());
//        print(sc.findHorizontalSeam());


//        sc = new SeamCarver(new Picture(root + "1x8.png"));
//        verticalSeam = sc.findVerticalSeam();
//        print(verticalSeam);

//        sc = new SeamCarver(new Picture(root + "6x5.png"));
//
//        long before = System.currentTimeMillis();
//        verticalSeam = sc.findVerticalSeam();

//        System.out.println("ET = " + (System.currentTimeMillis() - before) + "ms");
//        print(verticalSeam);

//        int[] baseSeam = {0, 0, 0, 0, 0};
//
//        int[] seam = {3, 4, 3, 2, 1};

//        PrintSeams.printSeam(sc, baseSeam, false);
//        sc.removeVerticalSeam(seam);
//        PrintSeams.printSeam(new SeamCarver(sc.picture()), baseSeam, false);

        //sc.removeVerticalSeam(new int[]{0, 2, 0, 0, 0});

//        int[] horizontalSeam = sc.findHorizontalSeam();
//        print(horizontalSeam)

//        testEnergy(sc, -1, 4);
//        testEnergy(sc, 6, 4);
//        testEnergy(sc, 5, 5);
    }

//    private static void testEnergy(SeamCarver sc, int x, int y) {
//        try {
//            sc.energy(x, y);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    private static void print(int[] seam) {
        for (int i : seam) System.out.print(i + " ");
        System.out.println();
    }

    private static final class Vertex implements Comparable<Vertex> {
        private final int x;
        private final int y;
        //private double energy;
        private double accEnergy = Double.POSITIVE_INFINITY;
        private final int width;
        private final int height;

        Vertex(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        int index() {
            return x + y * width;
        }

        boolean atLeftCorner() {
            return x == 0;
        }

        boolean atRightCorner() {
            return x == width - 1;
        }

        boolean atBottomRow() {
            return y == height - 1;
        }

        boolean atTopRow() {
            return y == 0;
        }

        private boolean valid() {
            return x >= 0 && x <= width - 1 && y >= 0 && y <= height - 1;
        }

        List<Vertex> verticalAdjacent() {
            List<Vertex> temp = new ArrayList<>();
            List<Vertex> adj = new ArrayList<>();
            if (atLeftCorner()) {
                temp.add(new Vertex(x, y + 1, width, height));
                temp.add(new Vertex(x + 1, y + 1, width, height));
            } else if (atRightCorner()) {
                temp.add(new Vertex(x - 1, y + 1, width, height));
                temp.add(new Vertex(x, y + 1, width, height));
            } else {
                temp.add(new Vertex(x - 1, y + 1, width, height));
                temp.add(new Vertex(x, y + 1, width, height));
                temp.add(new Vertex(x + 1, y + 1, width, height));
            }

            for (Vertex v : temp)
                if (v.valid())
                    adj.add(v);

            return adj;
        }

        public String toString() {
            return "(" + x + ", " + y + ") - " + " - " + accEnergy;
        }

        public List<Vertex> horizontalAdjacent() {

            List<Vertex> temp = new ArrayList<>();
            List<Vertex> adj = new ArrayList<>();

            if (atTopRow()) {
                temp.add(new Vertex(x + 1, y, width, height));
                temp.add(new Vertex(x + 1, y + 1, width, height));
            } else if (atBottomRow()) {
                temp.add(new Vertex(x + 1, y, width, height));
                temp.add(new Vertex(x + 1, y - 1, width, height));
            } else {
                temp.add(new Vertex(x + 1, y - 1, width, height));
                temp.add(new Vertex(x + 1, y, width, height));
                temp.add(new Vertex(x + 1, y + 1, width, height));
            }

            for (Vertex v : temp)
                if (v.valid())
                    adj.add(v);

            return adj;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (x != vertex.x) return false;
            return y == vertex.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public int compareTo(Vertex that) {
            return Double.compare(accEnergy, that.accEnergy);
        }
    }
}
