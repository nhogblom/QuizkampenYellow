package org.example.server;

import org.example.Player;

import java.util.List;

public class Matchmaker extends Thread {
    private final PlayerQueue playerQueue;

    public Matchmaker(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
        this.start();
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            System.out.println("Waiting for players to start");
            List<Player> players = playerQueue.getPlayersForAGame();
            Game game = new Game(players.getFirst(),players.getLast());
            System.out.println("Game started");
        }
    }
}
