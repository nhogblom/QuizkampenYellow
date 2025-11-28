package org.example.client.panels;

import org.example.client.Flag;
import org.example.client.NetworkClient;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JFrame {
    private JLabel connectingLabel;

    private String username;
    private Flag moveToNextUI;

    public WaitingPanel(String username, Flag moveToNextUI) {
        this.username = username;
        super("");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();

        // connection is established to server.
        NetworkClient client = new NetworkClient(username, this, moveToNextUI);
        if (client.connect()) {
            connectingLabel.setText("Connected to " + username);
        }
        moveOnToNextUI();

    }

    public void moveOnToNextUI() {
        new Thread(() -> {
            while (!moveToNextUI.isFlag()) {
                try {
                    Thread.sleep(2000);
                    System.out.println(moveToNextUI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectingLabel.setText("MATCH STARTAD!");

        }).start();
    }

    private void addGuiComponents() {
        connectingLabel = new JLabel("");
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
    // TODO användaren får info om att  anslutningen är etablerad och att motspelare inväntas.
}
