package org.example.client.panels;

import javax.swing.*;
import java.awt.*;

public class RoundSummaryPanel extends JFrame{

    public RoundSummaryPanel(){

        super("");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
        addAnswerBoxes();
    }

    private void addGuiComponents(){
        JButton Player1 = new JButton("Player 1");
        Player1.setBounds(100, 150, 170, 100);
        Player1.setForeground(Color.WHITE);
        Player1.setBackground(Constants.LIGHT_GREEN);
        Player1.setEnabled(false);
        add(Player1);

        JButton Player2 = new JButton("Player 2");
        Player2.setBounds(330, 150, 170, 100);
        Player2.setForeground(Color.WHITE);
        Player2.setBackground(Constants.LIGHT_GREEN);
        Player2.setEnabled(false);
        add(Player2);
    }

    private void addAnswerBoxes() {
        addAnswerRow(300);
        addAnswerRow(360);
        addAnswerRow(420);
        addAnswerRow(480);
        addAnswerRow(540);
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

    //  todo spelresultat från avslutad omgång., övergå till att nästa spelare får välja kategori alternativt om alla rundor körts till game summary panel

}
