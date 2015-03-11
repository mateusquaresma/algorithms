/**
 * Created by mateus on 21/02/15.
 */
public class MergeSortTest {

    public static void main(String[] args) {

        Integer[] numbers = new Integer[]{78, 10, 55, 79, 18, 12, 68, 13, 27, 41, 23, 90};

        Merge.sort(numbers);

        for (Integer i : numbers)
            System.out.print(i + " ");

    }

}
