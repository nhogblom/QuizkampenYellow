package org.example.server;

import java.io.Serializable;
import java.util.List;

public class QuizQuestion implements Serializable {

    private String question;
    private List<String> answers;
    private final String correctAnswer;

    public QuizQuestion(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


    public boolean isCorrect(String clientAnswer) {
        return clientAnswer.equals(correctAnswer);
    }
}
//