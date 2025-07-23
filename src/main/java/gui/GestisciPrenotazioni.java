package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

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


    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }


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
                String cf = valoreSelezionato.substring(start2,end2);
                String nomeNuovo = JOptionPane.showInputDialog(frame,"Cambia nominativo","Cambia nominativo",JOptionPane.PLAIN_MESSAGE);
                if(nomeNuovo != null)
                {
                    String cognomeNuovo = JOptionPane.showInputDialog(frame, "Cambia cognome", "Cambia cognome", JOptionPane.PLAIN_MESSAGE);
                    controller.updatePrenotazione(nomeNuovo,cognomeNuovo,emailLogin,codiceVolo,cf);
                    searchField.setText("");
                }
                prenotazioni();


            }
        });
    }


    /**
     * Prenotazioni. <p>
     *     Serve a formattare tutta la lista delle prenotazioni.
     *
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
                            bgColor = new Color(0, 128, 0);
                            fgColor = Color.WHITE;
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
}
