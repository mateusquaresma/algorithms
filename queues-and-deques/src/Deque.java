import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;

    private Node<Item> tail;

    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be null");

        Node<Item> newFirst;

        if (isEmpty()) {
            newFirst = new Node<>(item, null, null);
            this.head = newFirst;
            this.tail = newFirst;
        } else {
            Node<Item> old = this.head;
            newFirst = new Node<>(item, null, old);
            old.head = newFirst;
            this.head = newFirst;
        }

        size++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be null");

        Node<Item> newLast;

        if (isEmpty()) {
            newLast = new Node<>(item, null, null);
            this.head = newLast;
            this.tail = newLast;
        } else {
            Node<Item> old = this.tail;
            newLast = new Node<>(item, old, null);
            old.tail = newLast;
            this.tail = newLast;
        }

        size++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Nothing to remove");

        Node<Item> old = this.head;
        Node<Item> newFirst = old.tail;

        // last element
        if (newFirst == null) {
            this.head = null;
            this.tail = null;
        } else {
            newFirst.head = null;
            this.head = newFirst;
        }

        size--;

        return old.value;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Nothing to remove");

        Node<Item> old = this.tail;
        Node<Item> newLast = old.head;

        //last element
        if (newLast == null) {
            this.head = null;
            this.tail = null;
        } else {
            newLast.tail = null;
            this.tail = newLast;
        }

        size--;

        return old.value;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(this.head);
    }

    private class Node<Item> {
        private Item value;

        private Node<Item> head;

        private Node<Item> tail;

        private Node(Item value, Node<Item> head, Node<Item> tail) {
            this.value = value;
            this.head = head;
            this.tail = tail;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current;

        private DequeIterator(Node<Item> head) {
            current = head;
        }

        @Override
        public boolean hasNext() {

            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("Nothing to return");

            Item value = current.value;
            current = current.tail;

            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        npeTest(deque);
        nseTest(deque);

        testAddingAtTheHead(deque);

        testAddingAtTheTail(deque);
    }

    private static void testAddingAtTheTail(Deque<Integer> deque) {
        /*deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        int expected = 1;

        for (Integer i : deque) {
            System.out.println(String.format("expected=%d, found=%d, " +
                    "result=%s", expected, i, i == expected));
            expected++;
        }

        System.out.println("removing...");

        int i = deque.removeLast();
        System.out.println(String.format("expected=%d, found=%d, " +
                "result=%s", 3, i, i == 3));
        System.out.println(String.format("expected-size=%d, found=%d, " +
                        "result=%s, empty=%s", 2, deque.size(), 2 == deque
                        .size(),
                deque.isEmpty()));

        i = deque.removeFirst();
        System.out.println(String.format("expected=%d, found=%d, " +
                "result=%s", 1, i, i == 1));
        System.out.println(String.format("expected-size=%d, found=%d, " +
                        "result=%s, empty=%s", 1, deque.size(), 1 == deque
                        .size(),
                deque.isEmpty()));

        i = deque.removeLast();
        System.out.println(String.format("expected=%d, found=%d, " +
                "result=%s", 2, i, i == 2));
        System.out.println(String.format("expected-size=%d, found=%d, " +
                        "result=%s, empty=%s", 0, deque.size(), 0 == deque
                        .size(),
                deque.isEmpty()));*/
    }

    private static void testAddingAtTheHead(Deque<Integer> deque) {
        /*deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);

        int expected = 3;

        for (Integer i : deque) {
            System.out.println(String.format("expected=%d, found=%d, " +
                    "result=%s", expected, i, i == expected));
            expected--;
        }

        System.out.println("removing...");

        int i = deque.removeFirst();
        System.out.println(String.format("expected=%d, found=%d, " +
                "result=%s", 3, i, i == 3));
        System.out.println(String.format("expected-size=%d, found=%d, " +
                        "result=%s, empty=%s", 2, deque.size(), 2 == deque
                        .size(),
                deque.isEmpty()));

        i = deque.removeLast();
        System.out.println(String.format("expected=%d, found=%d, " +
                "result=%s", 1, i, i == 1));
        System.out.println(String.format("expected-size=%d, found=%d, " +
                        "result=%s, empty=%s", 1, deque.size(), 1 == deque
                        .size(),
                deque.isEmpty()));

        i = deque.removeLast();
        System.out.println(String.format("expected=%d, found=%d, " +
                "result=%s", 2, i, i == 2));
        System.out.println(String.format("expected-size=%d, found=%d, " +
                        "result=%s, empty=%s", 0, deque.size(), 0 == deque
                        .size(),
                deque.isEmpty()));*/
    }

    private static void npeTest(Deque<?> deque) {
        try {
            deque.addFirst(null);
        } catch (NullPointerException e) {
            System.out.println("addFirst NPE Test OK");
        }

        try {
            deque.addLast(null);
        } catch (NullPointerException e) {
            System.out.println("addLast NPE Test OK");
        }
    }

    private static void nseTest(Deque<?> deque) {
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("removeFirst NSE Test OK");
        }

        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println("removeLast NSE Test OK");
        }
    }

}