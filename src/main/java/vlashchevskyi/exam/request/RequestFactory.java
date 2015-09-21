package main.java.vlashchevskyi.exam.request;

/**
 * {@code RequestFactory} is factory to create requests by condition.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/4/2015
 *
 */
public class RequestFactory {
    private static final String LOCKER = "locker";
    private static RequestFactory instance = null;

    private RequestFactory() {

    }

    public static RequestFactory getInstance() {
        synchronized (LOCKER) {
            if (instance == null) {
                instance = new RequestFactory();
            }

            return instance;
        }
    }

    public BookingRequest getRequest(RowBookingRequest request) {
        return new BaseBookingRequest(request);
    }
}
