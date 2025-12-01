package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameResult implements Serializable {
    private List<RoundResult> result = new ArrayList<>();
    private GameConfig gameConfig = new GameConfig();

    public GameResult() {
    }

    public RoundResult getRoundResult(int round) {
        return this.result.get(round - 1);
    }

    public void addRoundResult(int round) {
        this.result.add(new RoundResult(round));
    }

    public List<RoundResult> getResult() {
        return this.result;
    }

    public int getScorePlayer1() {
        int totalPoints = 0;
        for (RoundResult roundResult : result) {
            for (int i = 0; i < gameConfig.getTotalQuestionsPerRound(); i++) {
                totalPoints += ((roundResult.getResults().get(i)) ? 1 : 0);
            }
        }
        return totalPoints;
    }

    public int getScorePlayer2() {
        int totalPoints = 0;
        for (RoundResult roundResult : result) {
            for (int i = 0; i < gameConfig.getTotalQuestionsPerRound(); i++) {
                totalPoints += ((roundResult.getResults().get(gameConfig.getTotalQuestionsPerRound() + i)) ? 1 : 0);
            }
        }
        return totalPoints;
    }

    public void setResult(List<RoundResult> result) {
        this.result = result;
    }

}
