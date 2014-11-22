package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: view
 */
public class Auswahl implements ActionListener {

    public JFrame frmChooser;
    private JButton btnCSV;
    private JButton btnAuswertung;

    /**
     * Erzeugen der Anwendung.
     */
    public Auswahl() {
        initialize();
    }

    /**
     * Initialisieren der grafischen Oberfläche.
     */
    private void initialize() {
        frmChooser = new JFrame();
        frmChooser.setTitle("Auswahl");
        frmChooser.setBounds(100, 100, 290, 140);
        frmChooser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmChooser.setLocationRelativeTo(null);
        frmChooser.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        frmChooser.getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 2, 10, 0));

        btnAuswertung = new JButton("Auswertung");
        btnAuswertung.addActionListener(this);
        panel.add(btnAuswertung);

        btnCSV = new JButton("CSV-Import");
        btnCSV.addActionListener(this);
        panel.add(btnCSV);
        frmChooser.setVisible(true);
    }

    // Listener für die Buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnCSV) {
            CSVImport csv = new CSVImport();
            csv.frmLoginWindow.setVisible(true);
        }
        if(e.getSource() == btnAuswertung) {
            Auswertung ausw = new Auswertung();
            ausw.frmAuswertung.setVisible(true);
        }
    }
}
