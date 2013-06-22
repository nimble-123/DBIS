package model;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class MyLogger {
    private static Handler fileTXT;
    private static Handler fileHTML;

    public static void setup() throws SecurityException, IOException {
        Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        fileTXT = new FileHandler(System.getProperty("user.home") +
                System.getProperty("file.separator") + "dw-log.txt",
                50000, 1, true);
        fileTXT.setLevel(Level.ALL);
        fileHTML = new FileHandler(System.getProperty("user.home") +
                System.getProperty("file.separator") + "dw-log.html",
                50000, 1, true);
        fileHTML.setLevel(Level.ALL);

        fileTXT.setFormatter(new SimpleFormatter());
        fileHTML.setFormatter(new HtmlFormatter());

        log.addHandler(fileTXT);
        log.addHandler(fileHTML);
    }
}
