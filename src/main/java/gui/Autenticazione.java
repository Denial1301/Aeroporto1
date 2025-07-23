package gui;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Autenticazione
{
    private JPanel mainPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registratiButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel welcomeLabel;
    private JCheckBox passwordCheck;
    private  JFrame frameHome;
    private Controller controller;

    public JFrame getFrameHome() {
        return frameHome;
    }

    public Autenticazione(Frame frameC)
    {
        frameHome = new JFrame("Autenticazione");
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frameHome.pack();
        frameHome.setVisible(true);
        frameHome.setSize(450, 200);
        controller = new Controller();
        registratiButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) { // REGISTRAZIONE
                Registrazione registrazione = new Registrazione(frameHome,controller);
                registrazione.getFrame().setVisible(true);
                frameHome.setVisible(false);
                loginField.setText("");
                passwordField.setText("");
            }
        });
        passwordCheck.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (passwordCheck.isSelected())
                {
                    passwordField.setEchoChar((char)0);
                }else
                    passwordField.setEchoChar('•');

            }
        });
        loginBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String password = String.valueOf(passwordField.getPassword());
                String login = loginField.getText();
                String[] campi = {login, password};
                if (controller.isVuoto(campi))
                {
                    JOptionPane.showMessageDialog(frameHome,"Dati mancanti ricontrollare i dati.","Warning", JOptionPane.WARNING_MESSAGE);

                }else
                 {
                     try {
                         controller.login(login,password);
                         if (login.contains("amministratoreaeroportonapoli"))
                         {
                            AdminPage adminPage = new AdminPage(frameHome, controller);
                            adminPage.getFrame().setVisible(true);
                         }else
                         {
                             UtentePage utentePage = new UtentePage(frameHome,controller);
                             utentePage.getFrame().setVisible(true);
                         }
                         frameHome.setVisible(false);
                         loginField.setText("");
                         passwordField.setText("");
                     } catch (Exception ex) {
                         JOptionPane.showMessageDialog(frameHome, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);

                     }
                 }
            }
        });
        loginField.addKeyListener(new KeyAdapter() { //VA ELIMINATA QUANDO FACCIAMO VEDERE AL PROF.
            @Override
            public void keyPressed(KeyEvent e) {
                String login;
                String password;
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    login = "andrea.colombo@amministratoreaeroportonapoli.it";
                    password = "admin123";
                    controller.login(login,password);
                    frameHome.setVisible(false);
                    AdminPage adminPage = new AdminPage(frameHome, controller);
                    adminPage.getFrame().setVisible(true);
                }else if(e.getKeyCode() == KeyEvent.VK_F1){
                    login = "dani.guardascione@gmail.com";
                    password = "123";
                    controller.login(login,password);
                    frameHome.setVisible(false);
                    UtentePage utentePage = new UtentePage(frameHome, controller);
                    utentePage.getFrame().setVisible(true);
                }
            }
        });


    }
}
