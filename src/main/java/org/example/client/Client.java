package org.example.client;

import org.example.client.panels.CategoryPanel;
import org.example.client.panels.MainWindow;
import org.example.client.panels.QuestionPanel;
import org.example.client.panels.RoundSummaryPanel;

import javax.swing.*;

public class Client {
    private final ClientBackpack backpack = new ClientBackpack();
    private final CategoryPanel categoryPanel = new CategoryPanel(backpack);
    private final QuestionPanel questionPanel = new QuestionPanel(backpack);
    private final RoundSummaryPanel roundSummaryPanel = new RoundSummaryPanel(backpack);

    //TODO lägga in alla paneler här och sen bara styra om dom syns eller ej senare i programmet.

    public Client() {
        backpack.setCategoryPanel(categoryPanel);
        SwingUtilities.invokeLater(() -> new MainWindow(backpack).setVisible(true));
    }

    void main() {
    }
}
