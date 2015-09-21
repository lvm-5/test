package main.java.vlashchevskyi.exam.resolver;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public class ResolverFactory {
    private static final String LOCKER = "locker";
    private static ResolverFactory instance = null;

    private ResolverFactory() {

    }

    public static ResolverFactory getInstance() {
        synchronized (LOCKER) {
            if (instance == null) {
                instance = new ResolverFactory();
            }

            return instance;
        }
    }

    public OverlapResolver getResolver(Boolean concurrentMode) {
        OverlapResolver resolver = null;

        if (concurrentMode) {
            resolver = new ConcurrentOverlapResolver();
        } else {
            resolver = new BasicOverlapResolver();
        }

        return resolver;
    }

}
