package test.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.request.SortedSplitter;
import main.java.vlashchevskyi.exam.request.Splitter;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/14/2015
 */
public class SortedSplitterTest extends SplitterTest {
    private static final Splitter splitter = new SortedSplitter();

    @Override
    protected Splitter getSplitter() {
        return splitter;
    }
}
