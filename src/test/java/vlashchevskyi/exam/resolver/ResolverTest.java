package test.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.resolver.Resolver;
import main.java.vlashchevskyi.exam.time.BookingTime;
import main.java.vlashchevskyi.exam.tool.Artifact;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.java.vlashchevskyi.exam.tool.Tool.doReflection;
import static org.junit.Assert.assertEquals;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/7/2015
 */
public class ResolverTest {
    private static Resolver resolver = new Resolver();

    @Test
    public void removeOverlaps_FilteredList_UnfilteredList() {
        Map<LocalDate, List<BookingRequest>> schedule = Artifact.buildOriginSchedule();
        Map<LocalDate, List<BookingRequest>> expectedSchedule = Artifact.buildSchedule();

        expectedSchedule.forEach(((localDate, exRequests) -> {
            List requests = schedule.get(localDate);
            resolver.removeOverlaps(requests);
            assertEquals(exRequests.size(), requests.size());
            for (int i = 0; i < exRequests.size(); i++) {
                assertEquals(exRequests.get(i), requests.get(i));
            }
        }));
    }

    @Test
    public void isOverlap_NoOverlapping_Times() {

        // 10:00-11:00 vs 12:00-13:00
        verifyOverlapping("10:00", "12:00", "1", false);

        // 12:00-13:00 vs 10:00-11:00
        verifyOverlapping("12:00", "10:00", "1", false);
    }


    @Test
    public void isOverlap_Overlapping_Times() {

        // 11:30-12:30 vs 12:00-13:00
        verifyOverlapping("11:30", "12:00", "1", true);
    }

    @Test
    public void isOverlap_NoOverlapping_MatchingBookingTimes() {

        // 11:00-12:00 vs 12:00-13:00
        verifyOverlapping("11:00", "12:00", "1", false);

        // 12:00-13:00 vs 12:00-13:00
        verifyOverlapping("12:00", "12:00", "1", true);

        // 13:00-14:00 vs 12:00-13:00
        verifyOverlapping("13:00", "12:00", "1", false);

        // 12:00-13:00 vs 12:00-15:00
        verifyOverlapping(getBookingTime("12:00", "1"), getBookingTime("12:00", "3"), true);
    }

    private void verifyOverlapping(BookingTime start, BookingTime end, Boolean expectation){
        List<BookingTime> times = new ArrayList();

        times.add(start);
        times.add(end);
        Boolean status = doReflection(resolver, "isOverlap", times.toArray());
        assertEquals(expectation, status);
    }

    private void verifyOverlapping(String start, String end, String duration, Boolean expectation){
        List<BookingTime> times = new ArrayList();

        times.add(getBookingTime(start, duration));
        times.add(getBookingTime(end, duration));
        Boolean status = doReflection(resolver, "isOverlap", times.toArray());
        assertEquals(expectation, status);
    }

    private BookingTime getBookingTime(String start, String duration) {
        final String DATE = "2015-09-16";
        BookingTime time = new BookingTime(DATE, start, duration);

        return time;
    }
}
