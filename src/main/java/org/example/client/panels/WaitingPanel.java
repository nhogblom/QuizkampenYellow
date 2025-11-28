package org.example.client.panels;

import org.example.client.ClientBackpack;
import org.example.client.NetworkClient;

import javax.swing.*;
import java.awt.*;

/**
 * WaitingPanel is the first screen shown after the user enters a username.
 *
 * Responsibilities:
 * 1. Establish a connection to the server through NetworkClient.
 * 2. Display connection status to the user while waiting for a match to start.
 * 3. Listen for a server signal (MATCH_STARTED or DEVELOPMENTMSG)
 * 4. When the server says “match started”, WaitingPanel:
 *      - Creates a QuestionPanel and passes in a listener so the UI can
 *        send chosen answers back to the server through NetworkClient.
 *      - Switches the active UI frame inside NetworkClient so incoming questions
 *        update the correct panel.
 *      - Closes itself and shows the QuestionPanel.
 *
 *
 * NOTE TO TEAM:
 * - moveToNextUI acts as a shared flag updated by NetworkClient when the server
 *   sends MATCH_STARTED. When true, WaitingPanel moves to the next screen.
 * - QuestionPanel now requires a listener (QuestionAnsweredListener) so it remains
 */

public class WaitingPanel extends JFrame {

    private JLabel connectingLabel;
    private final String username;
    private final ClientBackpack moveToNextUI;
    private NetworkClient client;

    public WaitingPanel(String username, ClientBackpack moveToNextUI) {
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
            while (!moveToNextUI.isGoToNextScreen()) {
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

                moveToNextUI.setGoToNextScreen(false);
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
