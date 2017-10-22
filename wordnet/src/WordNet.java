import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mateus on 3/25/16.
 */
public class WordNet {

    private static final int NOUN_ID_INDEX = 0;
    private static final int NOUN_INDEX = 1;
    private static final String CSV_SPLIT_REGEX = ",";
    private static final int HYPERNYM_SOURCE = 0;

    private Map<String, List<Integer>> nouns = new HashMap<>();
    private Map<Integer, String> syns = new HashMap<>();
    private Digraph digraph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        nullCheck(synsets, "Synset");
        nullCheck(hypernyms, "Hypernyms");

        In synsetReader = new In(synsets);
        String[] lines = synsetReader.readAllLines();

        for (String line : lines) {
            String[] data = line.split(CSV_SPLIT_REGEX);
            String[] individualSyns = data[NOUN_INDEX].split(" ");

            int id = Integer.parseInt(data[NOUN_ID_INDEX]);

            for (String syn : individualSyns) {

                if (nouns.containsKey(syn))
                    nouns.get(syn).add(id);
                else
                    nouns.put(syn, new ArrayList<>(Arrays.asList(id)));
            }

            syns.put(id, data[NOUN_INDEX]);
        }

        In hypernymsReader = new In(hypernyms);
        String[] hypernymslines = hypernymsReader.readAllLines();

        digraph = new Digraph(lines.length);

        for (String line : hypernymslines) {
            String[] data = line.split(CSV_SPLIT_REGEX);
            String source = data[HYPERNYM_SOURCE];
            for (int i = 1; i < data.length; i++) {
                digraph.addEdge(Integer.parseInt(source), Integer.parseInt(data[i]));
            }
        }

        int root = -1;
        for (int i = 0; i < digraph.V(); i++) {
            int outdegree = digraph.outdegree(i);

            if (root >= 0 && outdegree == 0)
                throw new IllegalArgumentException("Digraph with multiple roots");

            if (outdegree == 0)
                root = i;
        }

        DirectedCycle cycleDetector = new DirectedCycle(digraph);
        if (cycleDetector.hasCycle()) throw new IllegalArgumentException("WordNet only works with DAGs");

        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        nullCheck(word, "Word");
        return nouns.keySet().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        nullCheck(nounA, "Noun A");
        nullCheck(nounB, "Noun B");

        Iterable<Integer> v = nouns.get(nounA);
        Iterable<Integer> w = nouns.get(nounB);

        if (v == null) throw new IllegalArgumentException(nounA + " is not a valid noun");
        if (w == null) throw new IllegalArgumentException(nounB + " is not a valid noun");

        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        nullCheck(nounA, "Noun A");
        nullCheck(nounB, "Noun B");

        Iterable<Integer> v = nouns.get(nounA);
        Iterable<Integer> w = nouns.get(nounB);

        if (v == null) throw new IllegalArgumentException(nounA + " is not a valid noun");
        if (w == null) throw new IllegalArgumentException(nounB + " is not a valid noun");

        int ancestor = sap.ancestor(v, w);
        return syns.get(ancestor);
    }


    private void nullCheck(Object o, String msg) {
        if (o == null)
            throw new NullPointerException(msg + " cannot be null");
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        WordNet wordNet = new WordNet("/Users/mateus/Documents/workspace/algorithms/wordnet/src/synsets.txt",
//                "/Users/mateus/Documents/workspace/algorithms/wordnet/src/hypernyms.txt");
//
//        System.out.println(wordNet.sap("worm", "bird"));
//
//        wordNet = new WordNet("/Users/mateus/Documents/workspace/algorithms/wordnet/src/synsets15.txt",
//                "/Users/mateus/Documents/workspace/algorithms/wordnet/src/hypernyms15Tree.txt");
//
////        System.out.println(wordNet.sap("invalid", "b"));
////        System.out.println(wordNet.sap("b", "invalid"));
//
////        wordNet = new WordNet("/Users/mateus/Documents/workspace/algorithms/wordnet/src/synsets.txt",
////                "/Users/mateus/Documents/workspace/algorithms/wordnet/src/hypernyms3InvalidCycle.txt");
//
//        wordNet = new WordNet("/Users/mateus/Documents/workspace/algorithms/wordnet/src/synsets.txt",
//                "/Users/mateus/Documents/workspace/algorithms/wordnet/src/hypernyms3InvalidTwoRoots.txt");

//        String root = "/Users/mateus/Documents/workspace/algorithms/wordnet/src/";
//        //WordNet wn = new WordNet(root + "synsets3.txt", root + "hypernyms3InvalidTwoRoots.txt");
//        WordNet wn = new WordNet(root + "synsets6.txt", root + "hypernyms6InvalidTwoRoots.txt");


    }
}
