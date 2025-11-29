package org.example.client;

import java.io.Serializable;

public class UsernameMessage implements Serializable {
    private final String username;

    public UsernameMessage(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
