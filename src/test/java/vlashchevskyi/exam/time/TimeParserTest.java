package test.java.vlashchevskyi.exam.time;

import main.java.vlashchevskyi.exam.time.BookingTime;
import main.java.vlashchevskyi.exam.time.TimeParser;
import main.java.vlashchevskyi.exam.time.WorkTime;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public class TimeParserTest {

    @Test
    public void parseBookingTime_BookingTime_TimeRecord() {
        LocalDate expectedDate = LocalDate.parse("2011-03-21", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime expectedTime = LocalTime.parse("09:00", DateTimeFormatter.ISO_LOCAL_TIME);
        Integer expectedDuration = 2;

        String timeRecord = createRecord(expectedDate, expectedTime, expectedDuration);
        BookingTime bookingTime = TimeParser.parseBookingTime(timeRecord);

        assertEquals(expectedDate, bookingTime.getDate());
        assertEquals(expectedTime, bookingTime.getTime());
        assertEquals(expectedDuration, bookingTime.getDuration());
    }

    private String createRecord(LocalDate date, LocalTime time, Integer duration) {
        StringBuilder timeRecord = new StringBuilder();
        timeRecord.append(date + " ");
        timeRecord.append(time + " ");
        timeRecord.append(duration);

        return timeRecord.toString();
    }

    @Test
    public void parseWorkHours_WorkTime_Hours() {
        String startHour = "09:00";
        String endHour = "17:30";
        String workTimeRecord = (startHour + " " + endHour).replaceAll(":", "");

        LocalTime exStartTime = LocalTime.parse(startHour, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime exEndTime = LocalTime.parse(endHour, DateTimeFormatter.ISO_LOCAL_TIME);

        WorkTime workTime = TimeParser.parseWorkHours(workTimeRecord);
        assertEquals(exStartTime, workTime.getStart());
        assertEquals(exEndTime, workTime.getEnd());
    }
}
