package org.example.client;

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
