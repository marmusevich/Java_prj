import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class MainStart {
    static private final int listSize = 15;

    public static void main(String[] args)  {
        Sorter insertionSort = new InsertionSort();
        Sorter bubbleSort = new BubbleSort();
        Sorter selectionSort = new SelectionSort();

        FillerList fill = new RandomFiller();
        List<Integer> list = fill.full(listSize);


        System.out.println("before");
        print(list);

        System.out.println("insertionSort");
        print(insertionSort.doSort(cloneList(list)));

        System.out.println("before");
        print(list);

        System.out.println("bubbleSort");
        print(bubbleSort.doSort(cloneList(list)));

        System.out.println("before");
        print(list);

        System.out.println("selectionSort");
        print(selectionSort.doSort(cloneList(list)));
    }



    public static void print( List<Integer> list) {
        for (int el : list) {
            System.out.print(el + ", ");
        }
        System.out.println("");
    }

    public static List<Integer> cloneList(List<Integer> list) {
        List<Integer> clone = new ArrayList<Integer>(list.size());
        for (Integer item : list)
            clone.add(item);
        return clone;
    }



}
