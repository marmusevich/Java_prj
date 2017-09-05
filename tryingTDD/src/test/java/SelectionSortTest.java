import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class SelectionSortTest {

    private List<Integer> list;
    private Sorter sorter;

    @Before
    public void setUp() throws Exception {
        list = Arrays.asList(5, 4, 3, 2, 1);
        sorter = new SelectionSort();
    }


    @After
    public void tearDown() throws Exception {
        list = null;
        sorter = null;
    }

    @Test
    public void notNull() throws Exception {
        assertNotNull("returned NUll", sorter.doSort(list));
    }

    @Test
    public void countElements() throws Exception {
        assertEquals(list.size(), sorter.doSort(list).size());
    }

    @Test
    public void doSort() throws Exception {
        List<Integer> expect = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> sortedList = sorter.doSort(list);
        assertArrayEquals(expect.toArray(), sortedList.toArray());
    }
}