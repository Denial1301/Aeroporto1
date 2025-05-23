package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class InserisciVoloForm {
    private JPanel mainPanel;
    private JTextField flyCodeField;
    private JLabel codeFlyLabel;
    private JLabel originLabel;
    private JLabel destinationLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private JLabel delayLabel;
    private JTextField flyCompanyField;
    private JTextField flyOriginField;
    private JTextField fyDestinationField;
    private JButton cancelBtn;
    private JButton saveBtn;
    private JLabel seatNumberLabel;
    private JTextField seatNumberField;
    private JTextField dateField;
    private JTextField timeArriveField;
    private JTextField delayField;
    private JFrame frame;



    public JFrame getFrame() {
        return frame;
    }

    public InserisciVoloForm(Frame frameC, Controller controller) {
        frame = new JFrame("Inserisci Volo");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 370);
        frame.setVisible(true);
        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter orarioFormatter = DateTimeFormatter.ofPattern("HH:mm");


                    LocalDate date = LocalDate.parse(dateField.getText(), dataFormatter);
                    LocalTime time = LocalTime.parse(timeArriveField.getText(), orarioFormatter);



                    controller.registraVolo(flyCodeField.getText(), flyCompanyField.getText(), flyOriginField.getText(),
                            fyDestinationField.getText(),date,time,Integer.parseInt(delayField.getText()),Integer.parseInt(seatNumberField.getText()));
                    JOptionPane.showMessageDialog(frame, "Volo aggiunto!");
                    frame.setVisible(false);
                    frameC.setVisible(true);


                } catch (Exception ex) { JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameC.setVisible(true);
            }
        });


    }
}
