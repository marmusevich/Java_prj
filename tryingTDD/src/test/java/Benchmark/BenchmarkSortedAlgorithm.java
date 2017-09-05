package Benchmark;

import org.junit.runner.manipulation.Sorter;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.ArrayList;
import java.util.List;

import  sorter.*;
/**
 * Created by a.marmusevich on 05.09.2017.
// */
@State(Scope.Benchmark)
//@State(Scope.Thread)
public class BenchmarkSortedAlgorithm {
    @Param({"50", "5000"})
    public int size;


    @Benchmark
    public void insertionSort() {
        List<Integer> list = getIntegers(size);

        InsertionSort insertionSort;
        insertionSort = new sorter.InsertionSort();
        insertionSort.doSort(list);
    }

    @Benchmark
    public void bubbleSort() {
        List<Integer> list = getIntegers(size);

        BubbleSort bubbleSort;
        bubbleSort = new sorter.BubbleSort();
        bubbleSort.doSort(list);
    }

    @Benchmark
    public void selectionSort() {
        List<Integer> list = getIntegers(size);

        SelectionSort selectionSort;
        selectionSort = new sorter.SelectionSort();
        selectionSort.doSort(list);
    }

    private List<Integer> getIntegers(int size) {
        List<Integer> list;
        FillerList fill = new RandomFiller();
        list = fill.full(size);
        return list;
    }




    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(BenchmarkSortedAlgorithm.class.getSimpleName())
                .forks(1)
                .warmupIterations(2)
                .measurementIterations(2)
                .build();

        new Runner(opt).run();
    }
}


