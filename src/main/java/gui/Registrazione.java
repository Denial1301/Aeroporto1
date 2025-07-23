package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;


/**
 * The type Registrazione.
 */
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
    private JLabel dateLabel;
    private JTextField dateField;


    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Instantiates a new Registrazione.
     *
     * @param frameC     the frame c
     * @param controller the controller
     */
    public Registrazione(JFrame frameC, Controller controller)
    {
        frame = new JFrame("Registrazione");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 370);
        frame.setVisible(true);



        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeField.getText();
                    String cognome = cognomeField.getText();
                    String email = emailField.getText();
                    if(!controller.isEmailValid(email))
                    {
                        JOptionPane.showMessageDialog(frame, "Email non valida.", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String password = new String(passwordField.getPassword());
                    String dataNascita = dateField.getText();
                    String[] campi = {nome, cognome, email, password, dataNascita};

                    if (controller.isVuoto(campi)) {
                        JOptionPane.showMessageDialog(frame, "Compilare tutti i campi.", "Campi vuoti", JOptionPane.WARNING_MESSAGE);
                    } else {
                        controller.reigstraUtente(email,nome,cognome,password,dataNascita);
                        nomeField.setText("");
                        cognomeField.setText("");
                        emailField.setText("");
                        passwordField.setText("");
                        dateField.setText("");

                        JOptionPane.showMessageDialog(frame, "Utente creato correttamente.");
                        frame.setVisible(false);
                        frameC.setVisible(true);
                        frame.dispose();
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato della data non valido.", "Formato data errato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mostraCheckBox.addActionListener(new ActionListener()
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