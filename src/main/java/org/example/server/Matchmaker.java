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
            Game game = new Game(players.getFirst(),players.getFirst());
            System.out.println("Game started");
        }
    }
}


// TODO Separat tråd som väcks när ny spelare läggs i kö. Plockar ut de två som väntat längst och startar ett spel.
