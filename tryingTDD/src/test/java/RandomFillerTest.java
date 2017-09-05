import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class RandomFillerTest {
    private final int listSize = 5;
    private FillerList fill;

    @Before
    public void setUp() throws Exception {
        fill = new RandomFiller();
    }

    @After
    public void tearDown() throws Exception {
        fill = null;
    }


    @Test
    public void notNull() throws Exception {
        assertNotNull("returned NUll", fill.full(listSize));
    }

    @Test
    public void countElements() throws Exception {
        assertEquals(listSize, fill.full(listSize).size());
    }

    @Test
    public void allElementsNotNull() throws Exception {
        List<Integer> l = fill.full(listSize);
        for (int el : l) {
            assertNotNull("element is NUll", el);
        }
    }
}