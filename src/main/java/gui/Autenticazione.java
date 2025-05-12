package gui;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Autenticazione {
    private JPanel mainPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registratiButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel welcomeLabel;
    private JCheckBox passwordCheck;
    private static JFrame frameHome;
    private Controller controller;
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon(Autenticazione.class.getResource("/resources/Logo.jpg"));
        Image image = icon.getImage();
        frameHome = new JFrame("Autenticazione");
        frameHome.setIconImage(image);
        frameHome.setContentPane(new Autenticazione().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);
        frameHome.setSize(400, 200);


    }

    public Autenticazione() {
        controller = new Controller();
        registratiButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) { // REGISTRAZIONE
                Registrazione registrazione = new Registrazione(frameHome);
                registrazione.getFrame().setVisible(true);
                frameHome.setVisible(false);
                loginField.setText("");
                passwordField.setText("");
            }
        });
        passwordCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (passwordCheck.isSelected()) {
                    passwordField.setEchoChar((char)0);
                }else passwordField.setEchoChar('•');

            }
        });
        loginBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // codice per il login da aggiungere
                
            }
        });

    }
}
