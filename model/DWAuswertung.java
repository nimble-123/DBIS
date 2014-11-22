package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class DWAuswertung {
    private static final Logger LOG = Logger.getGlobal();

    private final Connection con;
    private PreparedStatement stmnt;
    private ResultSet rs;

    public DWAuswertung(DBConnection dbgetter) {
        con = dbgetter.getConnection();
    }

    public String getQuartal(String jahr, String quartal) {
        String string = null;
        try {
            stmnt = con.prepareStatement("select * " +
                    "from VIEW_AGG_QUARTAL " +
                    "where Jahr = " + jahr +
                    "and Quartal = " + quartal);
            rs = stmnt.executeQuery();

            if(rs.next()) {
                string = Double.toString(rs.getDouble(1)) + " EUR";
            } else {
                string = "0,00 EUR";
            }
            LOG.info("DB Abfrage erfolgreich");

        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Fehler in der Quartalsberechnung.", e);
        }

        return string;
    }

    public String[] getAuswertung(String jahr, String quartal, String einArt, String mitglied) {
        String[] string = new String[5];
        try {
            stmnt = con.prepareStatement("select * " +
                    "from VIEW_ADDITIONAL " +
                    "where Jahr = " + jahr +
                    "and Quartal = " + quartal +
                    "and Einnahmeart = '" + einArt + "'" +
                    "and Mitglied = " + mitglied);
            rs = stmnt.executeQuery();

            if(rs.next()) {
                string[0] = Double.toString(rs.getDouble(1)) + " EUR";
                string[1] = Integer.toString(rs.getInt(2)) + " St.";
            } else {
                string[0] = "0,00 EUR";
                string[1] = "0 St.";
            }
            LOG.info("DB Abfrage erfolgreich");

        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Fehler in der Auswertung.", e);
        }

        return string;
    }
}
