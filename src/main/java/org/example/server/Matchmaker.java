package org.example.server;

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
            System.out.println("Matchmaker: Waiting for players to start");
            List<Player> players = playerQueue.getPlayersForAGame();
            Game game = new Game(players.getFirst(),players.getLast());
            Thread t = new Thread(game);
            t.start();
            System.out.println("Game started");
        }
    }
}
