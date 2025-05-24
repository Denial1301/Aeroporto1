package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JPanel mainPanel;
    private JLabel backgroundLabel;
    private JButton entraBtn;

    private static JFrame frameHome;

    public static void main(String[] args)
    {
        frameHome = new JFrame("Autenticazione");
        frameHome.setContentPane(new MainGUI().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);
        frameHome.setSize(500, 700);

    }
    public MainGUI() {
        entraBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameHome.setVisible(false);
                Autenticazione autenticazione = new Autenticazione(frameHome);
                frameHome.setVisible(false);
                autenticazione.getFrameHome().setVisible(true);
            }
        });
    }
}
