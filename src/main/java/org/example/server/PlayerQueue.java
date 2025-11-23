package org.example.server;

import org.example.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PlayerQueue {
    private List<Player> playerQueue = new ArrayList<>();

    public void add(Player player) {
        playerQueue.add(player);
    }

    public boolean enoughPlayersForAGame(){
        return !playerQueue.isEmpty() && playerQueue.size() >= 2;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        if (playerQueue != null && playerQueue.size() >= 2){
            players.add(playerQueue.removeFirst());
            players.add(playerQueue.removeFirst());
            return players;
        }else{
            return null;
        }
    }

    //TODO Håller spelare som väntar på en match och låter Matchmakern plocka ut två spelare åt gången.
}
