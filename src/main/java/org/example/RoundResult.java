package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoundResult implements Serializable {
    private int round;
    private List<Boolean> results = new ArrayList<>();

    public RoundResult(int round) {
        this.round = round;
    }

    public void addResult(boolean result) {
        results.add(result);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<Boolean> getResults() {
        return results;
    }

    public void setResults(List<Boolean> rightAnswers) {
        this.results = rightAnswers;
    }
}
