package main.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.RequestFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  {@code BasicRequestWrapper} class implements the method to wrap row requests.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class BasicRequestWrapper extends RequestWrapper {
    private static final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public List<BookingRequest> wrapRequests() {
        List<BookingRequest> bookingRequests = new ArrayList<>();

        getRowRequests().forEach(request -> {
            BookingRequest baseRequest = requestFactory.getRequest(request);
            if (!baseRequest.isEmpty() && isWorkTimeAcceptable(baseRequest)) {
                bookingRequests.add(baseRequest);
            }
        });

        return bookingRequests;
    }
}
