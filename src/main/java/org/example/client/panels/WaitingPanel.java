package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JFrame{

    private String username;

    public WaitingPanel(String username) {

        this.username = username;
        super("");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel connectingLabel = new JLabel("Connected");
        connectingLabel.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        connectingLabel.setBounds(100, 50, 400, 43);
        connectingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(connectingLabel);
        JLabel title = new JLabel("Waiting for opponent...");
        title.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        title.setBounds(100, 300, 400, 43);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(title);
    }
    //
    // TODO användaren får info om att  anslutningen är etableradd och att motspelare inväntas.
}
