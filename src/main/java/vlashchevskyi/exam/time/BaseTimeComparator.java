package main.java.vlashchevskyi.exam.time;

import java.util.Comparator;

/**
 * {@code BaseTimeComparator} class is comparator to compare {@code BaseTime} objects
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/6/2015
 */
public class BaseTimeComparator implements Comparator {

    protected Boolean isClassTheSame(Object o1, Object o2) {
        return o1 instanceof BaseTime && o2 instanceof BaseTime;
    }


    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == null || o2 == null || !isClassTheSame(o1, o2)) {
            return (o1 == o2) ? 0 : -1;
        }

        BaseTime current = (BaseTime) o1;
        BaseTime neighbour = (BaseTime) o2;

        int status = current.getDate().compareTo(neighbour.getDate());
        status = (status == 0) ? current.getTime().compareTo(neighbour.getTime()) : status;

        return status;
    }
}
