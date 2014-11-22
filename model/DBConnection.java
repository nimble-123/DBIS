package model;

import com.inet.tds.TdsDataSource;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class DBConnection {
    private static final Logger LOG = Logger.getGlobal();

    private final String serverName;
    private final String port;
    private final String database;
    private final String user;
    private final String password;
    private Connection con;

    public DBConnection(String serverName, String port, String database,
                        String user, String password) {

        this.serverName = serverName;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    /**
     * Gibt die Connection zur Datenbank zurueck
     */
    public synchronized Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                DataSource ds = new TdsDataSource();
                ((TdsDataSource) ds).setServerName(serverName);
                ((TdsDataSource) ds).setPortNumber(Integer.parseInt(port));
                con = ds.getConnection(user, password);
                con.setCatalog(database);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "DB Verbindung fehlgeschlagen.", e);
            JOptionPane.showMessageDialog(new JFrame(),
                    "Keine Verbindung zur Datenbank!\n" +
                            "Bitte üerprüfen Sie Ihre Angaben!",
                    "Datenimport", JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }

    /**
     * Gibt eine Connection aus dem Pool wieder frei.
     */
    public synchronized void freeConnection() {
        try {
            System.out.println("Active DB-Verbindung wird geschlossen...");
            con.close();
            con = null;
        } catch (Exception ignored) {
        }
    }
}
