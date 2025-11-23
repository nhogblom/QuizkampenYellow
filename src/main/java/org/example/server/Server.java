package org.example.server;

public class Server {
    //todo ändra så att port tas från properties när en sådan är tillgänglig
    private int port = 55533;
    private PlayerQueue playerQueue = new PlayerQueue();


    public Server() {
        // Creates matchmaker that runs within its own thread.
        Matchmaker matchmaker = new Matchmaker(playerQueue);
        // creates serverListener takes care of new connections.
        ServerListener serverListener = new ServerListener(port, playerQueue, matchmaker);
    }

    void main() {

    }
}