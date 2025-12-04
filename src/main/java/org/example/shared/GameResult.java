package org.example.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
Game score is stored as true or false.
GameResult consists of a list that contains lists of each round.
The list of a round consists of boolean values for correct and wrong answer.
 */

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

    public int getGameResult() {
        int score = 0;
        for (RoundResult roundResult : result) {
            for (int j = 0; j < roundResult.getRound().size(); j++) {
                if (roundResult.getRound().get(j) == true) {
                    score++;
                }
            }
        }
        return score;
    }

    public void setResult(List<RoundResult> result) {
        this.result = result;
    }

}
