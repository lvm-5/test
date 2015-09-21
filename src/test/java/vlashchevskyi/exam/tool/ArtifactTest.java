package test.java.vlashchevskyi.exam.tool;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;
import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;
import main.java.vlashchevskyi.exam.time.WorkTime;
import main.java.vlashchevskyi.exam.tool.Artifact;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/14/2015
 */
public class ArtifactTest {

    @Test
    public void buildRequestWrapper_Wrapper_NA() {
        RequestWrapper wrapper = Artifact.buildRequestWrapper();
        assertFalse(wrapper.isEmpty());
    }

    @Test
    public void buildBookingRequests_BookingRequests_NA() {
        final int VALID_BOOKINGS_AMOUNT = 10;

        List<BookingRequest> bookingRequests = Artifact.buildBookingRequests();
        assertTrue(bookingRequests.size() >= VALID_BOOKINGS_AMOUNT);

        bookingRequests.forEach(request -> {
            assertFalse(request.isEmpty());
        });
    }

    @Test
    public void buildWorkTime_WorkTime_NA() {
        WorkTime workTime = Artifact.buildWorkTime();
        assertFalse(workTime.isEmpty());
    }

    @Test
    public void buildRowBookingRequests_RowBookingRequests_NA() {
        Set<RowBookingRequest> rowBookingRequests = Artifact.buildRowBookingRequests();

        rowBookingRequests.forEach(request -> {
            assertFalse(request.isEmpty());
        });

    }

    @Test
    public void buildOriginRequests_OriginRequests_NA() {
        List<String> originRequests = Artifact.buildOriginRequests();

        originRequests.forEach(originRequest -> {
            assertFalse(originRequest.isEmpty());
        });
    }

    @Test
    public void buildSchedule_Schedule_NA() {
        Map<LocalDate, List<BookingRequest>> schedule = Artifact.buildSchedule();
        assertTrue(schedule.size() > 1);

        Iterator<LocalDate> dateIt = schedule.keySet().iterator();
        List bookings = schedule.get(dateIt.next());
        assertFalse(bookings.isEmpty());
    }
}
