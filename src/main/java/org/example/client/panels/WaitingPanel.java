package org.example.client.panels;

import org.example.client.Flag;
import org.example.client.NetworkClient;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JFrame {

    private JLabel connectingLabel;
    private final String username;
    private final Flag moveToNextUI;
    private NetworkClient client;

    public WaitingPanel(String username, Flag moveToNextUI) {
        super("Quizkampen - Waiting");

        this.username = username;
        this.moveToNextUI = moveToNextUI;

        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();

        // Create client + connect
        this.client = new NetworkClient(username, this, moveToNextUI);
        if (client.connect()) {
            connectingLabel.setText("Connected as " + username);
        } else {
            connectingLabel.setText("Connection failed");
        }

        waitForServerStartSignal();
    }

    private void waitForServerStartSignal() {
        new Thread(() -> {
            while (!moveToNextUI.isFlag()) {
                try { Thread.sleep(100); }
                catch (InterruptedException ignored) {}
            }

            SwingUtilities.invokeLater(() -> {
                // Build QuestionPanel with listener
                QuestionPanel questionPanel = new QuestionPanel(
                        optionIndex -> client.sendAnswer(optionIndex)
                );

                client.setActiveJframe(questionPanel);

                this.dispose();
                questionPanel.setVisible(true);

                moveToNextUI.setFlag(false);
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
