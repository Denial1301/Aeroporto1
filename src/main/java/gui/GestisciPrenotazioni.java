package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The type Gestisci prenotazioni.
 */
public class GestisciPrenotazioni {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JLabel searchLabel;
    private JButton searchBtn;
    private JButton cancelBtn;
    private JTextField searchField;
    private JScrollPane prenotazioniScroll;
    private JList prenotazioniLista;
    private JButton cambiaPrenotazione;
    private JLabel cancellaPrenotazioneLabel;
    private JButton siBtn;
    private JButton noBtn;
    private JPanel cancellaPanel;
    private Controller controller;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


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
     * Instantiates a new Gestisci prenotazioni.
     *
     * @param frameC     the frame c
     * @param controller the controller
     */
    public GestisciPrenotazioni(Frame frameC, Controller controller) {
        this.controller = controller;
        frame = new JFrame("Gestisci Prenotazioni");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200, 370);
        frame.setVisible(true);
        cancellaPanel.setVisible(false);
        prenotazioni();
        prenotazioniLista.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!prenotazioniLista.getSelectedValuesList().isEmpty())
                {
                    cancellaPanel.setVisible(true);
                }
            }
        });
        siBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valoreSelezionato = (String) prenotazioniLista.getSelectedValue();
                String emailLogin = controller.getEmailLogin();
                int start = valoreSelezionato.indexOf("codice volo: '") + "codice volo: '".length();

                int end = valoreSelezionato.indexOf("'", start);
                String codiceVolo = valoreSelezionato.substring(start, end);
                System.out.println(codiceVolo);
                int start2 = valoreSelezionato.indexOf("cf: '") + "cf: '".length();
                int end2 = valoreSelezionato.indexOf("'", start2);
                String cf = valoreSelezionato.substring(start2,end2);
                System.out.println(cf);

                    controller.deletePrenotazione(emailLogin,codiceVolo,cf);
                    JOptionPane.showMessageDialog(frame, "Prenotazione eliminata","Conferma",JOptionPane.INFORMATION_MESSAGE);
                    searchField.setText("");
                    prenotazioni();
                    prenotazioniLista.clearSelection();
                    cancellaPanel.setVisible(false);
            }
        });
        noBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            cancellaPanel.setVisible(false);
            }
        });



        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            prenotazioni();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameC.setVisible(true);
            }
        });
        cambiaPrenotazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String valoreSelezionato = (String) prenotazioniLista.getSelectedValue();
                if (valoreSelezionato == null)
                {
                JOptionPane.showMessageDialog(frame, "Selezionare una prenotazione.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
                }
                String emailLogin = controller.getEmailLogin();
                int start = valoreSelezionato.indexOf("codice volo: '") + "codice volo: '".length();
                int end = valoreSelezionato.indexOf("'", start);
                String codiceVolo = valoreSelezionato.substring(start, end);
                int start2 = valoreSelezionato.indexOf("cf: '") + "cf: '".length();
                int end2 = valoreSelezionato.indexOf("'", start2);
                String cfVecchio = valoreSelezionato.substring(start2,end2);
                String nome, cognome, cf, numDoc, dataNascitaS, dataEmissioneS, dataScadenzaS;
                nome = inputSequenziale("Nome");
                if (nome == null) return;

                cognome = inputSequenziale("Cognome");
                if (cognome == null) return;

                cf = inputSequenziale("Codice fiscale");
                if (cf == null) return;

                numDoc = inputSequenziale("Numero documento");
                if (numDoc == null) return;

                dataNascitaS = inputSequenziale("Data di nascita (dd/MM/yyyy)");
                if (dataNascitaS == null) return;
                LocalDate dataNascita = LocalDate.parse(dataNascitaS,formatter);

                dataEmissioneS = inputSequenziale("Data emissione documento (dd/MM/yyyy)");
                if (dataEmissioneS == null) return;
                LocalDate dataEmissione = LocalDate.parse(dataEmissioneS,formatter);
                dataScadenzaS = inputSequenziale("Data scadenza documento (dd/MM/yyyy)");
                if (dataScadenzaS == null) return;
                LocalDate dataScadenza = LocalDate.parse(dataScadenzaS,formatter);
                controller.updateDocumento(numDoc,dataEmissione,dataScadenza,cfVecchio);
                controller.updatePasseggero(nome,cognome,cf,numDoc,dataNascita,dataEmissione,dataScadenza,emailLogin,codiceVolo, cfVecchio);

                controller.updatePrenotazione(cf,cfVecchio);



                searchField.setText("");
                prenotazioni();


            }
        });
    }


    /**
     * Prenotazioni. <p>
     * Serve a formattare tutta la lista delle prenotazioni.
     */
    public void prenotazioni() {
        String ricerca = searchField.getText();
        try
        {
            prenotazioniLista.setModel(new DefaultListModel<>());
            prenotazioniLista.setListData(new String[0]);
            DefaultListModel<String> lista = new DefaultListModel<>();

            ArrayList<String> prenotazioni = controller.cercaPrenotazioni(ricerca);
            for (String voliPrenotati : prenotazioni) {

                lista.addElement(voliPrenotati);
            }
            prenotazioniLista.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value,
                                                              int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    String testo = value.toString();
                    String stato = "";
                    String prefix = "stato: '";
                    int start = testo.indexOf(prefix);
                    if (start != -1) {
                        int statoStart = start + prefix.length();
                        int end = testo.indexOf("'", statoStart);
                        if (end != -1) {
                            stato = testo.substring(statoStart, end);
                        }
                    }
                    Color bgColor = list.getBackground();
                    Color fgColor = list.getForeground();
                    switch (stato) {
                        case "In_Attesa":
                            bgColor = Color.ORANGE;
                            fgColor = Color.BLACK;
                            break;
                        case "Cancellata":
                            bgColor = Color.RED;
                            fgColor = Color.WHITE;
                            break;
                        case "Confermata":
                            bgColor = Color.GREEN;
                            fgColor = Color.BLACK;
                            break;
                    }


                    if (isSelected) {
                        label.setBackground(list.getSelectionBackground());
                        label.setForeground(list.getSelectionForeground());
                    } else {
                        label.setBackground(bgColor);
                        label.setForeground(fgColor);
                    }


                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                    return label;
                }
            });


            prenotazioniLista.setModel(lista);

        }catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }

    }

    /**
     * Input sequenziale string.
     *
     * @param campo the campo
     * @return the string
     */
    public String inputSequenziale(String campo)
    {
        String input;
        do{
            input = JOptionPane.showInputDialog(frame, campo,campo,JOptionPane.PLAIN_MESSAGE);
            if(input == null)
            {
                return null;
            }
            if(input.isEmpty())
            {
                JOptionPane.showMessageDialog(frame, "Non puoi inserire un campo vuoto.","Errore",javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        }while(input.isEmpty());
        return input;
    }
}
