package org.example.client.panels;

import org.example.client.ClientBackpack;
import org.example.shared.Avatars;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    private JTextField usernameField;
    private Avatars avatar;
    private JLabel avatar1label;
    private JLabel avatar2label;
    private JLabel avatar3label;
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

        //avatar
        ImageIcon avatar1img = new ImageIcon(new ImageIcon(Avatars.DUCK.getPathToAvatarImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        ImageIcon avatar2img = new ImageIcon(new ImageIcon(Avatars.FOX.getPathToAvatarImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        ImageIcon avatar3img = new ImageIcon(new ImageIcon(Avatars.PIKACHU.getPathToAvatarImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        // border
        Border selectedBorder = BorderFactory.createLineBorder(Constants.LIGHT_YELLOW, 3);

        avatar1label = new JLabel(avatar1img);
        avatar1label.setBounds(100, 90, 100, 100);
        add(avatar1label);
        avatar1label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar = Avatars.DUCK;
                avatar1label.setBorder(selectedBorder);
                avatar2label.setBorder(null);
                avatar3label.setBorder(null);
            }
        });
        avatar2label = new JLabel(avatar2img);
        avatar2label.setBounds(250, 90, 100, 100);
        add(avatar2label);
        avatar2label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar = Avatars.DUCK;
                avatar2label.setBorder(selectedBorder);
                avatar3label.setBorder(null);
                avatar1label.setBorder(null);
            }
        });

        avatar3label = new JLabel(avatar3img);
        avatar3label.setBounds(390, 90, 100, 100);
        add(avatar3label);
        avatar3label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar = Avatars.PIKACHU;
                avatar3label.setBorder(selectedBorder);
                avatar2label.setBorder(null);
                avatar1label.setBorder(null);
            }
        });


        add(avatar1label);
        add(avatar2label);
        add(avatar3label);


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
                backpack.setAvatar(avatar);
                if (avatar == null) {
                    JOptionPane.showMessageDialog(MainWindow.this, "Please select an avatar!");
                } else {


                    if (backpack.getUsername().equals("") || backpack.getUsername().isEmpty()) {
                        JOptionPane.showMessageDialog(MainWindow.this, "Please enter a username!");
                    } else {
                        WaitingPanel waiting = new WaitingPanel(backpack);
                        waiting.setLocationRelativeTo(MainWindow.this);
                        MainWindow.this.dispose();
                        waiting.setVisible(true);
                    }
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

