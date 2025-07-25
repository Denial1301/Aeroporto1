package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;


/**
 * The type Inserisci volo.
 */
public class InserisciVolo {
    private JPanel mainPanel;
    private JTextField flyCodeField;
    private JLabel codeFlyLabel;
    private JLabel originLabel;
    private JLabel destinationLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
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
    private JLabel flyStateLabel;
    private JComboBox flyStateBox;
    private JLabel timeDeparturesLabel;
    private JTextField timeDepartureField;
    private JFrame frame;


    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Instantiates a new Inserisci volo.
     *
     * @param frameC     the frame c
     * @param controller the controller
     */
    public InserisciVolo(Frame frameC, Controller controller) {
        frame = new JFrame("Inserisci Volo");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 370);
        frame.setVisible(true);



        flyStateBox.addItem("Programmato");
        flyStateBox.setSelectedIndex(0);

        flyStateBox.setSelectedIndex(0);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter orarioFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    String codice = flyCodeField.getText();
                    String compagnia = flyCompanyField.getText();
                    String aereoportoOrigine = flyOriginField.getText();
                    String aereoportoDestinazione = fyDestinationField.getText();
                    LocalDate date = LocalDate.parse(dateField.getText(), dataFormatter);
                    LocalTime oraPartenza = LocalTime.parse(timeDepartureField.getText(),orarioFormatter);
                    LocalTime oraArrivo = LocalTime.parse(timeArriveField.getText(), orarioFormatter);
                    String stato = Objects.requireNonNull(flyStateBox.getSelectedItem()).toString();

                    int numPosti = Integer.parseInt(seatNumberField.getText());
                    String[] campi = {codice,compagnia,aereoportoOrigine,aereoportoDestinazione,dateField.getText(), timeDepartureField.getText(),
                            timeArriveField.getText(),stato,seatNumberField.getText()};
                    if(controller.isVuoto(campi))
                    {
                        JOptionPane.showMessageDialog(frame,"Compilare tutti i campi.","Campi vuoti",JOptionPane.WARNING_MESSAGE);
                    }
                    if(!codice.startsWith("NAP") && !codice.startsWith("ARR"))
                    {
                        JOptionPane.showMessageDialog(frame, "Il codice deve iniziare con NAP o ARR","Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.registraVolo(codice,compagnia,aereoportoOrigine,aereoportoDestinazione,date,oraPartenza, oraArrivo,0,numPosti,stato);
                    JOptionPane.showMessageDialog(frame, "Volo aggiunto!");
                    frame.setVisible(false);
                    frameC.setVisible(true);



                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formattazione della data o ora errata.","Errore", JOptionPane.ERROR_MESSAGE);
                }catch (DateTimeException ex) {
                    JOptionPane.showMessageDialog(frame,ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
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
