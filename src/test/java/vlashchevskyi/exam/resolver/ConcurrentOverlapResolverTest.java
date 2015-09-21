package test.java.vlashchevskyi.exam.resolver;

import main.java.vlashchevskyi.exam.resolver.ConcurrentOverlapResolver;
import main.java.vlashchevskyi.exam.resolver.OverlapResolver;

/**
 *
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/15/2015
 */
public class ConcurrentOverlapResolverTest extends OverlapResolverTest {
    private OverlapResolver overlapResolver = new ConcurrentOverlapResolver();

    @Override
    protected OverlapResolver getOverlapResolver() {
        return overlapResolver;
    }
}
