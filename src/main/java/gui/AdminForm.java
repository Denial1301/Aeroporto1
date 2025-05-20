package gui;

import controller.Controller;

import javax.swing.*;

public class AdminForm {
    private JPanel mainPanel;
    private JButton insertFlyBtn;
    private JButton modifyFlyBtn;
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public AdminForm(JFrame frameHome, Controller controller) {
        frame = new JFrame("Amministratore");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 370);
        frame.setVisible(true);
    }
}
