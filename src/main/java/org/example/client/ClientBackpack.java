package org.example.client;

import org.example.client.panels.QuestionPanel;
import org.example.client.panels.WaitingPanel;

import javax.swing.*;

public class ClientBackpack {
    private String username;
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

    public void setQuestionAnsweredListener(Object o) {
    }

    public void setNetworkClient(NetworkClient networkClient) {
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }
}
