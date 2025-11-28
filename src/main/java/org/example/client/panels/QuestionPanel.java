package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

import org.example.client.ClientBackpack;
import org.example.Question;


/**
 * QuestionPanel now receives a listener so it can notify NetworkClient when a button is clicked.
 * It no longer receives a Flag. WaitingPanel will pass the correct listener.
 */
public class QuestionPanel extends JFrame {

    // Listener for sending answers back to NetworkClient
    private final QuestionAnsweredListener listener;
    private ClientBackpack backpack;


    // GUI components
    private JButton questionButton;
    private JButton optionButton1;
    private JButton optionButton2;
    private JButton optionButton3;
    private JButton optionButton4;

    public QuestionPanel(ClientBackpack backpack, QuestionAnsweredListener listener) {
        super("Quizkampen - Question");
        this.listener = listener;
        this.backpack = backpack;
        backpack.setQuestionPanel(this);
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);
        addGuiComponents();
    }
    public void updateQuestion(Question q) {
        updateQuestion(q.getQuestionText(), q.getOptions());
    }


    private void addGuiComponents() {

        // Question text area
        questionButton = new JButton("QUESTION");
        questionButton.setFont(new Font("Arial", Font.BOLD, 16));
        questionButton.setBounds(100, 140, 400, 200);
        questionButton.setEnabled(false);
        add(questionButton);

        // Option 1
        optionButton1 = makeOptionButton(100, 380);
        optionButton1.addActionListener(e -> listener.onAnswerSelected(0));
        add(optionButton1);

        // Option 2
        optionButton2 = makeOptionButton(320, 380);
        optionButton2.addActionListener(e -> listener.onAnswerSelected(1));
        add(optionButton2);

        // Option 3
        optionButton3 = makeOptionButton(100, 550);
        optionButton3.addActionListener(e -> listener.onAnswerSelected(2));
        add(optionButton3);

        // Option 4
        optionButton4 = makeOptionButton(320, 550);
        optionButton4.addActionListener(e -> listener.onAnswerSelected(3));
        add(optionButton4);
    }

    private JButton makeOptionButton(int x, int y) {
        JButton b = new JButton("option");
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBounds(x, y, 180, 150);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        return b;
    }

    /** Update GUI when NetworkClient receives a question */
    public void updateQuestion(String question, String[] options) {
        questionButton.setText(question);
        optionButton1.setText(options[0]);
        optionButton2.setText(options[1]);
        optionButton3.setText(options[2]);
        optionButton4.setText(options[3]);
    }

    /** Listener interface */
    public interface QuestionAnsweredListener {
        void onAnswerSelected(int optionIndex);
    }
}
