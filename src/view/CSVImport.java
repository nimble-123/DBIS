package view;

import model.CSVParser;
import model.DBConnection;
import model.DWImport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: view
 */
public class CSVImport implements ActionListener {

    private static final Logger LOG = Logger.getGlobal();

    // Importframe
    public JFrame frmLoginWindow;
    private JTextField txtFdUser;
    private JTextField txtFdDB;
    private JPasswordField pwdField;
    private JTextField txtFdServer;
    private JTextField txtFdPort;
    private JTextField txtFdFile;
    private JButton btnBeenden;
    private JButton btnDatei;
    private JButton btnImport;
    private JLabel lblUser;
    private JLabel lblPwd;
    private JLabel lblDB;
    private JLabel lblServer;
    private JLabel lblPort;
    private JLabel lblDatei;

    // Verbindungsmanagement
    private DBConnection dbgetter;
    private Connection con;
    private List<String[]> list;

    /**
     * Initialisation der Anwendung.
     */
    public CSVImport() {
        initialize();
    }

    /**
     * Initialisation der grafischen Oberfläche.
     */
    private void initialize() {
        frmLoginWindow = new JFrame();
        frmLoginWindow.setTitle("Import von Datens\u00E4tzen");
        frmLoginWindow.setBounds(100, 100, 451, 225);
        frmLoginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmLoginWindow.setLocationRelativeTo(null);

        lblUser = new JLabel("Benutzername:");
        lblPwd = new JLabel("Passwort:");
        lblDB = new JLabel("Datenbank:");
        lblServer = new JLabel("Server:");
        lblPort = new JLabel("Port:");
        lblDatei = new JLabel("Datei:");

        txtFdUser = new JTextField();
        txtFdUser.setHorizontalAlignment(SwingConstants.TRAILING);
        txtFdUser.setText("DB6002109");
        txtFdUser.setFont(new Font("Dialog", Font.PLAIN, 12));
        txtFdUser.setColumns(10);

        txtFdDB = new JTextField();
        txtFdDB.setHorizontalAlignment(SwingConstants.TRAILING);
        txtFdDB.setText("DB6002109");
        txtFdDB.setFont(new Font("Dialog", Font.PLAIN, 12));
        txtFdDB.setColumns(10);

        pwdField = new JPasswordField();
        pwdField.setHorizontalAlignment(SwingConstants.TRAILING);
        pwdField.setText("marlboro111");
        pwdField.setFont(new Font("Dialog", Font.PLAIN, 12));

        txtFdServer = new JTextField();
        txtFdServer.setHorizontalAlignment(SwingConstants.TRAILING);
        txtFdServer.setText("whv-fbmit3.hs-woe.de");
        txtFdServer.setFont(new Font("Dialog", Font.PLAIN, 12));
        txtFdServer.setColumns(10);

        txtFdPort = new JTextField();
        txtFdPort.setHorizontalAlignment(SwingConstants.TRAILING);
        txtFdPort.setText("1433");
        txtFdPort.setFont(new Font("Dialog", Font.PLAIN, 12));
        txtFdPort.setColumns(10);

        txtFdFile = new JTextField();
        txtFdFile.setHorizontalAlignment(SwingConstants.TRAILING);
        txtFdFile.setText(getClass().getClassLoader().getResource("res/Extra_Umsaetze.csv").toString().substring(9));
        txtFdFile.setFont(new Font("Dialog", Font.PLAIN, 12));
        txtFdFile.setColumns(10);

        btnImport = new JButton("Import");
        btnImport.addActionListener(this);

        btnDatei = new JButton("Datei w\u00E4hlen");
        btnDatei.addActionListener(this);

        btnBeenden = new JButton("Beenden");
        btnBeenden.addActionListener(this);

        GroupLayout groupLayout = new GroupLayout(frmLoginWindow.getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(lblUser)
                                                .addComponent(lblDB)
                                                .addComponent(lblPwd)
                                                .addComponent(lblServer)
                                                .addComponent(lblDatei)
                                                .addComponent(lblPort))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtFdPort, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                                .addComponent(txtFdServer, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                                .addComponent(pwdField, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                                .addComponent(txtFdUser, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                                .addComponent(txtFdDB, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                                .addComponent(txtFdFile, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)))
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(btnDatei, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(114)
                                        .addComponent(btnImport)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBeenden)))
                        .addContainerGap())
        );
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblUser)
                                .addComponent(txtFdUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPwd)
                                .addComponent(pwdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDB)
                                .addComponent(txtFdDB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblServer)
                                .addComponent(txtFdServer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPort)
                                .addComponent(txtFdPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDatei)
                                .addComponent(txtFdFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnDatei)
                                .addComponent(btnBeenden)
                                .addComponent(btnImport))
                        .addGap(202))
        );
        frmLoginWindow.getContentPane().setLayout(groupLayout);
    }

    // Listener um Mausklicks abzufangen und zu verarbeiten
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBeenden) {
            frmLoginWindow.dispose();
        }

        if (e.getSource() == btnDatei) {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(fc);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtFdFile.setText(file.getAbsolutePath());
            }
            LOG.info("Dateiauswahl erfolgreich.");
        }

        if (e.getSource() == btnImport) {
            try {
                LOG.info("DB Verbindung wird hergestellt.");
                dbgetter = new DBConnection(txtFdServer.getText(),
                        txtFdPort.getText(), txtFdDB.getText(),
                        txtFdUser.getText(), new String(
                        pwdField.getPassword()));
                con = dbgetter.getConnection();
                con.setAutoCommit(false);

                list = new CSVParser(txtFdFile.getText()).getLines();

                LOG.info("Importvorgang wird gestartet.");
                @SuppressWarnings("unused")
                DWImport dwimport = new DWImport(con, list);
                dbgetter.freeConnection();
            } catch (SQLException e1) {
                LOG.log(Level.SEVERE, "Fehler im Datensatz.", e1);
                JOptionPane.showMessageDialog(new JFrame(),
                        "Keine Verbindung zur Datenbank!\n" +
                                "Bitte \u00FCberprüfen Sie Ihre Angaben!",
                        "Datenimport", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
