package org.example.server;

import org.example.GameConfig;

public class Server {

    public Server() {
        GameConfig gameConfig = new GameConfig();
        PlayerQueue playerQueue = new PlayerQueue();
        // Creates matchmaker that runs within its own thread.
        Matchmaker matchmaker = new Matchmaker(playerQueue);
        // creates serverListener takes care of new connections.
        ServerListener serverListener = new ServerListener(gameConfig.getPort(), playerQueue);
    }

    void main() {
    }
}