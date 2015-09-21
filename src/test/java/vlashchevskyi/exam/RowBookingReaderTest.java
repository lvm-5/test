package test.java.vlashchevskyi.exam;

import main.java.vlashchevskyi.exam.request.RowBookingReader;
import main.java.vlashchevskyi.exam.request.RowBookingRequest;
import main.java.vlashchevskyi.exam.tool.Artifact;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/13/2015
 */
public class RowBookingReaderTest {
    private static RowBookingReader reader = RowBookingReader.getInstance();
    private static List<String> originRequests = Artifact.buildOriginRequests();

    @Test
    public void RowBookingReader_Singleton_NA() {
        RowBookingReader exReader = RowBookingReader.getInstance();
        assertSame(reader, exReader);
    }

    @Test
    public void readRowRequests_RowRequests_RecordsIterator() {
        Set<RowBookingRequest> rowBookingRequests = reader.readRowRequests(originRequests.iterator());

        assertEquals(originRequests.size() / 2, rowBookingRequests.size());
    }


    @Test
    public void readRowRequests_RowRequests_BadRecordsIterator() {
        final int WORK_TIME_RECORDS_AMOUNT = 1;
        int size = originRequests.size();

        originRequests.remove(1);
        assertTrue(originRequests.size() < size);

        Set<RowBookingRequest> rowBookingRequests = reader.readRowRequests(originRequests.iterator());
        assertEquals((originRequests.size() - WORK_TIME_RECORDS_AMOUNT) / 2, rowBookingRequests.size());
    }
}
