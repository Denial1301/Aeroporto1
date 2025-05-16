package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtenteForm {
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JButton viewBtn;
    private JButton prenotaButton;
    private JPanel btnPanel;
    private JButton modificaPrenotazioneButton;
    private JButton annullaPrenotazioneBtn;
    private JButton logoutBtn;
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public UtenteForm(JFrame frameC)
    {
    frame = new JFrame("Pagina Utente");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(mainPanel);
    frame.pack();
    frame.setSize(frameC.getWidth(), 300);

    logoutBtn.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frameC.setVisible(true);

        }
    });
    }
}
