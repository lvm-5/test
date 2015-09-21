package main.java.vlashchevskyi.exam.request;

/**
 * {@code SubmissionTimeComparator} class is comparator compares booking requests by submission time.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public class SubmissionTimeComparator extends RequestComparator {

    @Override
    public int compare(Object o1, Object o2) {

        if (o1 == null || o2 == null || !isClassTheSame(o1, o2)) {
            return (o1 == o2) ? 0 : -1;
        }

        BookingRequest current = (BookingRequest) o1;
        BookingRequest neighbour = (BookingRequest) o2;
        int status = timeComparator.compare(current.getSubmissionTime(), neighbour.getSubmissionTime());
        status = (status == 0) ? timeComparator.compare(current.getBookingTime(), neighbour.getBookingTime()) : status;

        return status;
    }

}
