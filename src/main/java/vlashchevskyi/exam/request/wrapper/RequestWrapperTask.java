package main.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.request.RequestFactory;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;

import java.util.concurrent.Callable;

/**
 * {@code RequestWrapperTask} class creates a task to wrap row booking request and is designed for multithreading
 * environment.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class RequestWrapperTask implements Callable {
    private static final RequestFactory requestFactory = RequestFactory.getInstance();
    RowBookingRequest rowRequest = null;

    public RequestWrapperTask(RowBookingRequest rowBookingRequest) {
        this.rowRequest = rowBookingRequest;
    }


    @Override
    public Object call() throws Exception {
        return requestFactory.getRequest(rowRequest);
    }
}
