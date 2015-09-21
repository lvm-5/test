package main.java.vlashchevskyi.exam.concurrency;

import main.java.vlashchevskyi.exam.tool.Tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * {@code PoolCaller} class creates thread pool adopted to run {@code Callable} tasks.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class PoolCaller<U> extends PoolExecutor<Callable> {
    private Tool.ResultAction<Boolean, U> condition = null;

    /**
     * Constructs and initializes a child instance.
     */
    public PoolCaller() {
        init();
    }

    /**
     * Constructs a child instance and initializes pool with defined size.
     *
     * @param size the {@code int} value of pool size.
     */
    public PoolCaller(int size) {
        super(size);
        init();
    }

    private void init() {
        condition = (result) -> {
            return true;
        };
    }

    /**
     * Runs tasks have been set up to {@code PoolExecutor} pool.
     *
     * @return  the {@code List} of {@code <U>} results
     */
    public List<U> call() {
        List<U> results = new ArrayList<>();
        if (!isEmpty()) {
            preLoad(results);
            if (getTasks().size() > 0) {
                results.addAll(gather(submit()));
            }
        }

        return results;
    }

    /*
     * Submits pool tasks that have been added to {@code PoolExecutor}.
     *
     * @return the {@code List} of futures.
     */
    private List<Future> submit() {
        List<Future> futures = new ArrayList();
        tasks.forEach((task) -> {
            futures.add(pool.submit(task));
        });

        return futures;
    }


    private void preLoad(List<U> results) {
        ExecutorService primer = PoolCreator.createPool();

        int pointer = tasks.size() - 1;
        Future future = primer.submit(tasks.get(pointer));
        U result = catchResult(future);
        addResult(results, result);

        tasks.remove(pointer);

        primer.shutdown();
        while (!primer.isTerminated()) {
        }
    }

    private List<U> gather(List<Future> futures) {
        List<U> futureResults = new ArrayList<>();
        try {
            Thread.currentThread().setPriority(1);

            int pointer = futures.size() - 1;
            while (pointer >= 0) {
                Future future = futures.get(pointer);
                if (future.isDone()) {
                    U result = catchResult(future);
                    addResult(futureResults, result);
                    futures.remove(pointer);
                    pointer--;
                }
            }
        } finally {
            Thread.currentThread().setPriority(5);
            pool.shutdown();
        }

        return futureResults;
    }

    private U catchResult(Future future) {
        U result = null;
        try {
            do {
                result = (U) future.get();
            } while (!future.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean addResult(List<U> results, U result) {
        boolean status = result != null && evaluate(result);
        if (status) {
            results.add(result);
        }

        return status;
    }

    private boolean evaluate(U futureResult) {
        return condition.doIt(futureResult);
    }

    /**
     * Sets up condition to be used at gathering task's results.
     *
     * @param condition the {@code Tool.ResultAction} represents code is going to be perform.
     */
    public void setCondition(Tool.ResultAction<Boolean, U> condition) {
        this.condition = condition;
    }

    @Override
    public <K, V> PoolCaller setTasks(Map<K, V> arguments) {
        return (PoolCaller) super.setTasks(arguments);
    }

    @Override
    public <U> PoolCaller setTasks(Collection<U> arguments) {
        return (PoolCaller) super.setTasks(arguments);
    }
}
