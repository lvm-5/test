package test.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.request.wrapper.ConcurrentRequestWrapper;
import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/13/2015
 */
public class ConcurrentRequestWrapperTest extends RequestWrapperTest {
    protected RequestWrapper wrapper = new ConcurrentRequestWrapper();

    @Override
    protected RequestWrapper getWrapper() {
        return wrapper;
    }
}
