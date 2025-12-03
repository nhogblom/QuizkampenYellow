package org.example.client.panels;

import org.example.client.ClientBackpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private JTextField usernameField;
    private final ClientBackpack backpack;

    public MainWindow(ClientBackpack backpack) {
        this.backpack = backpack;
        super("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);
        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel title = new JLabel("Quizkampen");
        title.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        title.setBounds(100, 20, 400, 43);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Constants.LIGHT_YELLOW);
        add(title);

        //subtitle for username textfield
        JLabel usernameSubtitle = new JLabel("Username:");
        usernameSubtitle.setFont(new java.awt.Font("Arial", Font.BOLD, 24));
        usernameSubtitle.setBounds(100, 200, 400, 43);
        usernameSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        usernameSubtitle.setForeground(Constants.LIGHT_YELLOW);
        add(usernameSubtitle);

        //textfield for username
        usernameField = new JTextField("");
        usernameField.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        usernameField.setBounds(100, 250, 400, 43);
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        usernameField.setForeground(Color.BLACK);

        add(usernameField);

        //start button

        JButton startButton = new JButton("Start new game");
        startButton.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        startButton.setBounds(100, 350, 400, 43);
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Constants.LIGHT_GREEN);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                backpack.setUsername(usernameField.getText());
                if (backpack.getUsername().equals("") || backpack.getUsername().isEmpty()) {
                    JOptionPane.showMessageDialog(MainWindow.this, "Please enter a username!");
                } else {
                    WaitingPanel waiting = new WaitingPanel(backpack);
                    waiting.setLocationRelativeTo(MainWindow.this);
                    MainWindow.this.dispose();
                    waiting.setVisible(true);
                }

            }
        });


        add(startButton);

        //exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        exitButton.setBounds(100, 450, 400, 43);
        exitButton.setForeground(Color.BLACK);
        exitButton.setBackground(Constants.LIGHT_RED);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });

        add(exitButton);

    }
}
// TODO Login screen, användaren promtas  för användarnamn & spelläge

