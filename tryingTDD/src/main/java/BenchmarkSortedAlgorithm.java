
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;


/**
 * Created by a.marmusevich on 05.09.2017.
// */
public class BenchmarkSortedAlgorithm {
    private final int listSize = 5;



    @Benchmark
    public void measureName() {

        FillerList fill;
        Sorter sorter;

        fill = new RandomFiller();
        List<Integer> list = fill.full(listSize);

        sorter = new BubbleSort();
        List<Integer> sortedList = sorter.doSort(list);
    }



    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkSortedAlgorithm.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();


        System.out.println("BenchmarkSortedAlgorithm");

    }

}


