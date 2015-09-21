package main.java.vlashchevskyi.exam.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * {@code PoolCreator} class creates certain kind of thread pool according to parameters.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 6/6/2015
 */
public class PoolCreator {

    private PoolCreator() {

    }

    /**
     * Creates pool with defined size.
     *
     * @param size  the fixed size of {@code ExecutorService} pool.
     * @return the {@code ExecutorService} pool.
     */
    public static ExecutorService createPool(Integer size) {
        ExecutorService pool = Executors.newFixedThreadPool(size);
        return pool;
    }

    /**
     * Creates pool with size defined at runtime
     *
     * @return the {@code ExecutorService} pool.
     */
    public static ExecutorService createPool() {
        ForkJoinPool pool = new ForkJoinPool();
        return pool;
    }
}
