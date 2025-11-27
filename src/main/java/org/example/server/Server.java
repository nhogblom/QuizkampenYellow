package org.example.server;

public class Server {

    public Server() {
        GameConfig gameConfig = new GameConfig();
        PlayerQueue playerQueue = new PlayerQueue();

        // Creates matchmaker that runs within its own thread.
        Matchmaker matchmaker = new Matchmaker(playerQueue);
        // creates serverListener takes care of new connections.
        ServerListener serverListener = new ServerListener(gameConfig.getPort(), playerQueue);
        //ServerListener serverListener = new ServerListener(55554, playerQueue);
    }

    void main() {

    }
}