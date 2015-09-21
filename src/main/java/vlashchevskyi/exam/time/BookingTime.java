package main.java.vlashchevskyi.exam.time;

/**
 * {@code BookingTime} class represents booking time record for booking request.
 * This class is immutable and thread-safe.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public final class BookingTime extends BaseTime {
    final private Integer duration;

    public BookingTime(String date, String time, String duration) {
        super(date, time);
        this.duration = Integer.valueOf(duration);
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getDate().toString() + " " + getTime().toString() + " " + duration;
    }
}
