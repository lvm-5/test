package main.java.vlashchevskyi.exam.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code BaseTime} is abstract class to represent booking request times.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public abstract class BaseTime implements Comparable {

    private final LocalDate date;
    private final LocalTime time;

    protected BaseTime(String date, String time) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        this.time = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public int compareTo(Object o1) {
        if (o1 == null || !(o1 instanceof BaseTime)) {
            return -1;
        }

        BaseTime neighbour = (BaseTime) o1;

        int status = getDate().compareTo(neighbour.getDate());
        status = (status == 0) ? getTime().compareTo(neighbour.getTime()) : status;

        return status;
    }

    @Override
    public String toString() {
        return date.toString() + " " + time.toString();
    }

    public LocalDate getDate() {
        return date;
    }


    public LocalTime getTime() {
        return time;
    }

    public boolean isEmpty() {
        return date == null || time == null;
    }

}
