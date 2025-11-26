package org.example.server;

public class QuizCategory {
    private String name;

    public QuizCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}

// TODO Representerar en frågekategori och används för att organisera vilka frågor som hör till vilken kategori.
