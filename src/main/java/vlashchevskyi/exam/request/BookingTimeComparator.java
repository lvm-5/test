package main.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.time.BookingTime;

/**
 * {@code BookingTimeComparator} class is comparator compares booking requests by booking time.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public class BookingTimeComparator extends RequestComparator {

    @Override
    public int compare(Object o1, Object o2) {

        if (o1 == null || o2 == null || !isClassTheSame(o1, o2)) {
            return (o1 == o2) ? 0 : -1;
        }

        BookingTime current = ((BookingRequest) o1).getBookingTime();
        BookingTime neighbour = ((BookingRequest) o2).getBookingTime();

        return timeComparator.compare(current, neighbour);
    }
}
