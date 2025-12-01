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

            Player p1 = players.getFirst();
            Player p2 = players.getLast();

            // set opponents so ChatRouter can route messages
            p1.setOpponent(p2);
            p2.setOpponent(p1);

            // Start the game
            Game game = new Game(p1, p2);
            Thread t = new Thread(game);
            t.start();

            System.out.println("Game started");
        }
    }

}
