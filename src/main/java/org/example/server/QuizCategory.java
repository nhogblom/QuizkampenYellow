package org.example.server;

public class QuizCategory {
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

// TODO Representerar en frågekategori och används för att organisera vilka frågor som hör till vilken kategori.
