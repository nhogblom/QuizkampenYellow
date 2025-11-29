package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

import org.example.client.ClientBackpack;
import org.example.Question;

/**
 * QuestionPanel
 *
 * This panel displays a single quiz question + 4 answer buttons.
 *
 * DESIGN:
 * - GUI-only: no networking, no socket logic.
 * - Communicates via QuestionAnsweredListener, provided from outside
 *   (WaitingPanel -> NetworkClient).
 * - Uses ClientBackpack only as shared context (stores a reference and
 *   registers itself, so NetworkClient can call updateQuestion(...)).
 */
public class QuestionPanel extends JFrame {

    /** Listener provided by WaitingPanel - forwarded to NetworkClient */
    private final QuestionAnsweredListener listener;
    private final ClientBackpack backpack;

    /** GUI components updated when new questions arrive */
    private JButton questionButton;
    private JButton optionButton1;
    private JButton optionButton2;
    private JButton optionButton3;
    private JButton optionButton4;

    public QuestionPanel(ClientBackpack backpack, QuestionAnsweredListener listener) {
        super("Quizkampen - Question");
        this.listener = listener;
        this.backpack = backpack;

        // Register this panel in the shared state so NetworkClient can find it
        backpack.setQuestionPanel(this);
        backpack.setActiveJframe(this);

        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
    }

    /**
     * NetworkClient calls this method whenever a new question is received.
     * This is the connection between the networking layer and the GUI layer.
     */
    public void updateQuestion(Question q) {
        if (q == null) {
            System.out.println("WARNING: updateQuestion called with null Question");
            return;
        }
        updateQuestion(q.getQuestionText(), q.getOptions());
    }

    /** Creates and places all GUI components */
    private void addGuiComponents() {

        // Main question box
        questionButton = new JButton("QUESTION");
        questionButton.setFont(new Font("Arial", Font.BOLD, 16));
        questionButton.setBounds(100, 140, 400, 200);
        questionButton.setEnabled(false);  // purely visual, not clickable
        add(questionButton);

        // Option 1
        optionButton1 = makeOptionButton(100, 380);
        optionButton1.addActionListener(e -> listener.onAnswerSelected(0)); // notify listener
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

    /** Helper for styling of all answer buttons */
    private JButton makeOptionButton(int x, int y) {
        JButton b = new JButton("option");
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBounds(x, y, 180, 150);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        return b;
    }

    /**
     * Update the text on all GUI components when a new question arrives.
     * This is called from NetworkClient - handleQuestion().
     */
    public void updateQuestion(String question, String[] options) {
        if (question == null || options == null || options.length < 4) {
            System.out.println("WARNING: invalid question/options in updateQuestion");
            return;
        }

        questionButton.setText(question);
        optionButton1.setText(options[0]);
        optionButton2.setText(options[1]);
        optionButton3.setText(options[2]);
        optionButton4.setText(options[3]);
    }

    /**
     * Listener interface
     * Implemented in WaitingPanel like:
     *
     *      optionIndex -> client.sendAnswer(optionIndex)
     *
     * This keeps QuestionPanel completely GUI-only.
     */
    public interface QuestionAnsweredListener {
        void onAnswerSelected(int optionIndex);
    }
}
