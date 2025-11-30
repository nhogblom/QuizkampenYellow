package org.example.server;

import java.io.Serializable;
import java.util.List;

public class CategoryPrompt implements Serializable {
    private List<QuizCategory> categories;

    public CategoryPrompt() {
        QuestionRepository questionRepository = new QuestionRepository();
        this.categories = questionRepository.getCategories();
    }

    public String printCategories() {
        return categories.toString();
    }

    public List<QuizCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<QuizCategory> categories) {
        this.categories = categories;
    }
}
