package main.java.vlashchevskyi.exam.tool;

import main.java.vlashchevskyi.exam.request.BaseBookingRequest;
import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.RowBookingReader;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;
import main.java.vlashchevskyi.exam.request.wrapper.BasicRequestWrapper;
import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;
import main.java.vlashchevskyi.exam.time.BookingTime;
import main.java.vlashchevskyi.exam.time.SubmissionTime;
import main.java.vlashchevskyi.exam.time.WorkTime;

import java.time.LocalDate;
import java.util.*;

/**
 * {@code Artifact} is static class provides service methods to create different objects for jUnit testing.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/13/2015
 */
public class Artifact {
    public static final String FILE_NAME = "resource\\jUnit.bookings.txt";
    public static final RowBookingReader reader = RowBookingReader.getInstance();
    public static final String WORK_TIME = "09:00 17:30";


    private Artifact() {

    }

    public static List<String> buildOriginRequests() {
        List<String> originRequests = new ArrayList<>();
        originRequests.add(WORK_TIME.replaceAll(":", ""));
        originRequests.add("2011-03-17 10:17:06 EMP0011");
        originRequests.add("2011-03-21 09:00 2");
        originRequests.add("2011-03-17 12:34:56 EMP0021");
        originRequests.add("2011-03-21 09:00 2");
        originRequests.add("2011-03-17 09:17:05 EMP0031");
        originRequests.add("2011-03-21 10:00 2");
        originRequests.add("2011-03-17 09:17:06 EMP0041");
        originRequests.add("2011-03-21 10:00 1");
        originRequests.add("2011-03-17 09:17:05 EMP004");
        originRequests.add("2011-03-21 09:30 1");
        originRequests.add("2011-02-17 09:17:05 EMP003");
        originRequests.add("2011-02-21 12:30 5");
        originRequests.add("2011-02-17 05:21:05 EMP002");
        originRequests.add("2011-02-21 11:30 1");
        originRequests.add("2011-02-17 05:21:05 EMP001");
        originRequests.add("2011-02-21 09:30 2");
        originRequests.add("2012-12-17 09:17:05 EMP0091");
        originRequests.add("2012-12-21 08:30 2");
        originRequests.add("2012-12-17 05:21:05 EMP0010");
        originRequests.add("2012-12-21 16:30 2");
        originRequests.add("2012-12-17 05:21:05 EMP006");
        originRequests.add("2012-12-21 11:00 2");
        originRequests.add("2012-12-17 05:21:05 EMP005");
        originRequests.add("2012-12-21 09:00 2");

        return originRequests;
    }

    public static Map buildSchedule() {
        Map<LocalDate, List<BookingRequest>> schedule = new TreeMap<>();
        Map<String, BookingRequest> requestsByIDs = getRequestsByIDs(Artifact.buildBookingRequests());

        int j = 0;
        int current = 1;
        do {
            List<BookingRequest> group = new ArrayList<>();
            for (int i = current; i < 7; i++) {
                group.add(requestsByIDs.get("EMP00" + i));
                if (i == 3 || i == 4 || i == 6) {
                    current = ++i;
                    break;
                }
            }
            schedule.put(group.get(0).getBookingTime().getDate(), group);
            j++;
        } while (j < 3);

        return schedule;
    }

    public static String buildPrintedSchedule() {
        final String NEW_LINE = "\r\n";
        Map<LocalDate, List<BookingRequest>> schedule = buildSchedule();

        StringBuilder output = new StringBuilder();

        schedule.forEach((date, dateGroup) -> {
            output.append(date);
            output.append(NEW_LINE);
            dateGroup.forEach(request -> {
                BookingTime booking = request.getBookingTime();
                StringBuilder timeRecord = new StringBuilder();
                timeRecord.append(booking.getTime());
                timeRecord.append(" ").append(booking.getTime().plusHours(booking.getDuration()));
                timeRecord.append(" ").append(((SubmissionTime) request.getSubmissionTime()).getEmployeeID());
                output.append(timeRecord);
                output.append(NEW_LINE);
            });
        });

        return output.toString();
    }

    public static Map<LocalDate, List<BookingRequest>> buildOriginSchedule() {
        Map<LocalDate, List<BookingRequest>> schedule = new TreeMap<>();

        buildBookingRequests().forEach(booking -> {
            LocalDate date = booking.getBookingTime().getDate();
            List group = schedule.get(date);
            if (group == null) {
                group = new ArrayList();
                schedule.put(date, group);
            }
            group.add(booking);
        });
        return schedule;
    }

    public static Set<RowBookingRequest> buildRowBookingRequests() {
        List<String> originRequests = Artifact.buildOriginRequests();
        return reader.readRowRequests(originRequests.iterator());
    }

    public static WorkTime buildWorkTime() {
        final String startTime = WORK_TIME.substring(0, 5);
        final String endTime = WORK_TIME.substring(6);

        WorkTime workTime = new WorkTime(startTime, endTime);

        return workTime;
    }

    public static List<BookingRequest> buildBookingRequests() {

        List<BookingRequest> bookingRequests = new ArrayList<>();
        Set<RowBookingRequest> rowBookingRequests = Artifact.buildRowBookingRequests();
        RequestWrapper wrapper = new BasicRequestWrapper().getFilled(Artifact.buildWorkTime(), rowBookingRequests);
        rowBookingRequests.forEach(rowRequest -> {
            BookingRequest request = new BaseBookingRequest(rowRequest);
            if (!request.isEmpty() && wrapper.isWorkTimeAcceptable(request)) {
                bookingRequests.add(request);
            }
        });

        return bookingRequests;
    }

    public static RequestWrapper buildRequestWrapper() {
        RequestWrapper wrapper = new BasicRequestWrapper();
        wrapper.setRowRequests(Artifact.buildRowBookingRequests());
        wrapper.setWorkTime(Artifact.buildWorkTime());

        return wrapper;
    }

    private static Map<String, BookingRequest> getRequestsByIDs(List<BookingRequest> requests) {
        Map<String, BookingRequest> employers = new HashMap<>();
        requests.forEach(request -> {
            SubmissionTime time = request.getSubmissionTime();
            employers.put(time.getEmployeeID(), request);
        });

        return employers;
    }
}
