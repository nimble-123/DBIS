package model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: model
 */
public class DWImport {
    private static final Logger LOG = Logger.getGlobal();

    private PreparedStatement stmnt;
    private int isOK;
    private ResultSet rs;
    private double betrag;
    private int menge;
    private int artikel_ID;
    private int einnahmeart;
    private int mitglied_ID;
    private int zeit_ID;
    private int csvLineNumber;


    public DWImport(Connection con, List<String[]> list) throws SQLException {
        csvLineNumber = 0;

        for(String[] line : list) {
            csvLineNumber += 1;
            betrag 				= 0;
            menge 				= Integer.parseInt(line[2]);
            artikel_ID 			= Integer.parseInt(line[1]);
            einnahmeart 		= 2;
            mitglied_ID 		= Integer.parseInt(line[0]);
            zeit_ID 			= 0;
            String kaufdatum	= line[3];

            // Überprüfung der geparsten Daten auf vorhandensein im DW
            stmnt = con.prepareStatement("select m.Mitglied_ID " +
                    "from Dim_Mitglied as m " +
                    "where m.Mitglied_ID = " + mitglied_ID);
            rs = stmnt.executeQuery();
            if(!rs.next()) {
                LOG.log(Level.WARNING, "Datensatz in Zeile " + csvLineNumber + " ignoriert!");
                JOptionPane.showMessageDialog(new JFrame(),
                        "Datensatz wird ignoriert, da in Zeile: " + csvLineNumber +
                                "ein im Data Warehouse nicht vorhandenes Mitglied gefunden wurde. OK zum fortfahren",
                        "Datenimport", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Überprüfung der geparsten Daten auf vorhandensein im DW
            stmnt = con.prepareStatement("select a.Artikel_ID " +
                    "from Dim_Artikel as a " +
                    "where a.Artikel_ID = " + artikel_ID);
            rs = stmnt.executeQuery();
            if(!rs.next()) {
                LOG.log(Level.WARNING, "Datensatz in Zeile " + csvLineNumber + " ignoriert!");
                JOptionPane.showMessageDialog(new JFrame(),
                        "Datensatz wird ignoriert, da in Zeile: " + csvLineNumber +
                                "ein im Data Warehouse nicht vorhandener Artikel gefunden wurde. OK zum fortfahren",
                        "Datenimport", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Berechnung des Betrags aus Preis * Menge
            stmnt = con.prepareStatement("select a.Preis " +
                    "from Dim_Artikel as a " +
                    "where a.Artikel_ID = " + artikel_ID);
            rs = stmnt.executeQuery();
            if(rs.next()) {
                betrag = rs.getDouble(1) * menge;
            }

            // Überprüfung des geparsten Kaufdatums.
            String2Date date = new String2Date(kaufdatum);
            try {
                stmnt = con.prepareStatement("select z.Zeit_ID " +
                        "from Dim_Zeit as z " +
                        "where z.Datum = '" + date.getDate() + "'");
            } catch (ParseException e) {
                LOG.log(Level.WARNING, "Datensatz in Zeile " + csvLineNumber + " ignoriert!", e);
                JOptionPane.showMessageDialog(new JFrame(),
                        "Datensatz wird ignoriert, da in Zeile: " + csvLineNumber +
                                "ein falsches Datum gefunden wurde. OK zum fortfahren",
                        "Datenimport", JOptionPane.ERROR_MESSAGE);
            }

            rs = stmnt.executeQuery();
            if(rs.next()) {
                zeit_ID = rs.getInt(1);
            }

            // SQL Statement wird vorbereitet und über die Variablen mit Informationen gefüllt
            stmnt = con.prepareStatement("insert into Fact_Einnahmen values(?, ?, ?, ?, ?, ?)");
            stmnt.setDouble(1, betrag);
            stmnt.setInt(2, menge);
            stmnt.setInt(3, artikel_ID);
            stmnt.setInt(4, einnahmeart);
            stmnt.setInt(5, mitglied_ID);
            stmnt.setInt(6, zeit_ID);

            isOK += stmnt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);

            csvLineNumber = 0;
        }

        // Logeintrag der Transaktion
        if(isOK == 0) {
            LOG.info("Keine Reihen betroffen");
        } else {
            LOG.info(isOK + " Reihe(n) betroffen.");
        }
    }
}
