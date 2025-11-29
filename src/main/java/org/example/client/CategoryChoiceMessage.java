package org.example.client;

import java.io.Serializable;

public class CategoryChoiceMessage implements Serializable {
    private final String category;

    public CategoryChoiceMessage(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
}
