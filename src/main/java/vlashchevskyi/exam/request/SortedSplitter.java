package main.java.vlashchevskyi.exam.request;

import java.time.LocalDate;
import java.util.*;

/**
 * {@code SortedSplitter} class implements the method to sort and split booking requests.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public class SortedSplitter implements Splitter {

    @Override
    public Map<LocalDate, List<BookingRequest>> split(List<BookingRequest> requests) {
        Map<LocalDate, List<BookingRequest>> bookingDateGroup = new LinkedHashMap<>();

        LocalDate timeGroupDate = null;
        List<BookingRequest> timeGroup = null;
        Collections.sort(requests, new BookingTimeComparator());
        Iterator<BookingRequest> it = requests.iterator();
        while (it.hasNext()) {
            BookingRequest request = it.next();
            LocalDate date = request.getBookingTime().getDate();
            if (timeGroup == null || date.compareTo(timeGroupDate) != 0) {
                timeGroup = new ArrayList<>();
                bookingDateGroup.put(date, timeGroup);
            }
            timeGroup.add(request);
            timeGroupDate = date;
        }

        return bookingDateGroup;
    }
}
