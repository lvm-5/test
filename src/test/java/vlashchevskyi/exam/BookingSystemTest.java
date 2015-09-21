package test.java.vlashchevskyi.exam;

import main.java.vlashchevskyi.exam.BookingSystem;
import main.java.vlashchevskyi.exam.MultiThreadMode;
import main.java.vlashchevskyi.exam.concurrency.ConcurrentSwitcher;
import main.java.vlashchevskyi.exam.request.BookingRequest;
import main.java.vlashchevskyi.exam.request.wrapper.RequestWrapper;
import main.java.vlashchevskyi.exam.resolver.OverlapResolver;
import main.java.vlashchevskyi.exam.tool.Artifact;
import main.java.vlashchevskyi.exam.tool.Tool;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static main.java.vlashchevskyi.exam.tool.Tool.doFieldReflection;
import static main.java.vlashchevskyi.exam.tool.Tool.doReflection;
import static org.junit.Assert.*;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/14/2015
 */
public class BookingSystemTest {
    private BookingSystem bookingSystem = new BookingSystem();

    @Test
    public void BookingSystem_Instance_NA() {
       verifyInstance(null);
    }

    @Test
    public void BookingSystem_Instance_MultiThreadingIsON() {
        verifyInstance(MultiThreadMode.True);
    }

    @Test
    public void BookingSystem_Instance_MultiThreadingIsOff() {
        verifyInstance(MultiThreadMode.False);
    }

    @Test
    public void BookingSystem_Instance_MultiThreadingIsNA() {
        verifyInstance(MultiThreadMode.Auto);
    }

    private void verifyInstance(MultiThreadMode mode) {
        BookingSystem exBookingSystem = null;
        Boolean exThreadMode = null;
        Boolean exAutoMode = null;

        if (mode == null) {
            exBookingSystem = new BookingSystem();
            exThreadMode = false;
            exAutoMode = false;
        } else {
            exBookingSystem = new BookingSystem(mode);
            exAutoMode = mode.equals(MultiThreadMode.Auto);
            exThreadMode = mode.equals(MultiThreadMode.True);
        }

        Boolean autoMode = doFieldReflection(exBookingSystem, "auto");
        assertEquals(exAutoMode, autoMode);

        Object printer = doFieldReflection(exBookingSystem, "printer");
        assertNotNull(printer);

        ConcurrentSwitcher switcher = doFieldReflection(exBookingSystem, "switcher");
        assertNotNull(switcher);
        assertEquals(exThreadMode, switcher.getMultithreadingMode());
    }

    @Test
    public void handleInput_Schedule_File(){
        File bookings = new File(Artifact.FILE_NAME);
        assertTrue(bookings.exists());

        bookingSystem.handleInput(bookings);
        verifySchedule();
    }

    @Test
    public void handleInput_Schedule_Console(){
        bookingSystem.handleInput(Artifact.buildOriginRequests());
        verifySchedule();
    }

    private void verifySchedule() {
        Map schedule = doFieldReflection(bookingSystem, "schedule");
        assertFalse(schedule.isEmpty());
    }

    @Test
    public void printBookingState_Schedule_PrintedSchedule() {
        bookingSystem.handleInput(new File(Artifact.FILE_NAME));
        RequestWrapper wrapper = doFieldReflection(bookingSystem, "wrapper");
        assertEquals(Artifact.buildRowBookingRequests(), wrapper.getRowRequests());

        String output = Tool.print(() -> bookingSystem.printBookings()).toString();
        String expectedOutput = Artifact.buildPrintedSchedule();
        assertTrue(output.contains(expectedOutput));
    }

    @Test
    public void configSwitcher_ConfiguredSwitcher_CollectionAndMode(){
        List collection = new ArrayList<>();
        for (int i = 0; i < ConcurrentSwitcher.THRESHOLD_OF_CONCURRENCE ; i++) {
            collection.add(null);
        };

        ConcurrentSwitcher switcher = null;

        switcher = doReflection(bookingSystem, "configSwitcher", new Object[]{new LinkedHashSet<>(), false});
        assertFalse(switcher.getMultithreadingMode());
        RequestWrapper wrapper = switcher.getWrapper();
        OverlapResolver resolver = switcher.getOverlapResolver();

        switcher = doReflection(bookingSystem, "configSwitcher", new Object[]{collection, true});
        assertTrue(switcher.getMultithreadingMode());
        RequestWrapper concurrentWrapper = switcher.getWrapper();
        OverlapResolver concurrentResolver = switcher.getOverlapResolver();

        assertNotEquals(concurrentResolver.getClass(), resolver.getClass());
        assertNotEquals(concurrentWrapper.getClass(), wrapper.getClass());
    }

    @Test
    public void readRecords_Wrapper_RecordsIterator() {
        List<String> records = Artifact.buildOriginRequests();

        RequestWrapper wrapper = doReflection(bookingSystem, "readRecords", new Object[]{records.iterator()});
        assertFalse(wrapper.isEmpty());
    }

    @Test
    public void buildRequestSchedule_Schedule_Wrapper() {
        RequestWrapper wrapper = Artifact.buildRequestWrapper();

        Map<LocalDate, List<BookingRequest>> schedule = null;
        schedule = doReflection(bookingSystem, "buildRequestSchedule", new Object[]{wrapper});
        assertFalse(schedule.isEmpty());
    }

}
