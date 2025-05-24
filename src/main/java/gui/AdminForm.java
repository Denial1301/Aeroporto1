package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminForm
{
    private JPanel mainPanel;
    private JButton insertFlyBtn;
    private JLabel welcomeLabel;
    private JButton logoutBtn;
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
        insertFlyBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                InserisciVoloForm voloForm = new InserisciVoloForm(frame,controller);
                voloForm.getFrame().setVisible(true);

            }
        });
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameHome.setVisible(true);
            }
        });

    }
}
