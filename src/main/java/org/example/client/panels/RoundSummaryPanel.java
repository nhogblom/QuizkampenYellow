package org.example.client.panels;

import org.example.RoundResult;
import org.example.client.ClientBackpack;

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
        // Player 1 box
        Player1 = new JButton(backpack.getUsername());
        Player1.setBounds(100, 150, 170, 100);
        Player1.setForeground(Color.WHITE);
        Player1.setBackground(Constants.LIGHT_GREEN);
        Player1.setEnabled(false);
        add(Player1);

        // Player 2 box
        Player2 = new JButton(backpack.getOpponentUsername());
        Player2.setBounds(330, 150, 170, 100);
        Player2.setForeground(Color.WHITE);
        Player2.setBackground(Constants.LIGHT_GREEN);
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
        playAgainYes.setBounds(150, 390, 120, 40);
        playAgainYes.setVisible(false);
        add(playAgainYes);

        playAgainNo = new JButton("No");
        playAgainNo.setBounds(330, 390, 120, 40);
        playAgainNo.setVisible(false);
        add(playAgainNo);

        // Simple behaviour for now:
        playAgainYes.addActionListener(e -> {
            //TODO: hook into real "start a new game" later if we want.
            JOptionPane.showMessageDialog(this, "Play again is not implemented yet.");
        });

        playAgainNo.addActionListener(e -> {
            // Close this window. We could also exit the app if we want.
            this.dispose();
        });
    }

//    private void addAnswerBoxes() {
//        addAnswerRow(300);
//        addAnswerRow(360);
//        addAnswerRow(420);
//        addAnswerRow(480);
//        addAnswerRow(540);
//    }

    public void addAnswerRow(int y, List<RoundResult> roundResult) {
        createButtonCluster(100, y, roundResult.get(0));
        createButtonCluster(330, y, roundResult.get(1));
        startCountDown();
    }

    private void createButtonCluster(int x, int y, RoundResult roundResult) {
        for (int i = 0; i < 3; i++) {
            JButton button = new JButton((roundResult.getResults().get(i)) ? "X" : "-");
            button.setBackground(Color.WHITE);
            button.setBounds(x + (i * 60), y, 50, 50);
            add(button);
            button.setEnabled(false);
        }
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
            message = winnerName + " won! Congrats!";
        }

        gameOverLabel.setText(message);
        gameOverLabel.setVisible(true);
        playAgainLabel.setVisible(true);
        playAgainYes.setVisible(true);
        playAgainNo.setVisible(true);

        // panel is visible
        this.setVisible(true);
    }

    //  todo spelresultat från avslutad omgång., övergå till att nästa spelare får välja kategori
    //  alternativt om alla rundor körts till game summary panel

}
