package view;

import model.CSVParser;
import model.DBConnection;
import model.DWAuswertung;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: view
 */
public class Auswertung implements ActionListener {

    private static final Logger LOG = Logger.getGlobal();

    private static final String SERVERNAME = "whv-fbmit3.hs-woe.de";
    private static final String PORT = "1433";
    private static final String DB = "DB6002109";
    private static final String USER = "DB6002109";
    private static final String PWD = "marlboro111";

    // Mainframe + Menübar
    public JFrame frmAuswertung;
    private JMenuBar menuBar;
    private JMenu mnDatei;
    private JMenuItem mnItmLog;
    private JMenu mnExport;
    private JMenuItem mnItmQExp;
    private JMenuItem mnItmAExp;
    private JMenuItem mnItmQuit;
    private JMenu mnHelp;
    private JMenuItem mnItmAbout;

    // Aufgabe - Aggregattabellen auf Quartalsbasis
    private JPanel panelQAusw;
    private JLabel lblJahr;
    private JLabel lblQuartal;
    private JLabel lblUmsatz;
    private JLabel lblErgebnis;
    private Vector<String> jahre;
    private ComboBoxModel<String> cBoxJahrModel;
    private Vector<String> quartal;
    private ComboBoxModel<String> cBoxQuartalModel;
    private JComboBox<String> cBoxJahr;
    private JComboBox<String> cBoxQuartal;
    private JButton btnAusw;

    // Aufgabe - beliebige weitere sinnvolle Auswertungen
    private JPanel panelAusw;
    private JButton btnAusw2;
    private JLabel lblJahr_1;
    private JLabel lblQuartal_1;
    private JLabel lblUmsatz_1;
    private JLabel lblErgebnis_2;
    private JLabel lblEinArt;
    private JLabel lblMitglied;
    private JLabel lblMenge;
    private JLabel lblMengeErg;
    private JComboBox<String> cBoxJahr2;
    private JComboBox<String> cBoxQuartal2;
    private Vector<String> einnahmeArt;
    private ComboBoxModel<String> cBoxEinArtModel;
    private Vector<String> mitglied;
    private ComboBoxModel<String> cBoxMitgliedModel;
    private JComboBox<String> cBoxEinArt;
    private JComboBox<String> cBoxMitglied;

    // Datenbankverbindung
    private DBConnection dbgetter;
    private DWAuswertung dwAusw;

    // Feature - Export der Ergebnisse im *.csv Format
    private String pfad;
    private JFileChooser chooser;
    private FileNameExtensionFilter plainFilter;
    private File file;
    private String[] entries;


    /**
     * Erzeugen der Anwendung.
     */
    public Auswertung() {
        initialize();
    }

