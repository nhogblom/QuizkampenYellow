package org.example.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizCategory  {
    private String name;
    private List<QuizQuestion> questions = new ArrayList<>();

    public QuizCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public void addQuestion(QuizQuestion q) {
        questions.add(q);
    }

    public String toString() {
        return name;
    }
}

