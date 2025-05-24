package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestisciPrenotazioni {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JLabel searchLabel;
    private JButton searchBtn;
    private JButton cancelBtn;
    private JTextField searchField;

    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }


    public GestisciPrenotazioni(Frame frameC, Controller controller) {
        frame = new JFrame("Amministratore");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 370);
        frame.setVisible(true);


        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameC.setVisible(true);
            }
        });
    }
}
