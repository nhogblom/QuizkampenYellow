package org.example.server;

import java.io.Serializable;
import java.util.List;

public class CategoryPrompt implements Serializable {
    private List<String> categories;

    public CategoryPrompt() {
        QuestionRepository questionRepository = new QuestionRepository();
        this.categories = questionRepository.getCategories();
    }

    public String printCategories() {
        return categories.toString();
    }
}
