package org.example.client.panels;

import org.example.client.ClientBackpack;
import org.example.server.CategoryPrompt;

import javax.swing.*;
import java.awt.*;

public class CategoryPanel extends JFrame {

    private JButton cat1;
    private JButton cat2;
    private JButton cat3;

    public CategoryPanel(ClientBackpack backpack) {
        super("");
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
        cat1.setText(categoryPrompt.getCategories().get(0));
        cat1.addActionListener((_)-> {
            // skapa ett anrop tillbaka till servern~
        });
        cat2.setText(categoryPrompt.getCategories().get(1));
        cat3.setText(categoryPrompt.getCategories().get(2));
    }

    public void waiting

    private void addGuiComponents() {
        JLabel cat = new JLabel("Choose a category:");
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
        add(cat1);
        //Category 2
        cat2 = new JButton();
        cat2.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat2.setBounds(100, 300, 400, 43);
        cat2.setForeground(Color.BLACK);
        cat2.setBackground(Constants.LIGHT_GREEN);
        add(cat2);
        //Category 3
        cat3 = new JButton();
        cat3.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat3.setBounds(100, 400, 400, 43);
        cat3.setForeground(Color.BLACK);
        cat3.setBackground(Constants.LIGHT_GREEN);
        add(cat3);
    }

}
