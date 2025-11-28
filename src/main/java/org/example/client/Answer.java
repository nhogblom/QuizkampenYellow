package org.example.client;

import java.io.Serializable;

public class Answer implements Serializable {
    private String playerName;
    private int chosenOption;

    public Answer(String playerName, int chosenOption) {
        this.playerName = playerName;
        this.chosenOption = chosenOption;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getChosenOption() {
        return chosenOption;
    }
}