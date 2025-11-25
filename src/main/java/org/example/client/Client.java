package org.example.client;

import org.example.client.panels.CategoryPanel;
import org.example.client.panels.MainWindow;
import org.example.client.panels.WaitingPanel;

import javax.swing.*;

public class Client {
    void main() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
                //new CategoryPanel().setVisible(true);
                //new WaitingPanel().setVisible(true);
                //
            }
        });
    }
}
