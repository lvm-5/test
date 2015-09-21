package main.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.request.BookingRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * {@code BasicOverlapResolver} class removes requests from {@code Map} if they are crossed with others by booking time.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/10/2015
 * @see Resolver
 */
public class BasicOverlapResolver implements OverlapResolver {
    Resolver resolver = new Resolver();

    @Override
    public void removeOverlaps(Map<LocalDate, List<BookingRequest>> bookingGroups) {
        bookingGroups.forEach((date, bookingDateGroup) -> {
            resolver.removeOverlaps(bookingDateGroup);
        });

    }
}
