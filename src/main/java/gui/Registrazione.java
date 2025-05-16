package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Registrazione {
    private JFrame frame;
    private Controller controller;
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
    private JLabel idAmministratoreLabel;
    private JTextField idAmministratoreField;
    private JCheckBox amministratoreCheck;
    private JCheckBox utenteCheck;
    private JPasswordField passwordField;
    private JCheckBox mostraCheckBox;



    public JFrame getFrame() {
        return frame;
    }

    public Registrazione(JFrame frameC)
    {
        frame = new JFrame("Registrazione");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 370);
        frame.setVisible(true);
        idAmministratoreLabel.setVisible(false);
        idAmministratoreField.setVisible(false);
        ButtonGroup gruppo = new ButtonGroup();
        gruppo.add(utenteCheck);
        gruppo.add(amministratoreCheck);
        utenteCheck.setSelected(true);

        returnBtn.addActionListener(new ActionListener() //UTILIZZATO PER TORNARE AL FORM "AUTENTICAZIONE" DOPO LA REGISTRAZIONE
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String idAmministratore = idAmministratoreField.getText();
                boolean isUtente = utenteCheck.isSelected();
                boolean isAdmin = amministratoreCheck.isSelected();

                if ((isUtente && (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty())) ||
                   (isAdmin && (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || idAmministratore.isEmpty())))
                {
                    JOptionPane.showMessageDialog(frame, "Errore, dati mancanti ricontrollare i campi.");
                    return;
                }
                nomeField.setText("");
                cognomeField.setText("");
                emailField.setText("");
                passwordField.setText("");
                idAmministratoreField.setText("");

                JOptionPane.showMessageDialog(frame, "Utente creato correttamente");
                frame.setVisible(false);
                frameC.setVisible(true);
                frame.dispose();

            }
        });

        amministratoreCheck.addActionListener(new ActionListener() { //TIPO REGISTRAZIONE (amministratore)
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean isSelected = amministratoreCheck.isSelected();
                idAmministratoreLabel.setVisible(isSelected);
                idAmministratoreField.setVisible(isSelected);
                frame.revalidate();
                frame.repaint();
            }
        });

        utenteCheck.addActionListener(new ActionListener() // TIPO DI REGISTRAZIONE(utente)
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                idAmministratoreLabel.setVisible(false);
                idAmministratoreField.setVisible(false);
                frame.revalidate();
                frame.repaint();
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