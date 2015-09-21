package main.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.time.TimeParser;
import main.java.vlashchevskyi.exam.time.WorkTime;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@code RowBookingReader} class reads booking requests as sequence of {@code String} and build row booking requests.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/13/2015
 */
public class RowBookingReader {
    public static final String EMPLOYER_MARKER = "EMP";
    private static final String LOCKER = "locker";
    private static RowBookingReader instance = null;

    private RowBookingReader() {

    }

    public static RowBookingReader getInstance() {
        synchronized (LOCKER) {
            if (instance == null) {
                instance = new RowBookingReader();
            }

            return instance;
        }
    }

    public Set<RowBookingRequest> readRowRequests(Iterator<String> linePointer) {
        Set<RowBookingRequest> requests = new LinkedHashSet<>();

        while (linePointer.hasNext()) {
            RowBookingRequest rowRequest = buildRowRequest(linePointer);
            if (!rowRequest.isEmpty()) {
                requests.add(rowRequest);
            }
        }


        return requests;
    }

    public RowBookingRequest buildRowRequest(Iterator<String> timeIt) {
        String submissionTime = readSubmissionTime(timeIt);
        String bookingTime = readNext(timeIt);

        return new RowBookingRequest(submissionTime, bookingTime);
    }

    private String readSubmissionTime(Iterator<String> originIt) {
        String submissionTime = null;
        do {
            submissionTime = readNext(originIt);
        } while (!submissionTime.contains(EMPLOYER_MARKER) && originIt.hasNext());

        return submissionTime;
    }

    public WorkTime readWorkHours(Iterator<String> it) {
        String time = readNext(it);
        WorkTime workHours = TimeParser.parseWorkHours(time);

        return workHours;
    }

    private String readNext(Iterator<String> it) {
        String data = "";
        while (data.isEmpty() && it.hasNext()) {
            data = it.next().trim();
        }

        return data;
    }
}
