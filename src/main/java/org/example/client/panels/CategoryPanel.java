package org.example.client.panels;

import org.example.Message;
import org.example.MessageTypes;
import org.example.client.Client;
import org.example.client.ClientBackpack;
import org.example.server.CategoryPrompt;
import org.example.server.QuizCategory;
import org.example.server.QuizQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryPanel extends JFrame {
    private JLabel cat;
    private JButton cat1;
    private JButton cat2;
    private JButton cat3;
    private ClientBackpack backpack;
    private QuizCategory a1;
    private QuizCategory a2;
    private QuizCategory a3;



    public CategoryPanel(ClientBackpack backpack) {
        super("");
        this.backpack = backpack;
        backpack.setCategoryPanel(this);
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
        ;
    }

    public void setCategories(CategoryPrompt categoryPrompt) {
        cat.setBounds(100, 50, 400, 43);
        cat.setText("Choose a category:");
        cat1.setText(categoryPrompt.getCategories().get(0).getName());
        cat1.setVisible(true);
        a1 = categoryPrompt.getCategories().get(0);
        cat2.setText(categoryPrompt.getCategories().get(1).getName());
        cat2.setVisible(true);
        a2 = categoryPrompt.getCategories().get(1);

        cat3.setText(categoryPrompt.getCategories().get(2).getName());
        cat3.setVisible(true);
        a3 = categoryPrompt.getCategories().get(2);
    }



    private void addGuiComponents() {
        cat = new JLabel("Choose a category:");
        cat.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        cat.setBounds(100, 50, 400, 43);
        cat.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(cat);

        //Category 1
        cat1 = new JButton();
        cat1.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat1.setBounds(100, 200, 400, 43);
        cat1.setForeground(Color.BLACK);
        cat1.setBackground(Constants.LIGHT_GREEN);
        cat1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backpack.getNetworkClient().sendMessage(new Message(MessageTypes.CATEGORY_CHOICE, a1));
            }
        });
        add(cat1);
        //Category 2
        cat2 = new JButton();
        cat2.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat2.setBounds(100, 300, 400, 43);
        cat2.setForeground(Color.BLACK);
        cat2.setBackground(Constants.LIGHT_GREEN);
        cat2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backpack.getNetworkClient().sendMessage(new Message(MessageTypes.CATEGORY_CHOICE, a2));
            }
        });
        add(cat2);
        //Category 3
        cat3 = new JButton();
        cat3.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat3.setBounds(100, 400, 400, 43);
        cat3.setForeground(Color.BLACK);
        cat3.setBackground(Constants.LIGHT_GREEN);
        cat3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backpack.getNetworkClient().sendMessage(new Message(MessageTypes.CATEGORY_CHOICE, a3));
            }
        });
        add(cat3);
    }

    public void displayWaitMessage(String s) {
        cat.setText("Waiting for opponent...");
        cat.setBounds(50, 200, 500, 43);
        cat1.setText("");
        cat1.setVisible(false);
        cat2.setText("");
        cat2.setVisible(false);
        cat3.setText("");
        cat3.setVisible(false);
    }
}
