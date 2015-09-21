package main.java.vlashchevskyi.exam.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * {@code Splitter} is interface contains the method to split unsorted booking requests dependent on their date.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public interface Splitter {
    /**
     * Organizes/splits booking requests in date groups.
     *
     * @param requests  the {@code List} of booking requests.
     * @return  the {@code Map} contains booking requests sorted by date.
     */
    Map<LocalDate, List<BookingRequest>> split(List<BookingRequest> requests);
}
