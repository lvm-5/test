package main.java.vlashchevskyi.exam.tool;

import java.io.*;

/**
 * {@code FileHelper} is static class provides service methods to read, write file.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/4/2015
 */
public class FileHelper {

    private FileHelper() {

    }

    public static BufferedReader getBuffReader(File file) {
        FileReader reader = getReader(file);

        return new BufferedReader(reader);
    }

    private static FileReader getReader(File file) {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return reader;
    }

    public static BufferedWriter getBuffWriter(File file) {
        FileWriter writer = getWriter(file);

        return new BufferedWriter(writer);
    }

    private static FileWriter getWriter(File file) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer;
    }
}
