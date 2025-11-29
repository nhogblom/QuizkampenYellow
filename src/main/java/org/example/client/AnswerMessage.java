package org.example.client;

import org.example.Answer;

import java.io.Serializable;

public class AnswerMessage implements Serializable {
    private final Answer answer;

    public AnswerMessage(Answer answer) {
        this.answer = answer;
    }
    public Answer getAnswer() {
        return answer;
    }
}
