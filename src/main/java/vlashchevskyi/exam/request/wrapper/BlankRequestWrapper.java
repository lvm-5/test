package main.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.time.WorkTime;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * {@code BlankRequestWrapper} class is a stub for {@code RequestWrapper}.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/14/2015
 */
public class BlankRequestWrapper extends RequestWrapper {
    private static final String ZERO_TIME = "00:00";

    public BlankRequestWrapper() {
        setRowRequests(new LinkedHashSet<>());
        WorkTime time = new WorkTime(ZERO_TIME, ZERO_TIME);
    }

    @Override
    public List<BookingRequest> wrapRequests() {
        return new ArrayList<>();
    }
}
