package org.example.client.panels;

import org.example.client.ClientBackpack;
import org.example.client.NetworkClient;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JFrame {

    private JLabel connectingLabel;
    private final ClientBackpack backpack;
    private final NetworkClient client;

    public WaitingPanel(ClientBackpack backpack) {
        super("Quizkampen - Waiting");

        this.backpack = backpack;

        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();

        // Create client + connect
        backpack.setActiveJframe(this);
        this.client = new NetworkClient(backpack.getUsername(), backpack);
        if (client.connect()) {
            connectingLabel.setText("Connected as " + backpack.getUsername()+" waiting for opponent.");
        } else {
            connectingLabel.setText("Connection failed");
        }

        waitForServerStartSignal();
    }

    private void waitForServerStartSignal() {
        new Thread(() -> {
            while (!backpack.isGoToNextScreen()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }

            SwingUtilities.invokeLater(() -> {
                // Build QuestionPanel with listener

                QuestionPanel questionPanel = new QuestionPanel(backpack,optionIndex -> client.sendAnswer(optionIndex));

                backpack.setActiveJframe(questionPanel);

                this.dispose();
                questionPanel.setVisible(true);

                backpack.setGoToNextScreen(false);
            });

        }).start();
    }

    private void addGuiComponents() {
        connectingLabel = new JLabel("");
        connectingLabel.setFont(new Font("Arial", Font.BOLD, 36));
        connectingLabel.setBounds(100, 50, 400, 43);
        connectingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(connectingLabel);

        JLabel title = new JLabel("Waiting for opponent...");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBounds(100, 300, 400, 43);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
    }
}
