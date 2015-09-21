package test.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;
import main.java.vlashchevskyi.exam.request.wrapper.BasicRequestWrapper;
import main.java.vlashchevskyi.exam.request.wrapper.BlankRequestWrapper;
import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;
import main.java.vlashchevskyi.exam.time.WorkTime;
import main.java.vlashchevskyi.exam.tool.Artifact;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/13/2015
 */
public class RequestWrapperTest {
    
    private RequestWrapper wrapper = new BasicRequestWrapper();
    protected static final WorkTime workTime = Artifact.buildWorkTime();
    protected static final Set<RowBookingRequest> requests = Artifact.buildRowBookingRequests();


    @Test
    public void getFilled_RequestWrapper_HoursAndRequests() {
        wrapper = getWrapper().getFilled(workTime, requests);
        assertEquals(workTime, wrapper.getWorkTime());
        assertEquals(requests, wrapper.getRowRequests());
    }

    @Test
    public void getFilled_RequestWrapper_WorkTimeIsNull() {
        wrapper = getWrapper().getFilled(null, requests);
        assertTrue(wrapper instanceof BlankRequestWrapper);
    }

    @Test
    public void getFilled_RequestWrapper_RequestsAreNull() {
        wrapper = getWrapper().getFilled(workTime, null);
        assertTrue(wrapper instanceof BlankRequestWrapper);
    }

    @Test
    public void wrapRequests_BookingRequests_RowRequests() {
        wrapper = getWrapper().getFilled(workTime, requests);

        List<BookingRequest> expectedRequests = Artifact.buildBookingRequests();
        List<BookingRequest> actualRequests = wrapper.wrapRequests();
        assertTrue(expectedRequests.size() == actualRequests.size());
        assertTrue(expectedRequests.contains(actualRequests.get(0)));
    }

    protected RequestWrapper getWrapper() {
        return wrapper;
    }
}
