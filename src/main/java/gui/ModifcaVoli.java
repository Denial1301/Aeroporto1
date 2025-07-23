package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * The type Modifca voli.
 */
public class ModifcaVoli {
    private JPanel mainPanel;
    private JButton confermaModificheBtn;
    private JPanel modificaPanel;
    private JLabel compagniaLabel;
    private JTextField compagniaField;
    private JLabel dataLabel;
    private JTextField dataField;
    private JLabel oraPartenzaLabel;
    private JLabel oraArrivoLabel;
    private JTextField arrivoField;
    private JComboBox statoBox;
    private JLabel statoLabel;
    private JLabel gateLabel;
    private JComboBox gateBox;
    private JLabel voliPartenzaLabel;
    private JScrollPane voliPartenzaScroll;
    private JList voliPartenzaList;
    private JLabel voliArrivoLabel;
    private JList voliArrivoList;
    private JScrollPane voliArrivoScroll;
    private JTextField partenzaField;
    private JLabel ritardoLabel;
    private JTextField ritardoField;
    private JButton indietroBtn;
    private JFrame frame;
    private Controller controller;
    private String codice;
    private boolean isPartenza;

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Instantiates a new Modifca voli.
     *
     * @param controller the controller
     * @param frameC     the frame c
     */
    public ModifcaVoli(Controller controller, JFrame frameC)
    {
        this.controller = controller;
        frame = new JFrame("Modifca voli");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(1800, 600);
        modificaPanel.setVisible(false);
        ArrayList<String> partenza = new ArrayList<>();
        ArrayList<String> arrivo = new ArrayList<>();
        ArrayList<String> gate = new ArrayList<>();
        controller.voliPrenotabili(partenza,arrivo);
        controller.getGate(gate);
        statoBox.addItem("Programmato");
        statoBox.addItem("Cancellato");
        statoBox.setSelectedIndex(0);
        gateBox.addItem("-");





        DefaultListModel<String> listaPartenza = creaLista(partenza);
        DefaultListModel<String> listaArrivo = new DefaultListModel<>();
        for(int i = 0; i < gate.size(); i++)
        {
            if(gate.get(i).startsWith("NAP"))
                continue;
            gateBox.addItem(gate.get(i));
        }
        ArrayList<String> listaVoliPartenza = new ArrayList<>();
        ArrayList<String> listaVoliArrivo = new ArrayList<>();
        controller.voliPrenotabili(listaVoliPartenza,listaVoliArrivo);
        voliPartenzaList.setModel(creaLista(listaVoliPartenza));
        voliPartenzaList.setCellRenderer(coloraRiga);
        voliArrivoList.setModel(creaLista(listaVoliArrivo));
        voliArrivoList.setCellRenderer(coloraRiga);
        voliPartenzaList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(voliPartenzaList.getSelectedIndex() != -1)
                {

                    String selected = (String) voliPartenzaList.getSelectedValue();
                    String[] campi = selected.split(",");
                    codice = campi[0].split("=")[1].replace("'", "").trim();

                    String compagnia = campi[1].split("=")[1].replace("'", "").trim();
                    String data = campi[2].split("=")[1].replace("'", "").trim();
                    String oraPartenza = campi[4].split("=")[1].replace("'", "").trim();
                    String oraArrivo = campi[5].split("=")[1].replace("'", "").trim();
                    String gate = campi[6].split("=")[1].replace(".", "").trim();
                    isPartenza = true;



                    compagniaField.setText(compagnia);
                    dataField.setText(data);
                    arrivoField.setText(oraArrivo);
                    partenzaField.setText(oraPartenza);
                    ritardoField.setText("0");
                    for(int i = 0 ; i < gateBox.getItemCount(); i++)
                    {
                        String gateStr = gateBox.getItemAt(i).toString();
                        if(gateStr.startsWith(gate))
                        {
                            gateBox.setSelectedItem(gateStr);
                            break;
                        }
                    }

                    modificaPanel.setVisible(true);
                    gateLabel.setVisible(true);
                    gateBox.setVisible(true);
                    voliArrivoList.clearSelection();
                }
            }
        });
        voliArrivoList.addListSelectionListener(new ListSelectionListener() {
            @Override

            public void valueChanged(ListSelectionEvent e) {
                if(voliArrivoList.getSelectedIndex() != -1)
                {
                    String selected = (String) voliArrivoList.getSelectedValue();
                    String[] campi = selected.split(",");
                    codice = campi[0].split("=")[1].replace("'", "").trim();
                    String compagnia = campi[1].split("=")[1].replace("'", "").trim();
                    String data = campi[2].split("=")[1].replace("'", "").trim();
                    String oraPartenza = campi[4].split("=")[1].replace("'", "").trim();
                    String oraArrivo = campi[5].split("=")[1].replace(".", "").trim();
                    isPartenza = false;
                    gateBox.setSelectedItem(0);



                    compagniaField.setText(compagnia);
                    dataField.setText(data);
                    arrivoField.setText(oraArrivo);
                    partenzaField.setText(oraPartenza);
                    ritardoField.setText("0");



                    modificaPanel.setVisible(true);
                    gateBox.setVisible(false);
                    gateLabel.setVisible(false);
                    voliPartenzaList.clearSelection();

                }
            }
        });

        confermaModificheBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime arrivo = LocalTime.parse(arrivoField.getText(), timeFormatter);
                    LocalTime partenza = LocalTime.parse(partenzaField.getText(), timeFormatter);
                    LocalDate data = LocalDate.parse(dataField.getText(),dateFormatter);
                    int ritardo = Integer.parseInt(ritardoField.getText());
                    String selectedGate = gateBox.getSelectedItem().toString();
                    String gate = selectedGate.substring(0,selectedGate.indexOf('(')) ;
                    String stato = statoBox.getSelectedItem().toString();
                    controller.updateVolo(isPartenza,codice, compagniaField.getText(),data,arrivo,partenza,stato,ritardo,gate);
                    JOptionPane.showMessageDialog(frameC, "Volo aggiornato con successo","Info",JOptionPane.INFORMATION_MESSAGE);
                    ArrayList<String> listaVoliPartenza = new ArrayList<>();
                    ArrayList<String> listaVoliArrivo = new ArrayList<>();
                    controller.voliPrenotabili(listaVoliPartenza,listaVoliArrivo);
                    voliPartenzaList.setModel(creaLista(listaVoliPartenza));
                    voliPartenzaList.setCellRenderer(coloraRiga);
                    voliArrivoList.setModel(creaLista(listaVoliArrivo));
                    voliArrivoList.setCellRenderer(coloraRiga);



                }catch(DateTimeParseException ex)
                {
                    JOptionPane.showMessageDialog(frame,"Formato data o ora non valido.","Warning",JOptionPane.WARNING_MESSAGE);
                }



            }
        });
        indietroBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameC.setVisible(true);
            }
        });


    }

    /**
     * The Colora riga.
     * Colora la riga del volo in base allo stato del volo.
     */
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
                    bgColor = new Color(0, 128, 0);
                    fgColor = Color.WHITE;
                    break;
                case "In_Ritardo":
                    bgColor = Color.ORANGE;
                    fgColor = Color.BLACK;
                    break;
                case "Decollato":
                    bgColor = new Color(138, 43, 226);
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
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            return label;
        }
    };

    /**
     * Crea lista default list model.
     *
     * @param listaVoli the lista voli
     * @return the default list model
     */
    public DefaultListModel<String> creaLista(ArrayList<String> listaVoli) {
        DefaultListModel<String> lista = new DefaultListModel<>();
        for (String volo : listaVoli) {
            lista.addElement(volo);
        }
        return lista;
    }



}
