package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Cerca passeggero.
 */
public class CercaPasseggero {

    private JPanel mainPanel;
    private JPanel searchPanel;
    private JLabel nomeLabel;
    private JScrollPane listScroll;
    private JList passeggeriList;
    private JLabel cercaPasseggeroLabel;
    private JTextField nomeField;
    private JLabel cognomeLabel;
    private JTextField cognomeField;
    private JLabel cfLabel;
    private JTextField cfField;
    private JButton cercaBtn;
    private JButton indietroBtn;
    private Controller controller;
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
     * Instantiates a new Cerca passeggero.
     *
     * @param frameC     the frame c
     * @param controller the controller
     */
    public CercaPasseggero(JFrame frameC, Controller controller) {

        this.controller = controller;
        frame = new JFrame("Cerca Passeggero");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200, 370);
        frame.setVisible(true);
        ArrayList<String> model = controller.listaPasseggero();

        passeggeriList.setModel(creaLista(model));


        cercaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String cf = cfField.getText();
                ArrayList<String> model;
                if (nome.isEmpty() && cognome.isEmpty() && cf.isEmpty()) {

                    model = controller.listaPasseggero();
                    passeggeriList.setModel(creaLista(model));
                    return;
                }
                try {
                    model = controller.listaPasseggeroFiltrato(nome, cognome, cf);
                    passeggeriList.setModel(creaLista(model));
                } catch (IllegalArgumentException ex) {
                   JOptionPane.showMessageDialog(frame,ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        indietroBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameC.setVisible(true);
                frame.dispose();

            }
        });


    }

    /**
     * Crea lista default list model.
     *
     * @param listaPasseggeri the lista passeggeri
     * @return the default list model
     */
    public DefaultListModel<String> creaLista(ArrayList<String> listaPasseggeri) {
        DefaultListModel<String> lista = new DefaultListModel<>();
        for (String passeggero : listaPasseggeri) {
            lista.addElement(passeggero);
        }
        return lista;
    }
}
