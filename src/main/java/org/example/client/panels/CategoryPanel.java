package org.example.client.panels;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoryPanel extends JFrame {

    private String category1;
    private String category2;
    private String category3;

    public CategoryPanel() {
        super("");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);

        addGuiComponents();
    }

    private void addGuiComponents(){
        JLabel cat = new JLabel("Choose a category:");
        cat.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        cat.setBounds(100, 50, 400, 43);
        cat.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(cat);

        //Category 1
        JButton cat1 = new JButton(category1);
        cat1.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat1.setBounds(100, 200, 400, 43);
        cat1.setForeground(Color.BLACK);
        cat1.setBackground(Constants.LIGHT_GREEN);
        add(cat1);
        //Category 2
        JButton cat2 = new JButton(category2);
        cat2.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat2.setBounds(100, 300, 400, 43);
        cat2.setForeground(Color.BLACK);
        cat2.setBackground(Constants.LIGHT_GREEN);
        add(cat2);
        //Category 3
        JButton cat3 = new JButton(category3);
        cat3.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        cat3.setBounds(100, 400, 400, 43);
        cat3.setForeground(Color.BLACK);
        cat3.setBackground(Constants.LIGHT_GREEN);
        add(cat3);
    }


    //  todo spelare promtas att göra ett val av spelkategori~
}
