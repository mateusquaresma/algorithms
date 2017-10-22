/**
 * Created by mateus on 3/26/16.
 */
public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        int max = Integer.MIN_VALUE;
        String outcast = null;

        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;

            for (int j = 0; j < nouns.length; j++)
                distance += wordnet.distance(nouns[i], nouns[j]);

            if (max < distance)
                outcast = nouns[i];
        }

        return outcast;
    }

    // see test client below
    public static void main(String[] args) {

//        String root = "/Users/mateus/Documents/workspace/algorithms/wordnet/src/";
//        String[] files = new String[]{"outcast5.txt", "outcast8.txt", "outcast11.txt"};
//
//        WordNet wordnet = new WordNet(root + "synsets.txt", root + "hypernyms.txt");
//        Outcast outcast = new Outcast(wordnet);
//
//        for (int t = 0; t < files.length; t++) {
//            In in = new In(root + files[t]);
//            String[] nouns = in.readAllStrings();
//            StdOut.println(files[t] + ": " + outcast.outcast(nouns));
//        }
    }
}
