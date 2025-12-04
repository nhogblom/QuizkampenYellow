package org.example.server;

import java.util.ArrayList;
import java.util.List;

public class PlayerQueue {
    private final List<Player> playerQueue = new ArrayList<>();

    public synchronized void addPlayer(Player player) {
        playerQueue.add(player);
        notifyAll();
    }

    public synchronized void removePlayer(Player player) {
        if  (playerQueue.contains(player)) {
        playerQueue.remove(player);
        notifyAll();
        }
    }

    public synchronized boolean enoughPlayersForAGame() {
        return !playerQueue.isEmpty() && playerQueue.size() >= 2;
    }

    public synchronized List<Player> getPlayersForAGame() {
        List<Player> players = new ArrayList<>();
        while (true) {
            if (enoughPlayersForAGame()) {
                players.add(playerQueue.removeFirst());
                players.add(playerQueue.removeFirst());
                return players;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
