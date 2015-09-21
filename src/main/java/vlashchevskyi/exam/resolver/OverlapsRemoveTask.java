package main.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.request.BookingRequest;

import java.util.List;

/**
 * {@code OverlapsRemoveTask} class creates a task to remove wrapped booking request and is designed for multithreading.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/8/2015
 */
public class OverlapsRemoveTask implements Runnable {

    final static private Resolver resolver = new Resolver();
    private List<BookingRequest> bookingDateGroup = null;

    public OverlapsRemoveTask(List<BookingRequest> bookingDateGroup) {
        this.bookingDateGroup = bookingDateGroup;
    }

    @Override
    public void run() {
        resolver.removeOverlaps(bookingDateGroup);
    }
}
