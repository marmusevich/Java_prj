import org.openjdk.jmh.annotations.Benchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class BubbleSort implements Sorter  {

    @Override
    public List<Integer> doSort(List<Integer> list) {
        for(int j = 0; j < list.size(); j++){
            boolean f = true;
            for(int i = 0; i < list.size() - j -1 ; i++){
                if(list.get(i) > list.get(i+1)){
                    swap(list, i, i+1);
                    f = false;
                }
            }
            if(f) break;
        }
        return list;
    }

    private void swap(List<Integer> list, int i, int k) {
        int temp = list.get(i);
        list.set(i, list.get(k));
        list.set(k, temp);
    }



}
