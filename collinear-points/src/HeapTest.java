/**
 * Created by mateus on 27/02/15.
 */
public class HeapTest {
    public static void main(String[] args) {

        Integer[] data = new Integer[]{99, 87, 62, 85, 74, 20, 43, 28, 18, 31};

        MaxPQ<Integer> queue = new MaxPQ<Integer>(data);

        print(queue);

        queue.insert(71);
        print(queue);

        queue.insert(72);
        print(queue);

        queue.insert(13);
        print(queue);
    }

    private static void print(MaxPQ<Integer> pq){
        for (Integer key : pq){
            System.out.print(key + " ");
        }
        System.out.println();
    }
}
