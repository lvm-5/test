package main.java.vlashchevskyi.exam.time;

/**
 * {@code SubmissionTime} class represents submission time record for booking request.
 * This class is immutable and thread-safe.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public final class SubmissionTime extends BaseTime {
    private final String employeeID;

    public SubmissionTime(String date, String time, String employeeID) {
        super(date, time);
        this.employeeID = employeeID;
    }

    public String getEmployeeID() {
        return employeeID;
    }


    @Override
    public String toString() {
        return getDate().toString() + " " + getTime().toString() + " " + employeeID;
    }


}
