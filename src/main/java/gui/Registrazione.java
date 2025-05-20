package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Registrazione {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel registerAsLabel;
    private JLabel nomeLabel;
    private JTextField nomeField;
    private JButton returnBtn;
    private JLabel cognomeLabel;
    private JTextField cognomeField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JCheckBox mostraCheckBox;


    public JFrame getFrame() {
        return frame;
    }

    public Registrazione(JFrame frameC, Controller controller)
    {
        frame = new JFrame("Registrazione");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 370);
        frame.setVisible(true);



        returnBtn.addActionListener(new ActionListener() //UTILIZZATO PER TORNARE AL FORM "AUTENTICAZIONE" DOPO LA REGISTRAZIONE
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();





                controller.reigstraUtente(nomeField.getText(), passwordField.getText(),cognomeField.getText(), emailField.getText()); // da togliere nel caso
                nomeField.setText("");
                cognomeField.setText("");
                emailField.setText("");
                passwordField.setText("");

                JOptionPane.showMessageDialog(frame, "Utente creato correttamente");
                frame.setVisible(false);
                frameC.setVisible(true);
                frame.dispose();
            }
        });




        mostraCheckBox.addActionListener(new ActionListener() //METODO PER MOSTRARE LA PASSWORD TRAMITE UNA CHECKBOX
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostraCheckBox.isSelected())
                {
                    passwordField.setEchoChar((char)0);
                }else
                    passwordField.setEchoChar('•');
            }
        });


    }

}