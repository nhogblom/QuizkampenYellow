package org.example.client.panels;

import org.example.RoundResult;
import org.example.client.ClientBackpack;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class RoundSummaryPanel extends JFrame {
    private ClientBackpack backpack;
    JButton Player1;
    JButton Player2;
    JLabel countDown;

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
        Player1 = new JButton(backpack.getUsername());
        Player1.setBounds(100, 150, 170, 100);
        Player1.setForeground(Color.WHITE);
        Player1.setBackground(Constants.LIGHT_GREEN);
        Player1.setEnabled(false);
        add(Player1);

        Player2 = new JButton(backpack.getOpponentUsername());
        Player2.setBounds(330, 150, 170, 100);
        Player2.setForeground(Color.WHITE);
        Player2.setBackground(Constants.LIGHT_GREEN);
        Player2.setEnabled(false);
        add(Player2);

        countDown = new JLabel();
        add(countDown);
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

    public void startCountDown(){
        new Thread(() ->{
            for(int i = 5; i < 0; i--){
                countDown.setText(Integer.toString(i));
            }
            this.dispose();
            backpack.getQuestionPanel().setVisible(true);
        }).start();
    }



    //  todo spelresultat från avslutad omgång., övergå till att nästa spelare får välja kategori alternativt om alla rundor körts till game summary panel

}
