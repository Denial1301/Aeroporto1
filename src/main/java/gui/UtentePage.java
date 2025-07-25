package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;


public class UtentePage {
    private JPanel mainPanel;
    private JLabel welcomeLabel;

    private JButton prenotaButton;
    private JPanel btnPanel;
    private JButton gestisciPrenotazioneButton;
    private JButton logoutBtn;
    private JTable departuresTable;

    private JScrollPane departuresScroll;
    private JScrollPane arrivalsScroll;
    private JTable arrivalsTable;
    private JLabel departuresLabel;
    private JLabel arrivalsLabel;
    private JLabel bookedFlyLabel;
    private JScrollPane bookedFlyScroll;
    private JList bookedFlyList;
    private JButton cercaPasseggeroBtn;
    private JButton updateBtn;
    private JFrame frame;



    public JFrame getFrame() {
        return frame;
    }

    public UtentePage(JFrame frameC, Controller controller)
    {

    frame = new JFrame("Pagina Utente");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(mainPanel);
    frame.pack();
    frame.setSize(1200, 600);


        String[] colonnePartenza = {"Codice", "Compagnia","Destinazione" , "Data", "Partenza", "Arrivo","Posti","Ritardo","Stato","Gate"};
        String[] colonneArrivo = {"Codice","Compagnia","Origine","Data","Partenza","Arrivo","Posti","Ritardo","Stato"};


        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {

                aggiornaTabella(colonnePartenza, departuresTable, true,controller);
                aggiornaTabella(colonneArrivo, arrivalsTable, false,controller);


                ArrayList<String> voliPrenotati = controller.getVoliPrenotati();
                DefaultListModel<String> lista = new DefaultListModel<>();
                for (String voloStr : voliPrenotati) {
                    lista.addElement(voloStr);
                }
                bookedFlyList.setModel(lista);
            }
        });

        bookedFlyLabel.setText("Voli prenotati dall'utente "+ controller.getEmailLogin());
        
        logoutBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frameC.setVisible(true);
            frame.dispose();

        }
    });
    prenotaButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            Prenota prenota = new Prenota(frame,controller);
            prenota.getFrame().setVisible(true);
        }
    });
    gestisciPrenotazioneButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            GestisciPrenotazioni gestisciPrenotazioni = new GestisciPrenotazioni(frame, controller);
            gestisciPrenotazioni.getFrame().setVisible(true);

        }
    });
    updateBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        controller.eliminaVoliScaduti();
        aggiornaTabella(colonnePartenza,departuresTable,true,controller);
        aggiornaTabella(colonneArrivo,arrivalsTable,false,controller);

        }
    });
    cercaPasseggeroBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            CercaPasseggero cercaPasseggero = new CercaPasseggero(frame, controller);
            cercaPasseggero.getFrame().setVisible(true);

        }
    });



    }

    public void aggiornaTabella(String[] colonne, JTable table,boolean isPartenza,Controller controller) {

        String[][] datiPartenza = controller.getDatiVoliPerGUI(colonne,isPartenza);
        DefaultTableModel model = new DefaultTableModel(datiPartenza, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setModel(model);
        TableCellRenderer ritardo = coloraRitardo();
        TableCellRenderer stato = coloraColonna();
        table.getColumnModel().getColumn(7).setCellRenderer(ritardo);
        table.getColumnModel().getColumn(8).setCellRenderer(stato);







    }

    public TableCellRenderer coloraRitardo() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String testo = value.toString();
                int ritardo = Integer.parseInt(testo.split(" ")[0]);



                if (ritardo == 0)
                {
                    c.setBackground(Color.WHITE);
                } else if (ritardo <= 30)
                {
                    c.setBackground(Color.ORANGE);
                } else
                {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                }



                return c;
            }
        };
    }

    public TableCellRenderer coloraColonna() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String stato = value.toString();

                switch (stato) {
                    case "In_Ritardo":
                        c.setBackground(Color.ORANGE);
                        c.setForeground(Color.BLACK);
                        break;
                    case "Cancellato":
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                        break;
                    case "Decollato":
                        c.setBackground(Color.CYAN);
                        c.setForeground(Color.BLACK);
                        break;
                    case "Programmato":
                        c.setBackground(Color.BLUE);
                        c.setForeground(Color.WHITE);
                        break;
                    case "Atterrato":
                        c.setBackground(Color.GREEN);
                        break;
                }

                return c;
            }
        };
    }





}








