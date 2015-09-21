package main.java.vlashchevskyi.exam.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * {@code PoolExecutor} is abstract class provides methods to create and configure a thread pool.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public abstract class PoolExecutor<T> {
    /**
     * The pool to run tasks.
     */
    protected final ExecutorService pool;
    private final TypeBuilder builder = new TypeBuilder();

    /**
     * The class of task that will be used to construct its instance.
     */
    protected Class<T> taskType = null;

    /**
     * The collection of tasks to run with {@code pool}.
     */
    protected List<T> tasks = null;

    /**
     * Constructs a child instance.
     */
    public PoolExecutor() {
        pool = PoolCreator.createPool();
    }

    /**
     * Constructs a child instance and initialize pool with defined size.
     *
     * @param size the {@code int} value of pool size.
     */
    public PoolExecutor(int size) {
        pool = PoolCreator.createPool(size);
    }

    /**
     * Gets tasks are ready to be run by pool.
     *
     * @return the {@code List} of tasks.
     */
    public List<T> getTasks() {
        return tasks;
    }

    /**
     * Sets tasks and constructs instances dependent on {@code <V>} argument.
     *
     * @param arguments the {@code Map} contains constructor arguments.
     * @param <K>       the key for constructor argument.
     * @param <V>       the value of argument.
     * @return the current {@code PoolExecutor} instance.
     */
    public <K, V> PoolExecutor setTasks(Map<K, V> arguments) {
        tasks = new ArrayList();
        arguments.forEach((key, argument) -> {
            tasks.add(builder.<T, V>build(taskType, argument));
        });

        return this;
    }

    /**
     * Sets tasks and constructs instances dependent on {@code <U>} argument.
     *
     * @param arguments the {@code Collection} contains constructor arguments.
     * @param <U>       the value of argument.
     * @return the current {@code PoolExecutor} instance.
     */
    public <U> PoolExecutor setTasks(Collection<U> arguments) {
        tasks = new ArrayList();
        arguments.forEach(argument -> {
            tasks.add(builder.<T, U>build(taskType, argument));
        });

        return this;
    }

    /**
     * Verifies whether {@code PoolExecutor} empty or not.
     *
     * @return the {@code boolean} state of {@code PoolExecutor}.
     */
    public boolean isEmpty() {
        return tasks == null || tasks.isEmpty();
    }

    /**
     * Gets class of task is going to be run by pool.
     *
     * @return the {@code Class} of tasks.
     */
    public Class getTaskType() {
        return taskType;
    }

    /**
     * Sets class of task is going to be run by pool.
     *
     * @param taskType the {@code Class} of task
     */
    public void setTaskType(Class taskType) {
        this.taskType = taskType;
    }
}
