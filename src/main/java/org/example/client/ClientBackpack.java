package org.example.client;

public class ClientBackpack {
    private boolean goToNextScreen;

    public ClientBackpack() {
        this.goToNextScreen = false;
    }

    public boolean isGoToNextScreen() {
        return goToNextScreen;
    }

    public void setGoToNextScreen(boolean goToNextScreen) {
        this.goToNextScreen = goToNextScreen;

    }

}
