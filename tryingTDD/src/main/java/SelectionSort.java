import java.util.List;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class SelectionSort implements Sorter {
    @Override
    public List<Integer> doSort(List<Integer> list) {

        for (int min = 0; min < list.size()-1;min++) {
            int least = min;
            for (int j=min+1;j<list.size();j++) {
                if(list.get(j) < list.get(least)) {
                        least = j;
                }
            }
            swap(list, min, least);
       	}

        return list;
    }

    private void swap(List<Integer> list, int i, int k) {
        int temp = list.get(i);
        list.set(i, list.get(k));
        list.set(k, temp);
    }
}
