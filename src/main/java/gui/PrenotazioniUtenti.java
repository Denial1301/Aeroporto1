package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Prenotazioni utenti.
 */
public class PrenotazioniUtenti {
    private JPanel mainPanel;
    private JLabel utentiPrenotatiLabel;
    private JScrollPane utentiListaPane;
    private JList utentiPrenotatiList;
    private JLabel nuovePrenotazioniLabel;
    private JScrollPane nuovePrenotazioniPane;
    private JList nuovePrenotazioniList;
    private JButton siCBtn;
    private JButton noCBtn;
    private JButton indietroButton;
    private JLabel confermaPrenotazioneLabel;
    private JLabel eliminaPrenotazioneLabel;
    private JButton siEBtn;
    private JButton noEBtn;
    private JFrame frame;
    private Controller controller;

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Instantiates a new Prenotazioni utenti.
     *
     * @param frameC     the frame c
     * @param controller the controller
     */
    public PrenotazioniUtenti(JFrame frameC, Controller controller)
    {
        this.controller = controller;

        frame = new JFrame("Prenotazioni utenti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(1200, 600);
        confermaPrenotazioneLabel.setVisible(false);

        eliminaPrenotazioneLabel.setVisible(false);
        siCBtn.setVisible(false);
        noCBtn.setVisible(false);
        siEBtn.setVisible(false);
        noEBtn.setVisible(false);
        ArrayList<String> prenotazioniNuove = controller.getPrenotazioniUtenti(true);
        ArrayList<String> prenotazioniUtenti = controller.getPrenotazioniUtenti(false);

        DefaultListModel<String> listaNuove = new DefaultListModel<>();
        aggiornaLista(false,utentiPrenotatiList);
        aggiornaLista(true,nuovePrenotazioniList);
        nuovePrenotazioniList.setCellRenderer(coloraPrenotazione);
        utentiPrenotatiList.setCellRenderer(coloraPrenotazione);


        nuovePrenotazioniList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!nuovePrenotazioniList.getSelectedValuesList().isEmpty())
                {
                    confermaPrenotazioneLabel.setVisible(true);
                    siCBtn.setVisible(true);
                    noCBtn.setVisible(true);
                }
            }
        });

        utentiPrenotatiList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!utentiPrenotatiList.getSelectedValuesList().isEmpty())
                {
                eliminaPrenotazioneLabel.setVisible(true);
                siEBtn.setVisible(true);
                noEBtn.setVisible(true);
                }
            }
        });



        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameC.setVisible(true);

            }
        });
        siCBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selezionato = nuovePrenotazioniList.getSelectedValuesList().toString();
                int inizio = selezionato.indexOf("numero:'") + "numero:'".length();
                int fine = selezionato.indexOf("'", inizio);
                String prenotazioneNuova = selezionato.substring(inizio, fine);
                System.out.println(prenotazioneNuova);
                controller.confermaPrenotazione(true,prenotazioneNuova);
                JOptionPane.showMessageDialog(frame,"Prenotazione confermata correttamente","Prenotazione confermata",JOptionPane.INFORMATION_MESSAGE);
                aggiornaLista(true,nuovePrenotazioniList);
                aggiornaLista(false,utentiPrenotatiList);
                confermaPrenotazioneLabel.setVisible(false);
                siCBtn.setVisible(false);
                noCBtn.setVisible(false);



            }

        });
        noCBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confermaPrenotazioneLabel.setVisible(false);
                siCBtn.setVisible(false);
                noCBtn.setVisible(false);

                String selezionato = nuovePrenotazioniList.getSelectedValuesList().toString();
                int inizio = selezionato.indexOf("numero:'") + "numero:'".length();
                int fine = selezionato.indexOf("'", inizio);
                String prenotazioneNuova = selezionato.substring(inizio, fine);
                controller.confermaPrenotazione(false, prenotazioneNuova);
                JOptionPane.showMessageDialog(frame,"Prenotazione eliminata correttamente","Prenotazione eliminata",JOptionPane.INFORMATION_MESSAGE);
                aggiornaLista(true,nuovePrenotazioniList);
                aggiornaLista(false,utentiPrenotatiList);
                nuovePrenotazioniList.setCellRenderer(coloraPrenotazione);
                utentiPrenotatiList.setCellRenderer(coloraPrenotazione);
                nuovePrenotazioniList.clearSelection();

            }
        });
        siEBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selezionato = utentiPrenotatiList.getSelectedValuesList().toString();
                int inizio = selezionato.indexOf("numero:'") + "numero:'".length();
                int fine = selezionato.indexOf("'", inizio);
                String prenotazioneUtente = selezionato.substring(inizio, fine);
                controller.confermaPrenotazione(false,prenotazioneUtente);
                JOptionPane.showMessageDialog(frame,"Prenotazione eliminata correttamente","Prenotazione eliminata",JOptionPane.INFORMATION_MESSAGE);
                aggiornaLista(true,nuovePrenotazioniList);
                aggiornaLista(false,utentiPrenotatiList);
                nuovePrenotazioniList.setCellRenderer(coloraPrenotazione);
                utentiPrenotatiList.setCellRenderer(coloraPrenotazione);
                confermaPrenotazioneLabel.setVisible(false);
                siCBtn.setVisible(false);
                noCBtn.setVisible(false);
            }
        });
        noEBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminaPrenotazioneLabel.setVisible(false);
                siEBtn.setVisible(false);
                noEBtn.setVisible(false);
                utentiPrenotatiList.clearSelection();


            }
        });



    }

    /**
     * Aggiorna lista.
     *<p>
     * Aggiorna le liste della gui.
     * @param isNuove  serve a selezionare le prenotazioni nuove a quelle degli utenti già prenotati.
     * @param listaGUI the lista gui
     */
    public void aggiornaLista( boolean isNuove,JList listaGUI)
    {
        ArrayList<String> tipoPrenotazioni = controller.getPrenotazioniUtenti(isNuove);

        DefaultListModel<String> listaUtenti = new DefaultListModel<>();
        for (String prenotazioneStr : tipoPrenotazioni)
        {
            listaUtenti.addElement(prenotazioneStr);
        }
        listaGUI.setModel(listaUtenti);
    }

    /**
     * The Colora prenotazione.
     * <p>
     * Colora la prenotazione in base allo stato.
     *
     */
    public DefaultListCellRenderer coloraPrenotazione = new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String testo = value.toString();

            String prefix = "stato: '";
            int start = testo.indexOf(prefix);
            String stato = "";
            if (start != -1) {
                int inizio = start + prefix.length();
                int end = testo.indexOf("'", inizio);
                if (end != -1) {
                    stato = testo.substring(inizio, end);
                }
            }

            Color bgColor = list.getBackground();
            Color fgColor = list.getForeground();

            switch (stato) {
                case "Cancellata":
                    bgColor = Color.RED;
                    fgColor = Color.WHITE;
                    break;
                case "Confermata":
                    bgColor = Color.GREEN;
                    fgColor = Color.BLACK;
                    break;
                case "In_Attesa":
                    bgColor = Color.ORANGE;
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

            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return label;
        }
    };





}
