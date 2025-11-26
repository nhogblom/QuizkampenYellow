package org.example.client;

import org.example.client.panels.MainWindow;
import org.example.client.panels.QuestionPanel;
import org.example.client.panels.RoundSummaryPanel;


import javax.swing.*;

public class Client {
    void main() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new MainWindow().setVisible(true);
                //new QuestionPanel().setVisible(true);
                //new RoundSummaryPanel().setVisible(true);
            }
        });
    }
}
