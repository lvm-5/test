package main.java.vlashchevskyi.exam.concurrency;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * {@code PoolRunner} class creates thread pool adopted to run {@code Runnable} tasks
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class PoolRunner extends PoolExecutor<Runnable> {

    /**
     * Constructs and initializes a child instance.
     */
    public PoolRunner() {
    }

    /**
     * Constructs a child instance and initializes pool with defined size.
     *
     * @param size the {@code int} value of pool size.
     */
    public PoolRunner(int size) {
        super(size);
    }


    /**
     * Runs tasks have been set up to {@code PoolExecutor} pool.
     */
    public void run() {
        if (!isEmpty()) {
            submit();
            pool.shutdown();
            while (!pool.isTerminated()) {
            }
        }
    }

    private void submit() {
        preLoad();
        tasks.forEach((task) -> {
            pool.submit(task);
        });
    }

    private void preLoad() {
        ExecutorService primer = PoolCreator.createPool();

        int pointer = tasks.size() - 1;
        primer.submit(tasks.get(pointer));
        tasks.remove(pointer);

        primer.shutdown();
        while (!primer.isTerminated()) {
        }
    }

    @Override
    public <K, V> PoolRunner setTasks(Map<K, V> arguments) {
        return (PoolRunner) super.setTasks(arguments);
    }

    @Override
    public <U> PoolExecutor setTasks(Collection<U> arguments) {
        return super.setTasks(arguments);
    }
}
