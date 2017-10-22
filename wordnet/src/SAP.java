import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;

/**
 * Created by mateus on 3/26/16.
 */
public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException("Digraph cannot be null");
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Arrays.asList(v), Arrays.asList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Arrays.asList(v), Arrays.asList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) throw new NullPointerException("Vertex V cannot be null");
        if (w == null) throw new NullPointerException("Vertex W cannot be null");


        BreadthFirstDirectedPaths bfsa = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsb = new BreadthFirstDirectedPaths(digraph, w);

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsa.hasPathTo(i) && bfsb.hasPathTo(i)) {
                int dist = bfsa.distTo(i) + bfsb.distTo(i);
                if (min > dist) min = dist;
            }
        }

        if (min == Integer.MAX_VALUE)
            return -1;
        else
            return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) throw new NullPointerException("Vertex V cannot be null");
        if (w == null) throw new NullPointerException("Vertex W cannot be null");

        for (int i : v)
            if (i < 0 || i > digraph.V() - 1)
                throw new IndexOutOfBoundsException("Invalid vertex");
        for (int i : w)
            if (i < 0 || i > digraph.V() - 1)
                throw new IndexOutOfBoundsException("Invalid vertex");

        BreadthFirstDirectedPaths bfsa = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsb = new BreadthFirstDirectedPaths(digraph, w);

        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsa.hasPathTo(i) && bfsb.hasPathTo(i)) {
                int dist = bfsa.distTo(i) + bfsb.distTo(i);
                if (min > dist) {
                    min = dist;
                    ancestor = i;
                }
            }
        }
        return ancestor;

    }

    // do unit testing of this class
    public static void main(String[] args) {

//        String root = "/Users/mateus/Documents/workspace/algorithms/wordnet/src/";
//
//        Digraph digraph = new Digraph(new In(root + "digraph1.txt"));
//        SAP sap = new SAP(digraph);
//        StdOut.printf("length = %d, ancestor = %d\n", sap.length(3, 11), sap.ancestor(3, 11));
//        StdOut.printf("length = %d, ancestor = %d\n", sap.length(9, 12), sap.ancestor(9, 12));
//        StdOut.printf("length = %d, ancestor = %d\n", sap.length(7, 2), sap.ancestor(7, 2));
//        StdOut.printf("length = %d, ancestor = %d\n", sap.length(1, 6), sap.ancestor(1, 6));
//
//        digraph = new Digraph(new In(root + "digraph2.txt"));
//        sap = new SAP(digraph);
//        StdOut.printf("length = %d, ancestor = %d\n", sap.length(1, 5), sap.ancestor(1, 5));
//
//        Digraph digraph = new Digraph(new In(root + "digraph-wordnet.txt"));
//        SAP sap  = new SAP(digraph);
//
//        List<Integer> worms = Arrays.asList(new Integer[]{81679, 81680, 81681, 81682});
//        List<Integer> birds = Arrays.asList(new Integer[]{24306, 24307, 25293, 33764, 70067});
//        StdOut.printf("length = %d, ancestor = %d\n", sap.length(worms, birds), sap.ancestor(worms, birds));


    }
}
