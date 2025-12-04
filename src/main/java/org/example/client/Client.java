package org.example.client;

import org.example.shared.GameConfig;
import org.example.client.panels.CategoryPanel;
import org.example.client.panels.MainWindow;
import org.example.client.panels.QuestionPanel;
import org.example.client.panels.RoundSummaryPanel;

import javax.swing.*;

public class Client {
    private final ClientBackpack backpack = new ClientBackpack();
    private GameConfig gameConfig = new GameConfig();
    private final QuestionPanel questionPanel = new QuestionPanel(backpack);
    private final RoundSummaryPanel roundSummaryPanel = new RoundSummaryPanel(backpack);


    public Client() {
        CategoryPanel categoryPanel = new CategoryPanel(backpack);
        backpack.setGameConfig(gameConfig);
        backpack.setCategoryPanel(categoryPanel);
        SwingUtilities.invokeLater(() -> new MainWindow(backpack).setVisible(true));
    }

    void main() {
    }
}
