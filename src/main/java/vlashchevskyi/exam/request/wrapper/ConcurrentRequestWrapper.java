package main.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.concurrency.PoolCaller;
import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;
import main.java.vlashchevskyi.exam.time.WorkTime;

import java.util.List;
import java.util.Set;

/**
 * {@code ConcurrentRequestWrapper} class implements the method to wrap row requests throughout thread pool.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class ConcurrentRequestWrapper extends RequestWrapper {
    private PoolCaller<BookingRequest> poolCaller = null;

    /**
     * Constructs an instance and initialize thread pool
     */
    public ConcurrentRequestWrapper() {
        poolCaller = new PoolCaller();
        poolCaller.setTaskType(RequestWrapperTask.class);
        poolCaller.setCondition((request) -> {
            return !request.isEmpty() && isWorkTimeAcceptable(request);
        });
    }

    @Override
    public RequestWrapper getFilled(WorkTime workHours, Set<RowBookingRequest> rowRequests) {
        return super.getFilled(workHours, rowRequests);
    }

    @Override
    public List<BookingRequest> wrapRequests() {

        List<BookingRequest> bookingRequests = poolCaller.setTasks(getRowRequests()).call();

        return bookingRequests;
    }
}
