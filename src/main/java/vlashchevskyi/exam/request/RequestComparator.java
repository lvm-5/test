package main.java.vlashchevskyi.exam.request;

import main.java.vlashchevskyi.exam.time.BaseTimeComparator;

import java.util.Comparator;

/**
 * {@code RequestComparator} is abstract class to compare booking requests.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public abstract class RequestComparator implements Comparator {
    protected static BaseTimeComparator timeComparator = new BaseTimeComparator();

    @Override
    public abstract int compare(Object o1, Object o2);

    protected Boolean isClassTheSame(Object o1, Object o2) {
        return o1 instanceof BookingRequest && o2 instanceof BookingRequest;
    }
}
