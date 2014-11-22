package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: ikarus
 * Date: 19.06.13
 * Package: view
 */
public class About extends JFrame implements ActionListener {

    private static final long serialVersionUID = -4475683170944454675L;
    private final JPanel contentPane;
    private final JLabel lblImage;
    private final JLabel lblAutor;
    private final JLabel lblMatNr;
    private final JLabel lblModul;
    private final JLabel lblJadeHS;
    private final JButton btnQuit;

    /**
     * Erzeugung der grafischen Oberfläche.
     */
    public About() {
        setTitle("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 314, 183);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().
                getResource("res/Logo_JadeHochschule.png"));
        lblImage = new JLabel(icon);
        lblImage.setBounds(12, 10, 103, 49);
        contentPane.add(lblImage);

        lblAutor = new JLabel("Autor: Nils Lutz");
        lblAutor.setBounds(138, 10, 138, 15);
        contentPane.add(lblAutor);

        lblMatNr = new JLabel("Matrikelnr.: 6002109");
        lblMatNr.setBounds(138, 37, 162, 15);
        contentPane.add(lblMatNr);

        lblModul = new JLabel("DBIS SS13");
        lblModul.setBounds(138, 64, 70, 15);
        contentPane.add(lblModul);

        lblJadeHS = new JLabel("Jade Hochschule");
        lblJadeHS.setBounds(138, 91, 138, 15);
        contentPane.add(lblJadeHS);

        btnQuit = new JButton("Schlie\u00dfen");
        btnQuit.setBounds(138, 118, 117, 25);
        btnQuit.addActionListener(this);
        contentPane.add(btnQuit);
    }

    // Listener zum Button
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnQuit) {
            this.dispose();
        }
    }
}
