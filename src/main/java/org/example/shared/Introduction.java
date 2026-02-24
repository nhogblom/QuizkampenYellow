package org.example.shared;

import java.io.Serializable;

public class Introduction implements Serializable {
    private String username;
    private Avatars avatar;

    public Introduction(String username, Avatars avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Avatars getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatars avatar) {
        this.avatar = avatar;
    }
}
