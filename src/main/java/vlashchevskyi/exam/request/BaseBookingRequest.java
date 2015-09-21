package main.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.time.BookingTime;
import main.java.vlashchevskyi.exam.time.SubmissionTime;
import main.java.vlashchevskyi.exam.time.TimeParser;

/**
 * {@code BaseBookingRequest} class implements methods to wrap, compare booking requests.
 * This class is immutable and thread-safe.
 *
 * @author Valentyn Lashchevskyi
 */
public final class BaseBookingRequest implements BookingRequest {
    private final SubmissionTime submissionTime;
    private final BookingTime bookingTime;

    public BaseBookingRequest(RowBookingRequest request) {
        submissionTime = TimeParser.parseSubmissionTime(request.getSubmissionTime());
        bookingTime = TimeParser.parseBookingTime(request.getBookingTime());
    }

    @Override
    public BookingTime getBookingTime() {
        return bookingTime;
    }

    @Override
    public SubmissionTime getSubmissionTime() {
        return submissionTime;
    }

    @Override
    public boolean isEmpty() {
        return submissionTime == null || bookingTime == null;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;

        if (o == null || !(o instanceof BaseBookingRequest)) {
            return -1;
        }

        BaseBookingRequest otherRequest = (BaseBookingRequest) o;
        int status = bookingTime.compareTo(otherRequest.getBookingTime());
        status = (status == 0) ? submissionTime.compareTo(otherRequest.getSubmissionTime()) : status;
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        int result = bookingTime.hashCode();
        result = 31 * result + submissionTime.hashCode();
        return result;
    }
}
