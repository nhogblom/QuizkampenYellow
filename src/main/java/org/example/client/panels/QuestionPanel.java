package org.example.client.panels;

import org.example.client.Flag;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JFrame {

    // Data for current question
    private String questionText;
    private String[] options = new String[4];

    // GUI components
    private JButton questionButton;
    private JButton optionButton1;
    private JButton optionButton2;
    private JButton optionButton3;
    private JButton optionButton4;
    private JButton giveUpButton;

    private final Flag nextFlag;

    public QuestionPanel(Flag nextFlag) {
        super("Quizkampen - Question");
        this.nextFlag = nextFlag;

        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addAnswerBoxes();
        addGuiComponents();
        addButtonListeners();
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
        add(questionButton);

        optionButton1 = createOptionButton("option1", 100, 550);
        optionButton2 = createOptionButton("option2", 320, 380);
        optionButton3 = createOptionButton("option3", 100, 380);
        optionButton4 = createOptionButton("option4", 320, 550);

        giveUpButton = new JButton("GIVE UP");
        giveUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        giveUpButton.setBounds(250, 15, 100, 25);
        giveUpButton.setForeground(Color.BLACK);
        giveUpButton.setBackground(Color.RED);
        add(giveUpButton);
    }

    private JButton createOptionButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBounds(x, y, 180, 150);
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        add(btn);
        return btn;
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
     * Called by NetworkClient when a new question arrives.
     */
    public void updateQuestion(String questionText, String[] options) {
        this.questionText = questionText;

        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("options[] must have exactly 4 items");
        }
        this.options = options.clone();

        // Update UI
        questionButton.setText(questionText);
        optionButton1.setText(options[0]);
        optionButton2.setText(options[1]);
        optionButton3.setText(options[2]);
        optionButton4.setText(options[3]);

        resetOptionButtonColors();
    }

    private void resetOptionButtonColors() {
        optionButton1.setBackground(Color.WHITE);
        optionButton2.setBackground(Color.WHITE);
        optionButton3.setBackground(Color.WHITE);
        optionButton4.setBackground(Color.WHITE);
    }

    /**
     * Add listeners to the four answer buttons + GiveUp button.
     * (Sending answers will be added AFTER this step.)
     */
    private void addButtonListeners() {
        optionButton1.addActionListener(e -> answerSelected(0, optionButton1));
        optionButton2.addActionListener(e -> answerSelected(1, optionButton2));
        optionButton3.addActionListener(e -> answerSelected(2, optionButton3));
        optionButton4.addActionListener(e -> answerSelected(3, optionButton4));

        giveUpButton.addActionListener(e -> {
            System.out.println("Player gave up!");
            nextFlag.setFlag(true); // Move to next UI if needed
        });
    }

    private void answerSelected(int index, JButton clickedButton) {
        System.out.println("Player selected option: " + index);

        // in future: send answer to server via NetworkClient

        clickedButton.setBackground(Color.YELLOW); // temporary feedback
    }
}
