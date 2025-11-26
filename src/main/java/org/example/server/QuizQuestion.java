package org.example.server;

public class QuizQuestion {
    // TODO Representerar en enda quizfråga med frågetext, fyra svarsalternativ, rätt svar och kategori.

    private String question;
    private List<String> answers;
    private int correctAnswerIndex;

    public QuizQuestion() {
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public boolean isCorrect(int index) {
        return index == correctAnswerIndex;
    }
}
