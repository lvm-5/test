package main.java.vlashchevskyi.exam.request;

/**
 * {@code RowBookingRequest} class represents original row booking requests.
 * This class is immutable and thread-safe.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/13/2015
 */
public final class RowBookingRequest implements Comparable {
    private final String submissionTime;
    private final String bookingTime;

    public RowBookingRequest(String submissionTime, String bookingTime) {
        this.submissionTime = submissionTime;
        this.bookingTime = bookingTime;
    }

    public boolean isEmpty() {
        boolean state = submissionTime == null;

        state = (!state) ? submissionTime.isEmpty() : state;
        state = (!state) ? bookingTime == null : state;
        state = (!state) ? bookingTime.isEmpty() : state;

        return state;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;

        if (o == null || !(o instanceof RowBookingRequest)) {
            return -1;
        }

        int status = -1;

        RowBookingRequest otherRequest = (RowBookingRequest) o;
        status = bookingTime.compareTo(otherRequest.getBookingTime());
        status = (status == 0) ? submissionTime.compareTo(otherRequest.getSubmissionTime()) : status;

        return status;
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        int result = getBookingTime().hashCode();
        result = 31 * result + getSubmissionTime().hashCode();
        return result;
    }
}
