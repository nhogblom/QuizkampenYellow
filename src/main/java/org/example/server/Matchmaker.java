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
    public synchronized void run() {
        while (!this.isInterrupted()) {
            if (playerQueue.enoughPlayersForAGame()) {
                List<Player> players = playerQueue.getPlayers();
                Game game = new Game(players.getFirst(), players.getLast());
            } else {
                try {
                    System.out.println("Waiting for players to start");
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

// TODO Separat tråd som väcks när ny spelare läggs i kö. Plockar ut de två som väntat längst och startar ett spel.
