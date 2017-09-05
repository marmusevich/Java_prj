import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class bubbleSort implements Sorter  {
    public bubbleSort() {
    }

    @Override
    public List<Integer> doSort(List<Integer> list) {

//        ЦИКЛ ДЛЯ J=1 ДО N-1 ШАГ 1                       FOR J=1 TO N-1 STEP 1
//        F=0                                             F=0
//        ЦИКЛ ДЛЯ I=1 ДО N-J ШАГ 1                       FOR I=1 TO N-J STEP 1
//        ЕСЛИ A[I] > A[I+1] ТО ОБМЕН A[I],A[I+1]:F=1     IF A[I]>A[I+1] THEN SWAP A[I],A[I+1]:F=1
//        СЛЕДУЮЩЕЕ I                                     NEXT I
//        ЕСЛИ F=0 ТО ВЫХОД ИЗ ЦИКЛА                      IF F=0 THEN EXIT FOR
//        СЛЕДУЮЩЕЕ J                                     NEXT J

        return list;
    }
}
