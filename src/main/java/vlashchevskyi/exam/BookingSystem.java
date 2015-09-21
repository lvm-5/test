package main.java.vlashchevskyi.exam;

import main.java.vlashchevskyi.exam.concurrency.ConcurrentSwitcher;
import main.java.vlashchevskyi.exam.request.*;
import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;
import main.java.vlashchevskyi.exam.resolver.OverlapResolver;
import main.java.vlashchevskyi.exam.time.BookingTime;
import main.java.vlashchevskyi.exam.time.SubmissionTime;
import main.java.vlashchevskyi.exam.time.WorkTime;
import main.java.vlashchevskyi.exam.tool.FileHelper;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/**
 * {@code BookingSystem} class receives booking requests from console or file, handles and prints them as a schedule.
 *
 * <p>Multiple or one thread mode is provided. {@code BookingSystem} can be switched to different thread modes automatically
 * to provide maximum performance.
 * </p>
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public class BookingSystem {
    private static final ConcurrentSwitcher switcher = ConcurrentSwitcher.getInstance();
    private static Printer printer = null;
    private RequestWrapper wrapper = null;
    private Map<LocalDate, List<BookingRequest>> schedule = null;
    private Boolean auto = null;

    /**
     * Constructs an instance with default settings.
     * Automatic thread mode switching is turned off and multithreading as well.
     */
    public BookingSystem() {
        auto = false;
        init(false);
    }

    /**
     * Constructs an instance and configure it according to provided thread mode.
     *
     * @param mode   the {@code Boolean} sets up thread mode.
     */
    public BookingSystem(MultiThreadMode mode) {

        switch (mode) {
            case True:
                this.auto = false;
                init(true);
                break;
            case False:
                this.auto = false;
                init(false);
                break;
            case Auto:
                this.auto = true;
                init(false);
                break;
        }
    }


    private void init(Boolean concurrency) {
        switcher.setMultithreadingMode(concurrency);
        printer = new Printer();
    }

    /**
     * Receives booking request records and organizes them as a schedule.
     *
     * @param args  the {@code List<String>} contains booking request records.
     */
    public void handleInput(List<String> args) {
        wrapper = readRecords(args.iterator());
        schedule = buildRequestSchedule(wrapper);
    }

    /**
     * Receives booking request records from file and organizes them as a schedule.
     *
     * @param file  the file is a source of booking request records.
     */
    public void handleInput(File file) {
        Stream<String> stream = FileHelper.getBuffReader(file).lines();
        wrapper = readRecords(stream.iterator());
        schedule = buildRequestSchedule(wrapper);
    }

    private RequestWrapper readRecords(Iterator<String> linePointer) {
        final RowBookingReader reader = RowBookingReader.getInstance();

        WorkTime hours = reader.readWorkHours(linePointer);
        Set<RowBookingRequest> rowBookingRequests = reader.readRowRequests(linePointer);
        RequestWrapper wrapper = configSwitcher(rowBookingRequests, auto).getWrapper();

        return wrapper.getFilled(hours, rowBookingRequests);
    }

    private ConcurrentSwitcher configSwitcher(Collection collection, Boolean auto) {

        if (auto) {
            Boolean mode = (collection.size() >= ConcurrentSwitcher.THRESHOLD_OF_CONCURRENCE);
            switcher.setMultithreadingMode(mode);
        }
        return switcher;
    }

    private Map<LocalDate, List<BookingRequest>> buildRequestSchedule(RequestWrapper wrapper) {

        List<BookingRequest> requests = wrapper.wrapRequests();
        Map<LocalDate, List<BookingRequest>> requestSchedule = splitRequestsByDate(requests);
        removeRequestOverlaps(requestSchedule);

        return requestSchedule;
    }

    /**
     * Splits requests by date {@link Splitter#split(List)}.
     *
     * @param baseRequests  the {@code List} of booking requests.
     * @return  the {@code Map} contains booking requests sorted by date.
     */
    protected Map<LocalDate, List<BookingRequest>> splitRequestsByDate(List<BookingRequest> baseRequests) {
        Splitter splitter = new UnsortedSplitter();

        return splitter.split(baseRequests);
    }

    /**
     * Removes requests from collection {@link OverlapResolver#removeOverlaps(Map)}.
     *
     * @param bookingGroups the {@code Map} of booking requests sorted by date.
     */
    protected void removeRequestOverlaps(Map<LocalDate, List<BookingRequest>> bookingGroups) {
        OverlapResolver resolver = switcher.getOverlapResolver();

        resolver.removeOverlaps(bookingGroups);
    }

    /**
     * Prints original list of booking requests and their schedule as result.
     */
    public void printBookings() {
        printer.printBookings();
    }

    private class Printer {


        public void printBookings() {
           // printInput("---INPUT---");
            printOutput("---OUTPUT---");
        }

        private void printInput(String header) {
            System.out.println(header + "\n");

            System.out.println(wrapper.getWorkTime());
            Iterator<RowBookingRequest> it = wrapper.getRowRequests().iterator();
            while (it.hasNext()) {
                RowBookingRequest request = it.next();
                System.out.println(request.getSubmissionTime().toString());
                System.out.println(request.getBookingTime().toString());
            }
        }

        private void printOutput(String header) {
            System.out.println("\n" + header + "\n");

            schedule.forEach((date, group) -> {
                System.out.println(date);
                group.forEach(request -> {
                    BookingTime booking = request.getBookingTime();
                    StringBuilder timeRecord = new StringBuilder();
                    timeRecord.append(booking.getTime());
                    timeRecord.append(" ").append(booking.getTime().plusHours(booking.getDuration()));
                    timeRecord.append(" ").append(((SubmissionTime) request.getSubmissionTime()).getEmployeeID());
                    System.out.println(timeRecord);
                });
            });
        }
    }
}