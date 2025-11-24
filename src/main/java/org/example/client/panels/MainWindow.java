package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{
    public MainWindow() {
        super("Quizkampen");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1000, 800);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel title = new JLabel("Quizkampen");
        title.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        title.setBounds(300, 20, 400, 43);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Constants.LIGHT_YELLOW);
        add(title);

        JTextField Username = new JTextField("");
        Username.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        Username.setBounds(300, 250, 400, 43);
        Username.setHorizontalAlignment(SwingConstants.CENTER);
        Username.setForeground(Color.BLACK);
        add(Username);
    }
}

