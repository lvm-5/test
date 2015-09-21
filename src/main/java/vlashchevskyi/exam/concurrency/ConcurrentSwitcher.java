package main.java.vlashchevskyi.exam.concurrency;

import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;
import main.java.vlashchevskyi.exam.request.wrapper.WrapperFactory;
import main.java.vlashchevskyi.exam.resolver.OverlapResolver;
import main.java.vlashchevskyi.exam.resolver.ResolverFactory;

/**
 * {@code ConcurrentSwitcher} class defines type of object dependent on threading mode state.
 * It includes {@code RequestWrapper} and {@code OverlapResolver} objects.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class ConcurrentSwitcher {
    /**
     * The constant defines effective threshold for multithreading mode and represents amount of booking requests are
     * going to be handled.
     */
    public static final int THRESHOLD_OF_CONCURRENCE = 10000;

    private static final Object LOCKER = "locker";
    private static final WrapperFactory wrapperFactory = WrapperFactory.getInstance();
    private static final ResolverFactory resolverFactory = ResolverFactory.getInstance();
    private static ConcurrentSwitcher instance = null;
    private Boolean multithreadingMode = null;

    private ConcurrentSwitcher() {
        multithreadingMode = true;
    }

    /**
     * Create an instance as singleton.
     *
     * @return  the {@code }
     */
    public static ConcurrentSwitcher getInstance() {
        synchronized (LOCKER) {
            if (instance == null) {
                instance = new ConcurrentSwitcher();
            }

            return instance;
        }
    }

    /**
     * Gets current state of multithreading mode.
     *
     * @return the {@code Boolean} is as state of multithreading mode.
     */
    public Boolean getMultithreadingMode() {
        return multithreadingMode;
    }

    /**
     * Sets up new value of multithreading mode.
     *
     * @param multithreadingMode    the new {Boolean} value of multithreading mode.
     */
    public void setMultithreadingMode(Boolean multithreadingMode) {
        this.multithreadingMode = multithreadingMode;
    }

    /**
     * Gets an wrapper according to multithreading mode.
     *
     * @return the {@code RequestWrapper} object.
     */
    public RequestWrapper getWrapper() {
        return wrapperFactory.getRequestWrapper(multithreadingMode);
    }

    /**
     * Gets an overlap resolver according to multithreading mode.
     *
     * @return the {@code OverlapResolver} object.
     */
    public OverlapResolver getOverlapResolver() {
        return resolverFactory.getResolver(multithreadingMode);
    }

}
