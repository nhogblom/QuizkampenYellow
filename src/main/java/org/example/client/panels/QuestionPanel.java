package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JFrame {

    private String question;
    private String option1, option2, option3, option4;
    private boolean correct1, correct2, correct3;


    public QuestionPanel() {
        super("");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addAnswerBoxes();
        addGuiComponents();
    }

    private void addGuiComponents(){
        JButton questions = new JButton("QUESTION");
        questions.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        questions.setBounds(100, 140, 400, 200);
        questions.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(questions);
        questions.setForeground(Color.BLACK);
        questions.setBackground(Color.WHITE);
        questions.setEnabled(false);

        //Category 1
        JButton opt1 = new JButton("option1");
        opt1.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt1.setBounds(100, 550, 180, 150);
        opt1.setForeground(Color.BLACK);
        opt1.setBackground(Color.WHITE);
        add(opt1);
        //Category 2
        JButton opt2 = new JButton("option2");
        opt2.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt2.setBounds(320, 380, 180, 150);
        opt2.setForeground(Color.BLACK);
        opt2.setBackground(Color.WHITE);
        add(opt2);
        //Category 3
        JButton opt3 = new JButton("option3");
        opt3.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt3.setBounds(100, 380, 180, 150);
        opt3.setForeground(Color.BLACK);
        opt3.setBackground(Color.WHITE);
        add(opt3);

        JButton opt4 = new JButton("option4");
        opt4.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt4.setBounds(320, 550, 180, 150);
        opt4.setForeground(Color.BLACK);
        opt4.setBackground(Color.WHITE);
        add(opt4);

        JButton giveUp = new JButton("GIVE UP");
        giveUp.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        giveUp.setBounds(250, 15, 100, 25);
        giveUp.setForeground(Color.BLACK);
        giveUp.setBackground(Color.RED);
        add(giveUp);
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
            add(button);
            button.setEnabled(false);
        }
    }

    // todo frågorna visas upp och spelaren får göra sitt val.
}
