package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdminPage
{
    private JPanel mainPanel;
    private JButton insertFlyBtn;
    private JLabel welcomeLabel;
    private JButton logoutBtn;
    private JButton utentePrenotazioniBtn;
    private JScrollPane partenzaVoliScroll;
    private JList arriviVoliList;
    private JScrollPane arriviVoliScroll;
    private JLabel voliPartenzaLabel;
    private JLabel voliArriviNapoli;
    private JPanel btnPanel;
    private JTable partenzaVoliTable;
    private JTable arriviVoliTable;
    private JButton modifcaVoloBtn;
    private JList partenzaVoliLista;
    private JFrame frame;
    private Controller controller;

    public JFrame getFrame() {
        return frame;
    }

    public AdminPage(JFrame frameHome, Controller controller) {
        this.controller = controller;
        frame = new JFrame("Amministratore");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1600, 600);
        frame.setVisible(true);
        String[] colonnePartenza = {"Codice", "Compagnia","Destinazione" , "Data", "Partenza", "Arrivo","Posti","Ritardo","Stato","Gate"};
        String[] colonneArrivo = {"Codice","Compagnia","Origine","Data","Partenza","Arrivo","Posti","Ritardo","Stato"};
        aggiornaTabella(colonnePartenza,partenzaVoliTable,true);
        aggiornaTabella(colonneArrivo,arriviVoliTable,false);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {

                aggiornaTabella(colonnePartenza, partenzaVoliTable, true);
                aggiornaTabella(colonneArrivo, arriviVoliTable, false);

            }
        });
        insertFlyBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                InserisciVolo voloForm = new InserisciVolo(frame,controller);
                voloForm.getFrame().setVisible(true);

            }
        });
        utentePrenotazioniBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                PrenotazioniUtenti prenotazioniForm = new PrenotazioniUtenti(frame,controller);
                prenotazioniForm.getFrame().setVisible(true);

            }
        });
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameHome.setVisible(true);
                frame.dispose();
            }
        });
        modifcaVoloBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ModifcaVoli modifcaVoli = new ModifcaVoli(controller,frame);
                modifcaVoli.getFrame().setVisible(true);
            }
        });


    }
    public void aggiornaTabella(String[] colonne, JTable table,boolean isPartenza) {

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
