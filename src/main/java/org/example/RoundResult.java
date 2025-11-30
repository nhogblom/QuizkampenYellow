package org.example;

import java.util.List;

public class RoundResult {
    private int round;
    private List<Boolean> rightAnswers;

    public RoundResult(int round, List<Boolean> rightAnswers) {
        this.round = round;
        this.rightAnswers = rightAnswers;
    }
}
