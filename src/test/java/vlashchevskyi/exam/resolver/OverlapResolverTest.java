package test.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.resolver.BasicOverlapResolver;
import main.java.vlashchevskyi.exam.resolver.OverlapResolver;
import main.java.vlashchevskyi.exam.tool.Artifact;
import org.junit.Test;

import java.time.LocalDate;
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
public class OverlapResolverTest {
    private OverlapResolver overlapResolver = new BasicOverlapResolver();

    @Test
    public void removeOverlap_Schedule_UnfilteredSchedule() {
        Map<LocalDate, List<BookingRequest>> schedule = Artifact.buildOriginSchedule();
        Map<LocalDate, List<BookingRequest>> expectedSchedule = Artifact.buildSchedule();

        assertTrue(!schedule.equals(expectedSchedule));
        getOverlapResolver().removeOverlaps(schedule);
        assertEquals(expectedSchedule.size(), schedule.size());
        expectedSchedule.forEach(((localDate, requests) -> {
            assertEquals(requests.size(), schedule.get(localDate).size());
        }));
    }

    protected OverlapResolver getOverlapResolver() {
        return overlapResolver;
    }
}
