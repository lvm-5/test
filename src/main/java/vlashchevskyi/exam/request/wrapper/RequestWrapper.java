package main.java.vlashchevskyi.exam.request.wrapper;

import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;
import main.java.vlashchevskyi.exam.time.BookingTime;
import main.java.vlashchevskyi.exam.time.WorkTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * {@code RequestWrapper} is abstract class that provides methods to wrap booking requests and to validate them.
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/9/2015
 */
public abstract class RequestWrapper {
    private WorkTime workTime = null;
    private Set<RowBookingRequest> rowRequests = null;

    /**
     * Wraps row booking request to {@code BookingRequest} and saves it to {@code List} collection.
     *
     * @return the {@code List} of wrapped booking requests.
     */
    public abstract List<BookingRequest> wrapRequests();

    /**
     * Validates wrapped booking request against working time.
     *
     * @param request   the {@code BookingRequest} represents wrapped booking request.
     * @return  the {@code boolean} as {@code true} if booking time is within working time.
     */
    public boolean isWorkTimeAcceptable(BookingRequest request) {
        boolean acceptable = false;

        BookingTime booking = request.getBookingTime();
        if (booking.getTime().compareTo(workTime.getStart()) >= 0) {
            LocalTime endMeetingTime = booking.getTime().plusHours(booking.getDuration());
            if (endMeetingTime.compareTo(workTime.getEnd()) <= 0) {
                acceptable = true;
            }
        }

        return acceptable;
    }

    /**
     * Initiates {@code RequestWrapper} with work time and row booking requests.
     *
     * @param workHours the {@code WorkTime} is work hours to be used for validation.
     * @param rowRequests   the {@code RowBookingRequest} row booking requests are going to be wrapped.
     * @return  the current wrapper or empty {@code BlankRequestWrapper} if initialization is failed.
     */
    public RequestWrapper getFilled(WorkTime workHours, Set<RowBookingRequest> rowRequests) {
        this.workTime = workHours;
        this.rowRequests = rowRequests;

        return (!isEmpty()) ? this : new BlankRequestWrapper();
    }

    /**
     * Validate wrapper for empty state.
     *
     * @return  the {@code boolean} is state of validation.
     */
    public boolean isEmpty() {
        return workTime == null || rowRequests == null;
    }


    /**
     * Gets row requests that contains only unique row booking requests.
     *
     * @return the {@code Set} of row booking requests.
     */
    public Set<RowBookingRequest> getRowRequests() {
        return rowRequests;
    }

    /**
     * Sets new values of row booking requests are going to be wrapped.
     *
     * @param rowRequests   the {@code Set} of row booking requests.
     */
    public void setRowRequests(Set<RowBookingRequest> rowRequests) {
        this.rowRequests = rowRequests;
    }

    /**
     * Gets current work time.
     *
     * @return the work time.
     */
    public WorkTime getWorkTime() {
        return workTime;
    }

    /**
     * Sets work time to be used for validation of booking requests at wrapping.
     *
     * @param workTime  the work time to initiate {@code RequestWrapper}.
     */
    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

}
