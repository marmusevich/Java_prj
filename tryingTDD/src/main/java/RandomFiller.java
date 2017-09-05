import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by a.marmusevich on 05.09.2017.
 */
public class RandomFiller implements FillerList {
    @Override
    public List<Integer> full(int countElements) {
        List<Integer> list = new ArrayList<>(countElements);
        Random rand = new Random();
        for (int i = 0; i< countElements; i++) {
            list.add(rand.nextInt());
        }
        return list;
    }
}
