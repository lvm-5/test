package main.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.BookingTimeComparator;
import main.java.vlashchevskyi.exam.request.SubmissionTimeComparator;
import main.java.vlashchevskyi.exam.time.BookingTime;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * {@code Resolver} class provides the method to remove booking records from {@code List}.
 * All records have overlaps by booking time is removed from collection.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/10/2015
 */
public class Resolver {
    /**
     * Remove booking requests that cause conflicts with others by booking time.
     *
     * @param bookingDateGroup the {@code List} contains booking requests are going to be verified by booking time
     *                         against other booking requests to find overlaps.
     */
    public void removeOverlaps(List<BookingRequest> bookingDateGroup) {
        Map<BookingRequest, Boolean> submissionDateGroup = new TreeMap<>(new SubmissionTimeComparator());
        bookingDateGroup.forEach(request -> submissionDateGroup.put(request, true));
        Collections.sort(bookingDateGroup, new BookingTimeComparator());

        submissionDateGroup.forEach((submissionRequest, access) -> {
            if (access) {
                IteratorBreaker breaker = new IteratorBreaker();
                BookingRequest request = submissionRequest;
                for (int i = 0; i < bookingDateGroup.size(); ) {
                    BookingRequest neighbourRequest = bookingDateGroup.get(i);
                    if (neighbourRequest == request) {
                        breaker.allow();
                        i++;
                        continue;
                    }

                    boolean overlap = isOverlap(request.getBookingTime(), neighbourRequest.getBookingTime());
                    if (overlap) {
                        bookingDateGroup.remove(i);
                        submissionDateGroup.put(neighbourRequest, false);
                        breaker.activate();
                    } else {
                        if (breaker.isItBreakable()) {
                            break;
                        }
                        i++;
                    }
                }
            }
        });
    }


    private Boolean isOverlap(BookingTime current, BookingTime neighbour) {
        LocalTime startTime = current.getTime();
        LocalTime endTime = startTime.plusHours(current.getDuration());
        LocalTime neighbourStartTime = neighbour.getTime();
        LocalTime neighbourEndTime = neighbourStartTime.plusHours(neighbour.getDuration());

        Boolean status = false;
        if (startTime.compareTo(neighbourStartTime) > 0) {
            status = startTime.compareTo(neighbourEndTime) >= 0;
        } else if (startTime.compareTo(neighbourStartTime) < 0) {
            status = endTime.compareTo(neighbourStartTime) <= 0;
        }

        return !status;
    }

    private static class IteratorBreaker {
        private BreakStatus breakStatus = BreakStatus.Inactive;

        public void allow() {
            breakStatus = (breakStatus.equals(BreakStatus.PreActive)) ? BreakStatus.Active : BreakStatus.Allowed;
        }

        public void activate() {
            breakStatus = (breakStatus.equals(BreakStatus.Allowed)) ?
                    BreakStatus.Active : BreakStatus.PreActive;
        }

        public Boolean isItBreakable() {
            return breakStatus.equals(BreakStatus.Active);
        }

        public BreakStatus getBreakStatus() {
            return breakStatus;
        }

        public void setBreakStatus(BreakStatus breakStatus) {
            this.breakStatus = breakStatus;
        }

        enum BreakStatus {Allowed, Active, PreActive, Inactive}
    }
}
