package main.java.vlashchevskyi.exam;

import main.java.vlashchevskyi.exam.tool.Tool;

import java.io.File;

/**
 * {@code Main} class provides functionality to submit booking requests and send them to {@code BookingSystem}.
 * Booking requests are read from file or entered with console. Copy/Paste requests from buffer to console is supported as well.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/3/2015
 */
public class Main {

    private static final String BOOKING_FILE_NAME = "resource\\bookings.txt";

    /**
     * Creates an instance to run the program.
     */
    public Main() {

    }

    /**
     * Provides treats to enter booking requests.
     * Console is used to submit booking requests if file name has not defined.
     *
     * @param args the {@code String[]} defines file name to read booking requests.
     *             The default file name is used if {@code String[]} is empty.
     */
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem(MultiThreadMode.False);

        String fileName = null;
        fileName = (args.length > 0) ? args[0] : BOOKING_FILE_NAME;

        File file = new File(fileName);
        if (file.exists()) {
            system.handleInput(file);
        } else {
            system.handleInput(Tool.getInputWithConsole("Start booking:"));
        }

        system.printBookings();
    }
}
