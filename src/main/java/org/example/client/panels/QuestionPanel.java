package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JFrame {

    // Data for the current question
    private String questionText;
    private String[] options = new String[4];

    // GUI components that we will update
    private JButton questionButton;
    private JButton optionButton1;
    private JButton optionButton2;
    private JButton optionButton3;
    private JButton optionButton4;
    private JButton giveUpButton;

    public QuestionPanel() {
        super("Quizkampen - Question");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addAnswerBoxes();
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Question area
        questionButton = new JButton("QUESTION");
        questionButton.setFont(new Font("Arial", Font.BOLD, 16));
        questionButton.setBounds(100, 140, 400, 200);
        questionButton.setHorizontalAlignment(SwingConstants.CENTER);
        questionButton.setForeground(Color.BLACK);
        questionButton.setBackground(Color.WHITE);
        questionButton.setEnabled(false);
        getContentPane().add(questionButton);

        // Answer option 1
        optionButton1 = new JButton("option1");
        optionButton1.setFont(new Font("Arial", Font.BOLD, 16));
        optionButton1.setBounds(100, 550, 180, 150);
        optionButton1.setForeground(Color.BLACK);
        optionButton1.setBackground(Color.WHITE);
        add(optionButton1);

        // Answer option 2
        optionButton2 = new JButton("option2");
        optionButton2.setFont(new Font("Arial", Font.BOLD, 16));
        optionButton2.setBounds(320, 380, 180, 150);
        optionButton2.setForeground(Color.BLACK);
        optionButton2.setBackground(Color.WHITE);
        add(optionButton2);

        // Answer option 3
        optionButton3 = new JButton("option3");
        optionButton3.setFont(new Font("Arial", Font.BOLD, 16));
        optionButton3.setBounds(100, 380, 180, 150);
        optionButton3.setForeground(Color.BLACK);
        optionButton3.setBackground(Color.WHITE);
        add(optionButton3);

        // Answer option 4
        optionButton4 = new JButton("option4");
        optionButton4.setFont(new Font("Arial", Font.BOLD, 16));
        optionButton4.setBounds(320, 550, 180, 150);
        optionButton4.setForeground(Color.BLACK);
        optionButton4.setBackground(Color.WHITE);
        add(optionButton4);

        // Give up button
        giveUpButton = new JButton("GIVE UP");
        giveUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        giveUpButton.setBounds(250, 15, 100, 25);
        giveUpButton.setForeground(Color.BLACK);
        giveUpButton.setBackground(Color.RED);
        add(giveUpButton);
    }

    private void addAnswerBoxes() {
        addAnswerRow(50);
    }

    private void addAnswerRow(int y) {
        createButtonCluster(100, y);
        createButtonCluster(330, y);
    }

    private void createButtonCluster(int x, int y) {
        for (int i = 0; i < 3; i++) {
            JButton button = new JButton("");
            button.setBackground(Color.WHITE);
            button.setBounds(x + (i * 60), y, 50, 50);
            button.setEnabled(false);
            add(button);
        }
    }

    /**
     * Called from the network layer when a new question arrives.
     * This replaces the hard-coded texts.
     */
    public void updateQuestion(String questionText, String[] options) {
        this.questionText = questionText;
        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("options must be an array of length 4");
        }
        this.options = options.clone();

        // Update GUI
        questionButton.setText(this.questionText);
        optionButton1.setText(this.options[0]);
        optionButton2.setText(this.options[1]);
        optionButton3.setText(this.options[2]);
        optionButton4.setText(this.options[3]);

        // Reset colors in case a previous question changed them later
        resetOptionButtonColors();
    }

    private void resetOptionButtonColors() {
        optionButton1.setBackground(Color.WHITE);
        optionButton2.setBackground(Color.WHITE);
        optionButton3.setBackground(Color.WHITE);
        optionButton4.setBackground(Color.WHITE);
    }

    // Later steps:
    // - add ActionListeners on optionButton1..4
    // - send chosen answer back via NetworkClient
    // - color buttons for correct/incorrect, etc.
}
