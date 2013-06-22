import model.MyLogger;
import view.Auswahl;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: PACKAGE_NAME
 */
public class Main {
    private final static Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Probleme beim erstellen der Log Datei!");
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Auswahl();
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, "Hauptfenster öffnet nicht!", e);
                }
            }
        });
    }
}
