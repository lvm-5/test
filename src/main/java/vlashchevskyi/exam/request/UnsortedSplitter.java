package main.java.vlashchevskyi.exam.request;

import java.time.LocalDate;
import java.util.*;

/**
 * {@code UnsortedSplitter} class implements the method to split unsorted booking requests.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public class UnsortedSplitter implements Splitter {

    @Override
    public Map<LocalDate, List<BookingRequest>> split(List<BookingRequest> requests) {
        Map<LocalDate, List<BookingRequest>> bookingDateGroup = new TreeMap<>();

        List<BookingRequest> timeGroup = null;
        Iterator<BookingRequest> it = requests.iterator();
        while (it.hasNext()) {
            BookingRequest request = it.next();
            LocalDate date = request.getBookingTime().getDate();
            timeGroup = bookingDateGroup.get(date);
            if (timeGroup == null) {
                timeGroup = new ArrayList<>();
                bookingDateGroup.put(date, timeGroup);
            }
            timeGroup.add(request);
        }

        return bookingDateGroup;
    }
}
