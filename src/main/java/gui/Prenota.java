package gui;

import controller.Controller;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Prenota {
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JLabel passegerNLabel;
    private JTextField passegerNField;
    private JLabel passegerCLabel;
    private JTextField passegerCField;
    private JLabel cfLabel;
    private JTextField cfField;
    private JLabel documentTypeLabel;
    private JComboBox documentTypeBox;
    private JLabel dateDeadlineLabel;
    private JTextField dateDeadlineField;
    private JLabel emmisionDateLabel;
    private JTextField emmisionDateField;
    private JList voliPartenzaList;
    private JButton cancelBtn;
    private JButton confirmBtn;
    private JPanel btnPanel;
    private JLabel numDocumentLabel;
    private JTextField numDocumentField;
    private JLabel birthDateLabel;
    private JTextField birthDateField;
    private JLabel voliPartenzaLabel;
    private JList voliArrivoList;
    private JLabel voliArrivo;
    private JScrollPane voliPartenzaScroll;
    private JScrollPane voliArrivoScroll;
    private JLabel cercaVoloLabel;
    private JPanel cercaVoloPanel;
    private JComboBox statoBox;
    private JLabel cercaStatoLabel;
    private JLabel cercaCodiceLabel;
    private JTextField cercaCodiceField;
    private JLabel cercaCompagniaLabel;
    private JTextField cercaCompagniaField;
    private JButton trovaBtn;
    private JLabel stato;
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public Prenota(JFrame frameC, Controller controller) {

        frame = new JFrame("Prenotazione");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(1800, 600);
        statoBox.addItem("-");
        statoBox.addItem("Programmato");
        statoBox.addItem("In_Ritardo");
        statoBox.addItem("Decollato");
        statoBox.addItem("Cancellato");
        statoBox.setSelectedIndex(0);
        
        documentTypeBox.addItem("Carta d'identità");
        documentTypeBox.addItem("Passaporto");
        documentTypeBox.setSelectedIndex(-1);

        ArrayList<String> listaVoliPartenza = new ArrayList<>();
        ArrayList<String> listaVoliArrivo = new ArrayList<>();
        controller.voliPrenotabili(listaVoliPartenza,listaVoliArrivo);
        voliPartenzaList.setModel(creaLista(listaVoliPartenza));
        voliPartenzaList.setCellRenderer(coloraRiga);
        voliArrivoList.setModel(creaLista(listaVoliArrivo));
        voliArrivoList.setCellRenderer(coloraRiga);

        voliArrivoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(voliArrivoList.getSelectedIndex() != -1)
                {
                    voliPartenzaList.clearSelection();
                }
            }
        });


        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameC.setVisible(true);
                frame.dispose();

            }
        });
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {

                try{
                    String passegerN = passegerNField.getText();
                    String passegerC = passegerCField.getText();
                    String cf = cfField.getText();
                    String dateDeadline = dateDeadlineField.getText();
                    String emmisionDate = emmisionDateField.getText();
                    String tipo = (String) documentTypeBox.getSelectedItem();
                    String numDocumento = numDocumentField.getText();
                    String dataNascita = birthDateField.getText();
                    String selezionato = (String) voliPartenzaList.getSelectedValue();

                    if(selezionato == null)
                    {
                        selezionato = (String) voliArrivoList.getSelectedValue();
                    }

                    int inizio = selezionato.indexOf("codice='") + "codice='".length();
                    int fine = selezionato.indexOf("'", inizio);
                    String codice = selezionato.substring(inizio, fine);
                    String[] campi = {passegerN, passegerC, cf, dateDeadline, emmisionDate, tipo,numDocumento,dataNascita,codice};
                    if(controller.isVuoto(campi))
                    {
                        JOptionPane.showMessageDialog(frame,"Compilare tutti i campi.","Campi vuoti",JOptionPane.WARNING_MESSAGE);
                    } else {
                        controller.addDocumento(numDocumento,tipo,emmisionDate,dateDeadline);
                        controller.addPasseggero(passegerN,passegerC,numDocumento,cf, dataNascita);
                        controller.addPrenotazione(cf,codice);
                        JOptionPane.showMessageDialog(frame,"Prenotazione effettuata","Prenotazione",JOptionPane.INFORMATION_MESSAGE);
                        frameC.setVisible(true);
                        frame.setVisible(false);

                    }
                }catch(DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame,"Formato della data non valida.","Formato data errato",JOptionPane.WARNING_MESSAGE);

                } catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(frame,"Selezionare un volo dalla lista.","Volo non selezionato", JOptionPane.WARNING_MESSAGE);
                }catch(SQLException ex) {
                    JOptionPane.showMessageDialog(frame,ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
                }


            }
        });



        passegerNField.addKeyListener(new KeyAdapter() { //VA ELIMINATA QUANDO FACCIAMO VEDERE AL PROF.
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    passegerNField.setText("Daniele");
                    passegerCField.setText("Guardascione");
                    cfField.setText("GRDDNL06A13F839H");
                    birthDateField.setText("13/01/2006");
                    dateDeadlineField.setText("13/01/2028");
                    emmisionDateField.setText("03/04/2022");
                    documentTypeBox.setSelectedIndex(0);
                    numDocumentField.setText("CL2");

                }
            }
        });
        trovaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codice = cercaCodiceField.getText();
                    String compagnia = cercaCompagniaField.getText();
                    String stato = statoBox.getSelectedItem().toString();
                    listaVoliArrivo.clear();
                    listaVoliPartenza.clear();
                    if(codice.isEmpty() && compagnia.isEmpty() && stato.equals("-")) {

                        controller.voliPrenotabili(listaVoliPartenza,listaVoliArrivo);
                        voliPartenzaList.setModel(creaLista(listaVoliPartenza));
                        voliArrivoList.setModel(creaLista(listaVoliArrivo));
                        return;
                    }

                    controller.cercaVoli(listaVoliPartenza,listaVoliArrivo,codice,compagnia,stato);
                    voliArrivoList.setModel(creaLista(listaVoliArrivo));
                    voliArrivoList.setCellRenderer(coloraRiga);
                    voliPartenzaList.setModel(creaLista(listaVoliPartenza));
                    voliPartenzaList.setCellRenderer(coloraRiga);
                    cercaCodiceField.setText("");
                    cercaCompagniaField.setText("");
                    statoBox.setSelectedIndex(0);
                    
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });



    }
    public DefaultListModel<String> creaLista(ArrayList<String> listaVoli) {
        DefaultListModel<String> lista = new DefaultListModel<>();
        for (String volo : listaVoli) {
            lista.addElement(volo);
        }
        return lista;
    }
    public DefaultListCellRenderer coloraRiga = new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String testo = value.toString();


            String prefix = "Stato=";
            int start = testo.indexOf(prefix);
            String stato = "";
            if (start != -1) {
                int inizio = start + prefix.length();
                int end = testo.indexOf('.', inizio);
                if (end == -1) end = testo.length();
                stato = testo.substring(inizio, end);
            }


            Color bgColor = list.getBackground();
            Color fgColor = list.getForeground();

            switch (stato) {
                case "Cancellato":
                    bgColor = Color.RED;
                    fgColor = Color.WHITE;
                    break;
                case "Programmato":
                    bgColor = Color.BLUE;
                    fgColor = Color.WHITE;
                    break;
                case "In_Ritardo":
                    bgColor = Color.ORANGE;
                    fgColor = Color.BLACK;
                    break;
                case "Decollato":
                    bgColor = Color.CYAN;
                    fgColor = Color.BLACK;
                    break;
                case "Atterrato":
                    bgColor = Color.GREEN;
                    fgColor = Color.BLACK;
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


