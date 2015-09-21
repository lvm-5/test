package main.java.vlashchevskyi.exam.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * {@code TimeParser} class provides methods to parse booking request date and time including other information.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public class TimeParser {
    private static final String TIME_PATTERN = "(([01]\\d|[2][0-3]):?([0-5]\\d):?([0-5]\\d)?)";
    private static final String DATE_PATTERN = "(\\d{4}-\\d{2}-\\d{2})";

    private TimeParser() {

    }


    public static SubmissionTime parseSubmissionTime(String submissionTime) {
        final String SUBMISSION_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN + " (EMP\\d+)";

        Pattern pattern = Pattern.compile(SUBMISSION_TIME_PATTERN);
        Matcher matcher = pattern.matcher(submissionTime);

        SubmissionTime submission = null;
        if (matcher.find()) {
            String date = matcher.group(1);
            String time = matcher.group(2);
            String employeeID = matcher.group(6);

            submission = new SubmissionTime(date, time, employeeID);
        }

        return submission;
    }

    public static BookingTime parseBookingTime(String bookingTime) {
        final String BOOKING_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN + " (\\d)";

        Pattern bookingPattern = Pattern.compile(BOOKING_TIME_PATTERN);
        Matcher matcher = bookingPattern.matcher(bookingTime);

        BookingTime booking = null;
        if (matcher.find()) {
            String date = matcher.group(1);
            String meetingTime = matcher.group(2);
            String duration = matcher.group(6);

            booking = new BookingTime(date, meetingTime, duration);
        }

        return booking;
    }

    public static WorkTime parseWorkHours(String workHours) {
        final String WORK_HOURS_PATTERN = TIME_PATTERN + " " + TIME_PATTERN;

        Pattern pattern = Pattern.compile(WORK_HOURS_PATTERN);
        Matcher matcher = pattern.matcher(workHours);

        WorkTime time = null;
        if (matcher.find()) {
            String startTime = getTime(matcher, 2);
            String endTime = getTime(matcher, 6);
            time = new WorkTime(startTime, endTime);
        }

        return time;
    }

    private static String getTime(Matcher matcher, Integer groupIndex) {

        return new StringBuffer(matcher.group(groupIndex)).
                append(":").append(matcher.group(++groupIndex)).toString();
    }

}
