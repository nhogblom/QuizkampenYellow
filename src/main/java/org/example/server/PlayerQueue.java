package org.example.server;

import org.example.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerQueue {
    private final List<Player> playerQueue = new ArrayList<>();

    public synchronized void add(Player player) {
        playerQueue.add(player);
        notifyAll();
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
        }
    }
}
