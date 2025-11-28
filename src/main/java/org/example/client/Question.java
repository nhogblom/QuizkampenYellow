package org.example.client;

import java.io.Serializable;

public class Question implements Serializable {
    private String questionText;
    private String[] options;

    public Question(String questionText, String[] options) {
        this.questionText = questionText;
        this.options = options;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }
}
