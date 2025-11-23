package org.example.server;

import org.example.Player;

import java.util.List;

public class Matchmaker extends Thread {
    private PlayerQueue playerQueue;

    public Matchmaker(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
        this.start();
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            List<Player> players = playerQueue.getPlayers();
            if (players != null) {
                System.out.println("Player Queue Size: " + players.size());
                Game game = new Game(players.getFirst(), players.getLast());
            } else {
                try {
                    System.out.println("Waiting for players to start");
                    wait(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

// TODO Separat tråd som väcks när ny spelare läggs i kö. Plockar ut de två som väntat längst och startar ett spel.
