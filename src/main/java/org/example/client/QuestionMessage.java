package org.example.client;

import org.example.Question;

import java.io.Serializable;

public class QuestionMessage implements Serializable {
    private final Question question;

    public QuestionMessage(Question question) {
        this.question = question;
    }
    public Question getQuestion() {
        return question;
    }
}
