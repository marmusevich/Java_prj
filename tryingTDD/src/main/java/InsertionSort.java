import java.util.List;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class InsertionSort implements Sorter {
    @Override
    public List<Integer> doSort(List<Integer> list) {
        for (int  i=0; i < list.size(); i++) {  // цикл проходов, i - номер прохода
        int x = list.get(i);
        // поиск места элемента в готовой последовательности
        int  j=i-1;
        for (; j>=0 && list.get(j) > x; j--)
            list.set( j+1, list.get(j));  	// сдвигаем элемент направо, пока не дошли
            // место найдено, вставить элемент
            list.set(j+1, x);
        }
        return list;
    }

    private void swap(List<Integer> list, int i, int k) {
        int temp = list.get(i);
        list.set(i, list.get(k));
        list.set(k, temp);
    }
}

//template<class T>
//        void insertSort(T a[], long size) {
//        T x;
//        long i, j;
//
//        for ( i=0; i < size; i++) {  // цикл проходов, i - номер прохода
//        x = a[i];
//
//        // поиск места элемента в готовой последовательности
//        for ( j=i-1; j>=0 && a[j] > x; j--)
//        a[j+1] = a[j];  	// сдвигаем элемент направо, пока не дошли
//
//        // место найдено, вставить элемент
//        a[j+1] = x;
//        }
//        }