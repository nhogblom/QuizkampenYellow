package org.example;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameResult implements Serializable {
    private List<RoundResult> result = new ArrayList<>();

    public GameResult() {

    }

    public RoundResult getRoundResult(int round) {
        return result.get(round-1);
    }

    public void addRoundResult(int round) {
        result.add(new RoundResult(round));
    }

    public List<RoundResult> getResult() {
        return result;
    }

    public void setResult(List<RoundResult> result) {
        result = result;
    }
}
