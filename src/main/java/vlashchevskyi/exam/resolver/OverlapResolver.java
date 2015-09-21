package main.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.request.BookingRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * {@code OverlapResolver} is interface contains the method to remove booking request from collection if its time is crossed with others.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/10/2015
 * @see Resolver
 */
public interface OverlapResolver {

    /**
     * Removes booking request with time overlaps from {@code bookingGroups}.
     *
     * @param bookingGroups the {@code Map} contains booking requests grouped by date.
     */
    void removeOverlaps(Map<LocalDate, List<BookingRequest>> bookingGroups);
}
