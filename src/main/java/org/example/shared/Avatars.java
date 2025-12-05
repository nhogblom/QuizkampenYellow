package org.example.shared;

public enum Avatars {
    FOX("src/main/resources/images/avatar2.png"),
    PIKACHU("src/main/resources/images/avatar3.png"),
    DUCK("src/main/resources/images/avatar1.png");

    private String pathToAvatarImage;


    Avatars(String pathToAvatarImage) {
        this.pathToAvatarImage = pathToAvatarImage;
    }

    public String getPathToAvatarImage() {
        return pathToAvatarImage;
    }

    public void setPathToAvatarImage(String pathToAvatarImage) {
        this.pathToAvatarImage = pathToAvatarImage;
    }
}
