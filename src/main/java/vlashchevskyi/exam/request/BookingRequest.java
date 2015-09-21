package main.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.time.BaseTime;

/**
 * {@code BookingRequest} is interface represents wrapped booking request.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/4/2015
 */
public interface BookingRequest extends Comparable {

    /**
     * Gets booking time of booking request.
     *
     * @param <T>   the type based on {@code BaseTime}.
     * @return  the booking time.
     */
    <T extends BaseTime> T getBookingTime();

    /**
     * Gets submission time of booking request.
     *
     * @param <T>   the type based on {@code BaseTime}.
     * @return  the submission time.
     */
    <T extends BaseTime> T getSubmissionTime();

    /**
     * Verifies whether {@code BookingRequest} is empty.
     *
     * @return the {@code boolean} result of verification.
     */
    boolean isEmpty();
}
