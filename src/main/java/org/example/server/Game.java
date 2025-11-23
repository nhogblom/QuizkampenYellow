package org.example.server;

public class Game implements Runnable {

    private final Player player1;
    private final Player player2;

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    @Override
    public void run() {
        // TODO: implement game flow
    }
}
