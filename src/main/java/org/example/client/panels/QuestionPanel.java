package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JFrame {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

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
        JButton questions = new JButton(question);
        questions.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        questions.setBounds(100, 140, 400, 200);
        questions.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(questions);
        questions.setForeground(Color.BLACK);
        questions.setBackground(Color.WHITE);
        questions.setEnabled(false);

        //Category 1
        JButton opt1 = new JButton(option1);
        opt1.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt1.setBounds(100, 550, 180, 150);
        opt1.setForeground(Color.BLACK);
        opt1.setBackground(Color.WHITE);
        add(opt1);
        //Category 2
        JButton opt2 = new JButton(option2);
        opt2.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt2.setBounds(320, 380, 180, 150);
        opt2.setForeground(Color.BLACK);
        opt2.setBackground(Color.WHITE);
        add(opt2);
        //Category 3
        JButton opt3 = new JButton(option3);
        opt3.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt3.setBounds(100, 380, 180, 150);
        opt3.setForeground(Color.BLACK);
        opt3.setBackground(Color.WHITE);
        add(opt3);

        JButton opt4 = new JButton(option4);
        opt4.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        opt4.setBounds(320, 550, 180, 150);
        opt4.setForeground(Color.BLACK);
        opt4.setBackground(Color.WHITE);
        add(opt4);
    }

    private void addAnswerBoxes(){
        JButton answerCheckLeft[] = new JButton[3];
        answerCheckLeft[0] = new JButton(option1);
        answerCheckLeft[1] = new JButton(option1);
        answerCheckLeft[2] = new JButton(option1);
        answerCheckLeft[0].setBackground(Color.WHITE);
        answerCheckLeft[1].setBackground(Color.WHITE);
        answerCheckLeft[2].setBackground(Color.WHITE);
        answerCheckLeft[0].setBounds(100, 40, 50, 50);
        answerCheckLeft[1].setBounds(160, 40, 50, 50);
        answerCheckLeft[2].setBounds(220, 40, 50, 50);
        add(answerCheckLeft[0]);
        add(answerCheckLeft[1]);
        add(answerCheckLeft[2]);
        answerCheckLeft[0].setEnabled(false);
        answerCheckLeft[1].setEnabled(false);
        answerCheckLeft[2].setEnabled(false);

        JButton answerCheckRight[] = new JButton[3];
        answerCheckRight[0] = new JButton(option1);
        answerCheckRight[1] = new JButton(option1);
        answerCheckRight[2] = new JButton(option1);
        answerCheckRight[0].setBackground(Color.WHITE);
        answerCheckRight[1].setBackground(Color.WHITE);
        answerCheckRight[2].setBackground(Color.WHITE);
        answerCheckRight[0].setBounds(330, 40, 50, 50);
        answerCheckRight[1].setBounds(390, 40, 50, 50);
        answerCheckRight[2].setBounds(450, 40, 50, 50);
        add(answerCheckRight[0]);
        add(answerCheckRight[1]);
        add(answerCheckRight[2]);
        answerCheckRight[0].setEnabled(false);
        answerCheckRight[1].setEnabled(false);
        answerCheckRight[2].setEnabled(false);
    }

    // todo frågorna visas upp och spelaren får göra sitt val.
}
