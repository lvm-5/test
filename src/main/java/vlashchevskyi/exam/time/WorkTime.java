package main.java.vlashchevskyi.exam.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code WorkTime} class represents office working time.
 * This class is immutable and thread-safe.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0
 */
public final class WorkTime {
    private final LocalTime start;
    private final LocalTime end;

    public WorkTime(String start, String end) {
        this.start = LocalTime.parse(start, DateTimeFormatter.ISO_LOCAL_TIME);
        this.end = LocalTime.parse(end, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public WorkTime(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public boolean isEmpty() {
        return start == null || end == null;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start.toString() + " " + end.toString();
    }

}
