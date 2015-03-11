import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INITIAL_CAPACITY = 1;

    private Item[] items;

    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_CAPACITY];
        this.tail = 0;
    }

    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];

        int length = 0;

        if (newSize >= items.length)
            length = items.length;
        else
            length = newSize;

        for (int i = 0; i < length; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return tail == 0 && items[tail] == null;
    }

    // tail will always represent the first null index and the number of
    // items on the queue
    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (tail == items.length)
            resize(items.length * 2);

        items[tail++] = item;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("empty queue");

        if (tail > 0 && tail == items.length / 4)
            resize(items.length / 2);

        tail--;

        // generate a random
        int i = 0;
        if (tail > 0)
            i = StdRandom.uniform(tail);

        //prepare the random to be returned
        Item item = items[i];

        // swap it with the last tail
        items[i] = items[tail];

        // clear the last tail
        items[tail] = null;

        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("empty queue");

        if (tail > 0)
            return items[StdRandom.uniform(tail)];
        else
            return items[0];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(items);
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private Item[] items;

        private int maxIndex;

        private int currentIndex;

        private RandomizedQueueIterator(Item[] items) {
            this.currentIndex = 0;

            this.items = (Item[]) new Object[items.length];

            for (int i = 0; i < items.length; i++)
                this.items[i] = items[i];

            StdRandom.shuffle(this.items);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < items.length;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("nothing to return");

            return items[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        int i = 0;
        /*while (i < 3000) {

            queue.enqueue("a");
            queue.enqueue("b");
            queue.enqueue("c");
            queue.enqueue("d");
            queue.enqueue("e");
            queue.enqueue("f");
            while (!queue.isEmpty()) {
                String dequeued = queue.dequeue();

                if ("a".equals(dequeued))
                    break;
            }

            i++;
        }*/

        i = 0;
        while (i < 10000) {

            queue = new RandomizedQueue<>();
            queue.enqueue("a");
            queue.enqueue("b");
            queue.enqueue("c");
            queue.enqueue("d");
            queue.enqueue("e");
            queue.enqueue("f");
            queue.enqueue("g");
            queue.enqueue("h");
            queue.enqueue("i");
            queue.enqueue("j");

            int j = 0;
            while (!queue.isEmpty()) {
                String dequeued = queue.dequeue();
                j++;

                if ("g".equals(dequeued)) {
                    System.out.println(j);
                    break;
                }
            }

            i++;
        }

        /*RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);
        queue.enqueue(10);

        System.out.println(String.format("expected=%d, found=%d, result=%s",
                10, queue.size(), 10 == queue.size));


        for (Integer i : queue) {
            System.out.print(i);
            System.out.print(" ");
        }

        System.out.println();

        for (Integer i : queue) {
            System.out.print(i);
            System.out.print(" ");
        }

        System.out.println();

        for (Integer i : queue) {
            System.out.print(i);
            System.out.print(" ");
        }

        System.out.println("\n----------");

        for (int i = 0; i < 5; i++) {
            System.out.print(queue.sample());
            System.out.print(" ");
        }

        System.out.println();

        for (int i = 0; i < 5; i++) {
            System.out.print(queue.sample());
            System.out.print(" ");
        }

        System.out.println();

        for (int i = 0; i < 5; i++) {
            System.out.print(queue.sample());
            System.out.print(" ");
        }

        System.out.println("\n----------");

        queue.enqueue(11);
        queue.enqueue(12);
        queue.enqueue(13);
        queue.enqueue(14);
        queue.enqueue(15);
        queue.enqueue(16);
        queue.enqueue(17);
        queue.enqueue(18);
        queue.enqueue(19);
        queue.enqueue(20);

        for (int i = 0; i < 16; i++) {
            System.out.print(queue.dequeue());
            System.out.print(" ");
        }

        System.out.println("\n----------");

        System.out.println(queue.size());*/
    }
}
