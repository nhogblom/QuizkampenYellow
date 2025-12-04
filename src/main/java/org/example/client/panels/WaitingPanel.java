package org.example.client.panels;

import org.example.shared.Message;
import org.example.shared.MessageTypes;
import org.example.client.ClientBackpack;
import org.example.client.NetworkClient;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * WaitingPanel is shown after entering a username.
 * <p>
 * It connects to the server, shows connection status,
 * and waits until NetworkClient sets goToNextScreen=true
 * (triggered by MATCH_STARTED).
 * <p>
 * When the match starts, it creates QuestionPanel,
 * registers it as the active frame in ClientBackpack,
 * and switches from this panel to QuestionPanel.
 */
public class WaitingPanel extends JFrame {

    private JLabel connectingLabel;
    private final ClientBackpack backpack;
    private NetworkClient client;

    public WaitingPanel(ClientBackpack backpack) {
        super("Quizkampen - Waiting");

        this.backpack = backpack;

        //  frame setup
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();

        // Register this frame as active so ClientBackpack knows where we are
        backpack.setActiveJframe(this);

        // Create client + connect to server && if client already is set get client from backpack.
        if (client == null) {
            this.client = new NetworkClient(backpack);
            if (client.connect()) {
                // Connection OK then show message and then wait for MATCH_STARTED from server
                connectingLabel.setText("<html><h1>" + "Connected as " + backpack.getUsername() + " waiting for opponent..." + "<html><h1>");
            } else {
                connectingLabel.setText("Connection failed");
            }
        } else {
            ///  send message to server playagain.
            this.client = new NetworkClient(backpack);
            client.sendMessage(new Message(MessageTypes.PLAYAGAIN,backpack.getUsername()));
        }


        waitForServerStartSignal();
    }

    /**
     * This method starts a thread that continuously checks the flag
     * goToNextScreen in ClientBackpack.
     * <p>
     * NetworkClient sets goToNextScreen(true) when it receives MATCH_STARTED
     * (or DEVELOPMENTMSG). When that happens, we switch to
     * QuestionPanel on the Swing EDT (via SwingUtilities.invokeLater).
     */
    private void waitForServerStartSignal() {
        new Thread(() -> {
            while (!backpack.isGoToNextScreen()) {
                try {
                    Thread.sleep(100); // avoid busy waiting
                } catch (InterruptedException ignored) {
                }
            }

            SwingUtilities.invokeLater(() -> {
                // Optional: update text briefly before switching
                connectingLabel.setText("Opponent found! Starting game...");

                // Register new active frame in backpack
                backpack.setActiveJframe(backpack.getCategoryPanel());

                // Close the waiting window and show the question window
                this.dispose();

                backpack.getCategoryPanel().setVisible(true);

                // Reset flag so it can be reused if needed later
                backpack.setGoToNextScreen(false);
            });

        }).start();
    }

    /**
     * Creates and places all GUI components for the waiting screen.
     */
    private void addGuiComponents() {
        connectingLabel = new JLabel("");
        connectingLabel.setFont(new Font("Arial", Font.BOLD, 36));
        connectingLabel.setBounds(120, 120, 380, 200);
        connectingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectingLabel.setForeground(Color.DARK_GRAY);
        add(connectingLabel);

        JLabel title = new JLabel("Waiting for opponent");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBounds(50, 300, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.DARK_GRAY);
        add(title);

        URL loadingUrl = getClass().getResource("/loading-96.gif");
        ImageIcon loading = new ImageIcon(loadingUrl);
        loading.setImage(loading.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        JLabel loadingLabel = new JLabel(loading);
        loadingLabel.setBounds(150, 300, 300, 300);
        add(loadingLabel);
    }
}