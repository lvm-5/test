package main.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.concurrency.PoolRunner;

import java.util.Map;

/**
 * {@code ConcurrentOverlapResolver} class uses <a href="../concurrency/PoolExecutor.html">PoolExecutor</a> to remove
 * requests with time overlaps from {@code Map}.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/10/2015
 * @see Resolver
 */
public class ConcurrentOverlapResolver implements OverlapResolver {
    PoolRunner poolRunner = null;

    public ConcurrentOverlapResolver() {
        poolRunner = new PoolRunner();
        poolRunner.setTaskType(OverlapsRemoveTask.class);
    }

    @Override
    public void removeOverlaps(Map bookingGroups) {
        poolRunner.setTasks(bookingGroups).run();
    }
}
