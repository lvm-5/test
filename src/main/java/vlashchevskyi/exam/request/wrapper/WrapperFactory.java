package main.java.vlashchevskyi.exam.request.wrapper;

/**
 * {@code WrapperFactory} class is factory to create wrapper dependent on conditions.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class WrapperFactory {
    private static final String LOCKER = "locker";
    private static WrapperFactory instance = null;

    private WrapperFactory() {

    }

    public static WrapperFactory getInstance() {
        synchronized (LOCKER) {
            if (instance == null) {
                instance = new WrapperFactory();
            }

            return instance;
        }
    }

    public RequestWrapper getRequestWrapper(Boolean concurrentMode) {
        RequestWrapper wrapper = null;

        if (concurrentMode) {
            wrapper = new ConcurrentRequestWrapper();
        } else {
            wrapper = new BasicRequestWrapper();
        }

        return wrapper;
    }

}