    /**
     * Initialisation der grafischen Oberfläche.
     */
    private void initialize() {
        frmAuswertung = new JFrame();
        frmAuswertung.setTitle("Auswertung");
        frmAuswertung.setBounds(100, 100, 671, 235);
        frmAuswertung.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmAuswertung.setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        frmAuswertung.setJMenuBar(menuBar);

        mnDatei = new JMenu("Datei");
        menuBar.add(mnDatei);

        mnItmLog = new JMenuItem("Protokoll öffnen");
        mnItmLog.addActionListener(this);
        mnDatei.add(mnItmLog);

        mnExport = new JMenu("Exportieren");
        mnDatei.add(mnExport);

        mnItmQExp = new JMenuItem("Quartalsauswertung");
        mnItmQExp.addActionListener(this);
        mnExport.add(mnItmQExp);

        mnItmAExp = new JMenuItem("Auswertung");
        mnItmAExp.addActionListener(this);
        mnExport.add(mnItmAExp);

        mnItmQuit = new JMenuItem("Schlie\u00dfen");
        mnItmQuit.addActionListener(this);
        mnDatei.add(mnItmQuit);

        mnHelp = new JMenu("?");
        menuBar.add(mnHelp);

        mnItmAbout = new JMenuItem("About");
        mnItmAbout.addActionListener(this);
        mnHelp.add(mnItmAbout);
        frmAuswertung.getContentPane().setLayout(null);

        panelQAusw = new JPanel();
        panelQAusw.setBorder(new TitledBorder(new LineBorder(null, 1, true),
                "Quartalsauswertung", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        panelQAusw.setBounds(5, 5, 200, 175);
        frmAuswertung.getContentPane().add(panelQAusw);
        panelQAusw.setLayout(null);

        lblJahr = new JLabel("Jahr:");
        lblJahr.setBounds(12, 25, 70, 15);
        panelQAusw.add(lblJahr);

        lblQuartal = new JLabel("Quartal:");
        lblQuartal.setBounds(12, 65, 70, 15);
        panelQAusw.add(lblQuartal);

        lblUmsatz = new JLabel("Umsatz:");
        lblUmsatz.setBounds(12, 109, 70, 15);
        panelQAusw.add(lblUmsatz);

        lblErgebnis = new JLabel("0,00 EUR");
        lblErgebnis.setHorizontalAlignment(SwingConstants.RIGHT);
        lblErgebnis.setBounds(94, 109, 94, 15);
        panelQAusw.add(lblErgebnis);

        cBoxJahr = new JComboBox<>();
        cBoxJahr.setBounds(123, 20, 65, 24);
        jahre = new Vector<>();
        jahre.add("2012");
        jahre.add("2013");
        jahre.add("2014");
        cBoxJahrModel = new DefaultComboBoxModel<>(jahre);
        cBoxJahr.setModel(cBoxJahrModel);
        panelQAusw.add(cBoxJahr);

        cBoxQuartal = new JComboBox<>();
        cBoxQuartal.setBounds(123, 60, 66, 24);
        quartal = new Vector<>();
        quartal.add("1");
        quartal.add("2");
        quartal.add("3");
        quartal.add("4");
        cBoxQuartalModel = new DefaultComboBoxModel<>(quartal);
        cBoxQuartal.setModel(cBoxQuartalModel);
        panelQAusw.add(cBoxQuartal);

        btnAusw = new JButton("auswerten");
        btnAusw.setBounds(12, 138, 176, 25);
        btnAusw.addActionListener(this);
        panelQAusw.add(btnAusw);

        panelAusw = new JPanel();
        panelAusw.setBorder(new TitledBorder(new LineBorder(null, 1, true),
                "Auswertung", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));
        panelAusw.setBounds(217, 5, 444, 175);
        frmAuswertung.getContentPane().add(panelAusw);
        panelAusw.setLayout(null);

        lblJahr_1 = new JLabel("Jahr:");
        lblJahr_1.setBounds(12, 25, 70, 15);
        panelAusw.add(lblJahr_1);

        lblQuartal_1 = new JLabel("Quartal:");
        lblQuartal_1.setBounds(12, 65, 70, 15);
        panelAusw.add(lblQuartal_1);

        lblUmsatz_1 = new JLabel("Umsatz:");
        lblUmsatz_1.setBounds(12, 109, 70, 15);
        panelAusw.add(lblUmsatz_1);

        lblErgebnis_2 = new JLabel("0,00 EUR");
        lblErgebnis_2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblErgebnis_2.setBounds(77, 109, 105, 15);
        panelAusw.add(lblErgebnis_2);

        lblEinArt = new JLabel("Einnahmeart:");
        lblEinArt.setBounds(200, 25, 107, 15);
        panelAusw.add(lblEinArt);

        lblMitglied = new JLabel("Mitglied:");
        lblMitglied.setBounds(200, 65, 70, 15);
        panelAusw.add(lblMitglied);

        lblMenge = new JLabel("Menge:");
        lblMenge.setBounds(12, 143, 70, 15);
        panelAusw.add(lblMenge);

        lblMengeErg = new JLabel("0 St.");
        lblMengeErg.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMengeErg.setBounds(94, 143, 89, 15);
        panelAusw.add(lblMengeErg);

        cBoxJahr2 = new JComboBox<>();
        cBoxJahr2.setBounds(116, 20, 66, 24);
        cBoxJahr2.setModel(cBoxJahrModel);
        panelAusw.add(cBoxJahr2);

        cBoxQuartal2 = new JComboBox<>();
        cBoxQuartal2.setBounds(116, 60, 66, 24);
        cBoxQuartal2.setModel(cBoxQuartalModel);
        panelAusw.add(cBoxQuartal2);

        cBoxEinArt = new JComboBox<>();
        einnahmeArt = new Vector<>();
        einnahmeArt.add("Tageskarte");
        einnahmeArt.add("Shopartikel");
        einnahmeArt.add("Event");
        einnahmeArt.add("Jahresgeb\u00FChr");
        cBoxEinArtModel = new DefaultComboBoxModel<>(einnahmeArt);
        cBoxEinArt.setModel(cBoxEinArtModel);
        cBoxEinArt.setBounds(320, 20, 112, 24);
        panelAusw.add(cBoxEinArt);

        cBoxMitglied = new JComboBox<>();
        mitglied = new Vector<>();
        mitglied.add("101");
        mitglied.add("102");
        mitglied.add("103");
        mitglied.add("104");
        mitglied.add("106");
        cBoxMitgliedModel = new DefaultComboBoxModel<>(mitglied);
        cBoxMitglied.setModel(cBoxMitgliedModel);
        cBoxMitglied.setBounds(366, 60, 66, 24);
        panelAusw.add(cBoxMitglied);

        btnAusw2 = new JButton("auswerten");
        btnAusw2.setBounds(315, 138, 117, 25);
        btnAusw2.addActionListener(this);
        panelAusw.add(btnAusw2);
    }

    // Listener für die Menüelemente und Buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAusw) {
            LOG.info("DB Verbindung wird hergestellt.");
            dbgetter = new DBConnection(SERVERNAME, PORT, DB, USER, PWD);

            dwAusw = new DWAuswertung(dbgetter);

            lblErgebnis.setText(dwAusw.getQuartal(
                    (String)cBoxJahr.getSelectedItem(),
                    (String)cBoxQuartal.getSelectedItem()));
        }

        if(e.getSource() == btnAusw2) {
            LOG.info("DB Verbindung wird hergestellt.");
            dbgetter = new DBConnection(SERVERNAME, PORT, DB, USER, PWD);

            dwAusw = new DWAuswertung(dbgetter);
            String[] array = dwAusw.getAuswertung(
                    (String)cBoxJahr2.getSelectedItem(),
                    (String)cBoxQuartal2.getSelectedItem(),
                    (String)cBoxEinArt.getSelectedItem(),
                    (String)cBoxMitglied.getSelectedItem());

            lblErgebnis_2.setText(array[0]);
            lblMengeErg.setText(array[1]);
        }

        if(e.getSource() == mnItmQExp) {
            pfad = null;
            chooser = new JFileChooser(pfad);
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            plainFilter = new FileNameExtensionFilter("*.csv", "csv");
            chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
            chooser.setFileFilter(plainFilter);
            chooser.setDialogTitle("Speichern unter...");
            chooser.setVisible(true);

            file = null;

            int result = chooser.showSaveDialog(frmAuswertung);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
            }

            if (plainFilter.accept(file)) {
                LOG.info(file.getAbsolutePath() + " bereit zum speichern.");
                entries = new String[3];
                entries[0] = lblErgebnis.getText().substring(0, lblErgebnis.getText().length() - 4);
                entries[1] = (String)cBoxJahr.getSelectedItem();
                entries[2] = (String)cBoxQuartal.getSelectedItem();

                if(new CSVParser().saveAs(file, entries)) {
                    LOG.info("Datei erfolgreich gespeichert.");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Daten erfolgreich exportiert nach:\n" +
                                    file.getAbsolutePath(),
                            "Datenexport", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    LOG.log(Level.WARNING, "Datei nicht gespeichert.");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Daten nicht exportiert!",
                            "Datenexport", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                LOG.info(file.getAbsolutePath() + " ist der falsche Dateityp.");
                JOptionPane.showMessageDialog(new JFrame(),
                        "Bitte folgendes Format nutzen:\n" +
                                "{DATEINAME}.csv",
                        "Datenexport", JOptionPane.ERROR_MESSAGE);
            }
            chooser.setVisible(false);
        }

        if(e.getSource() == mnItmAExp) {
            pfad = null;
            chooser = new JFileChooser(pfad);
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            plainFilter = new FileNameExtensionFilter("*.csv", "csv");
            chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
            chooser.setFileFilter(plainFilter);
            chooser.setDialogTitle("Speichern unter...");
            chooser.setVisible(true);

            file = null;

            int result = chooser.showSaveDialog(frmAuswertung);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
            }

            if (plainFilter.accept(file)) {
                LOG.info(file.getAbsolutePath() + " bereit zum speichern.");
                entries = new String[6];
                entries[0] = lblErgebnis.getText().substring(0, lblErgebnis.getText().length() - 4);
                entries[1] = lblMengeErg.getText().substring(0, lblMengeErg.getText().length() - 4);
                entries[2] = (String)cBoxEinArt.getSelectedItem();
                entries[3] = (String)cBoxMitglied.getSelectedItem();
                entries[4] = (String)cBoxJahr.getSelectedItem();
                entries[5] = (String)cBoxQuartal.getSelectedItem();

                if(new CSVParser().saveAs(file, entries)) {
                    LOG.info("Datei erfolgreich gespeichert.");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Daten erfolgreich exportiert nach:\n" +
                                    file.getAbsolutePath(),
                            "Datenexport", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    LOG.log(Level.WARNING, "Datei nicht gespeichert.");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Daten nicht exportiert!",
                            "Datenexport", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                LOG.info(file.getAbsolutePath() + " ist der falsche Dateityp.");
                JOptionPane.showMessageDialog(new JFrame(),
                        "Bitte folgendes Format nutzen:\n" +
                                "{DATEINAME}.csv",
                        "Datenexport", JOptionPane.ERROR_MESSAGE);
            }
            chooser.setVisible(false);
        }

        if(e.getSource() == mnItmLog) {
            try {
                Desktop.getDesktop().browse(new URI("file://" + System.getProperty("user.home") +
                        System.getProperty("file.separator") + "dw-log.html"));
            } catch (URISyntaxException | IOException e1) {
                LOG.log(Level.WARNING, "Protokoll kann nicht geöffnet werden.", e1);
                JOptionPane.showMessageDialog(new JFrame(),
                        "Protokoll konnte nicht geöffnet werden.",
                        "Protokoll öffnen...", JOptionPane.ERROR_MESSAGE);
            }
        }

        if(e.getSource() == mnItmQuit) {
            frmAuswertung.dispose();
        }

        if(e.getSource() == mnItmAbout) {
            About frame = new About();
            frame.setVisible(true);
        }
    }
}
