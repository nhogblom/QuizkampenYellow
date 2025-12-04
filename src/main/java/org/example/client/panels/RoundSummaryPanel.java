package org.example.client.panels;

import org.example.shared.RoundResult;
import org.example.client.ClientBackpack;
import org.example.client.NetworkClient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoundSummaryPanel extends JFrame {

    private ClientBackpack backpack;
    JButton Player1;
    JButton Player2;
    JLabel countDown;
    private JLabel playAgainLabel;

    // Game over UI
    private JButton gameOverBackground;
    private JLabel gameOverLabel;
    private JButton playAgainYes;
    private JButton playAgainNo;

    public RoundSummaryPanel(ClientBackpack backpack) {
        super("");
        this.backpack = backpack;
        backpack.setRoundSummaryPanel(this);
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
//        addAnswerBoxes();
    }

    private void addGuiComponents() {
        UIManager.put("Button.disabledText", Color.WHITE);
        // Player 1 box
        Player1 = new JButton(backpack.getUsername());
        Player1.setFont(new Font("Arial", Font.BOLD, 20));
        Player1.setBounds(100, 150, 170, 50);
        Player1.setBackground(new Color(0, 0, 0, 0.2f));
        Player1.setEnabled(false);
        add(Player1);

        // Player 2 box
        Player2 = new JButton(backpack.getOpponentUsername());
        Player2.setFont(new Font("Arial", Font.BOLD, 20));
        Player2.setBounds(330, 150, 170, 50);
        Player2.setBackground(new Color(0, 0, 0, 0.2f));
        Player2.setEnabled(false);
        add(Player2);

        // Countdown label (used between rounds)
        countDown = new JLabel();
        countDown.setFont(new Font("Arial", Font.BOLD, 24));
        countDown.setForeground(Color.WHITE);
        countDown.setBounds(275, 260, 50, 40); // center-ish
        countDown.setHorizontalAlignment(SwingConstants.CENTER);
        add(countDown);

        // ---- Game over label (initially hidden) ----
        gameOverLabel = new JLabel("");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setBounds(100, 320, 400, 50);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setVisible(false);
        add(gameOverLabel);

        // ---- "Play again?" label (initially hidden) ----
        playAgainLabel = new JLabel("Play again?");
        playAgainLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        playAgainLabel.setBounds(100, 360, 400, 40);
        playAgainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playAgainLabel.setForeground(Color.WHITE);
        playAgainLabel.setVisible(false);
        add(playAgainLabel);

        // ---- "Play again?" buttons (initially hidden) ----
        playAgainYes = new JButton("Yes");
        playAgainYes.setBounds(150, 425, 120, 40);
        playAgainYes.setBackground(Constants.LIGHT_GREEN);
        playAgainYes.setVisible(false);
        add(playAgainYes);

        playAgainNo = new JButton("No");
        playAgainNo.setBounds(330, 425, 120, 40);
        playAgainNo.setBackground(Constants.LIGHT_RED);
        playAgainNo.setVisible(false);
        add(playAgainNo);


        // Yes: disconnect current client and start a new game (new WaitingPanel)
        playAgainYes.addActionListener(e -> {
            // clear chatTextArea in preparation for the new game.
            backpack.getQuestionPanel().setChatAreaText("");
            // Start a new game using same backpack / username
            WaitingPanel waitingPanel = new WaitingPanel(backpack);
            waitingPanel.setVisible(true);
            // Close this summary window
            this.dispose();
            backpack.setRoundSummaryPanel(new RoundSummaryPanel(backpack));
        });

        // No: disconnect and close the app window
        playAgainNo.addActionListener(e -> {
            NetworkClient client = backpack.getNetworkClient();
            if (client != null) {
                client.disconnect();
            }
            this.dispose();
            System.exit(0);
        });

        gameOverBackground = new JButton();
        gameOverBackground.setBounds(100, 300, 400, 215);
        gameOverBackground.setBackground(new Color(0, 0, 0, 0.6f));
        gameOverBackground.setEnabled(false);
        gameOverBackground.setVisible(false);
        add(gameOverBackground);
    }

//    private void addAnswerBoxes() {
//        addAnswerRow(300);
//        addAnswerRow(360);
//        addAnswerRow(420);
//        addAnswerRow(480);
//        addAnswerRow(540);
//    }

    public void addAnswerRow(int y, List<RoundResult> roundResult) {
        createButtonCluster(70, y, roundResult.get(0));
        createButtonCluster(310, y, roundResult.get(1));
        startCountDown();
    }

    private void createButtonCluster(int x, int y, RoundResult roundResult) {
        for (int i = 0; i < backpack.getGameConfig().getTotalQuestionsPerRound(); i++) {

            ImageIcon correct = new ImageIcon(new ImageIcon("src/main/resources/images/right.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            ImageIcon wrong = new ImageIcon(new ImageIcon("src/main/resources/images/wrong.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));

            JLabel label = new JLabel((roundResult.getResults().get(i) ? correct : wrong));
            label.setBounds(x + (i * 55), y, 50, 50);
            add(label);

        }
        repaint();
        revalidate();
    }

    public void setPlayerName() {
        Player1.setText(backpack.getUsername());
        Player2.setText(backpack.getOpponentUsername());
    }

    public void startCountDown() {

        for (int i = 5; i >= 0; i--) {
            countDown.setText(Integer.toString(i));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.dispose();
        backpack.getCategoryPanel().setVisible(true);

    }

    // Show final result: winner name and "Play again? Yes / No" buttons.
    // winnerName: the username sent by server, or "DRAW" if it's a tie.
    // localUsername: our own username (to show "You won!" when appropriate).
    public void showGameOver(String winnerName, String localUsername) {

        // Stop showing countdown in game-over mode
        if (countDown != null) {
            countDown.setVisible(false);
        }

        String message;
        if ("DRAW".equals(winnerName)) {
            message = "It's a draw!";
        } else if (winnerName.equals(localUsername)) {
            message = "You won! Congrats!";
        } else {
            message = winnerName + " won!";
        }

        gameOverLabel.setText(message);
        gameOverLabel.setVisible(true);
        playAgainLabel.setVisible(true);
        playAgainYes.setVisible(true);
        playAgainNo.setVisible(true);
        gameOverBackground.setVisible(true);

        // panel is visible
        this.setVisible(true);
    }

    public void showOpponentLeft(String username) {
        // Stop showing countdown in game-over mode
        if (countDown != null) {
            countDown.setVisible(false);
        }

        String message = username+" has left the game.";
        gameOverLabel.setText(message);
        gameOverLabel.setVisible(true);
        playAgainLabel.setVisible(true);
        playAgainYes.setVisible(true);
        playAgainNo.setVisible(true);
        gameOverBackground.setVisible(true);

        // panel is visible
        this.setVisible(true);
    }
}
