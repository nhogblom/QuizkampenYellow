package org.example.client;

import org.example.client.panels.CategoryPanel;
import org.example.client.panels.QuestionPanel;
import org.example.client.panels.WaitingPanel;

import javax.swing.*;

public class ClientBackpack {
    private CategoryPanel categoryPanel;
    private String username;
    private String opponentUsername;
    private boolean goToNextScreen;
    private JFrame activeJframe;
    private NetworkClient networkClient;
    private QuestionPanel questionPanel;

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }

    public void setQuestionPanel(QuestionPanel questionPanel) {
        this.questionPanel = questionPanel;
    }

    public ClientBackpack() {
        this.goToNextScreen = false;
    }

    public boolean isGoToNextScreen() {
        return goToNextScreen;
    }

    public void setGoToNextScreen(boolean goToNextScreen) {
        this.goToNextScreen = goToNextScreen;
    }

    public void setUsername(String text) {
        this.username = text;
    }

    public String getUsername() {
        return username;
    }

    public JFrame getActiveJframe() {
        return activeJframe;
    }

    public void setActiveJframe(JFrame activeJframe) {
        this.activeJframe = activeJframe;
    }

    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    public void setCategoryPanel(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public void setOpponentUsername(String opponentUsername) {
        this.opponentUsername = opponentUsername;
    }

    public void setNetworkClient(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }
}
