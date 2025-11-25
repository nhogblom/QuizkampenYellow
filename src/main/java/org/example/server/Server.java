package org.example.server;

public class Server {
    private final PlayerQueue playerQueue = new PlayerQueue();
    private GameConfig gameConfig = new GameConfig();

    public Server() {
        // Creates matchmaker that runs within its own thread.
        Matchmaker matchmaker = new Matchmaker(playerQueue);
        // creates serverListener takes care of new connections.
        ServerListener serverListener = new ServerListener(gameConfig.getPort(), playerQueue);
    }

    void main() {

    }
}