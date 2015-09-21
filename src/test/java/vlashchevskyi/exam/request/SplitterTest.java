package test.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.Splitter;
import main.java.vlashchevskyi.exam.request.UnsortedSplitter;
import main.java.vlashchevskyi.exam.tool.Artifact;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/14/2015
 */
public class SplitterTest {
    private static final Splitter splitter = new UnsortedSplitter();

    @Test
    public void split_Schedule_Bookings() {
        List<BookingRequest> bookingRequests = Artifact.buildRequestWrapper().wrapRequests();
        Map<LocalDate, List<BookingRequest>> schedule = getSplitter().split(bookingRequests);
        Iterator<LocalDate> dateIt = schedule.keySet().iterator();

        Map<LocalDate, List<BookingRequest>> expectedSchedule = Artifact.buildOriginSchedule();
        expectedSchedule.forEach((exDate, exGroup) -> {
            assertEquals(exDate, dateIt.next());
            List actualGroup = schedule.get(exDate);
            exGroup.forEach(request -> assertTrue(actualGroup.contains(request)));
        });
    }

    protected Splitter getSplitter() {
        return splitter;
    }
}
