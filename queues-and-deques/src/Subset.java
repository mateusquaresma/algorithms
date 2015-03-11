/**
 * Created by mateus on 8/02/15.
 */
public class Subset {
    public static void main(String[] args) {

        if (args.length <= 0)
            throw new IllegalArgumentException("inform k");

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        int i = 0;
        while (!queue.isEmpty()) {
            i++;
            StdOut.println(queue.dequeue());
            if (i == k)
                break;
        }

    }
}
