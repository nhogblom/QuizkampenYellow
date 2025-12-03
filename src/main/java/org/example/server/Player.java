package org.example.server;

import org.example.GameResult;
import org.example.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private String username;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private List<Message> incomingMessages = new LinkedList<>();
    private PlayerConnectionHandler playerConnectionHandler;
    private GameResult gameResult = new GameResult();
    private Player opponent;
    private PlayerQueue playerQueue;

    public Player(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, PlayerQueue playerQueue) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.playerQueue = playerQueue;
        this.playerConnectionHandler = new PlayerConnectionHandler(this, incomingMessages);
    }

    public PlayerConnectionHandler getPlayerListener() {
        return playerConnectionHandler;
    }

    public void setPlayerListener(PlayerConnectionHandler playerConnectionHandler) {
        this.playerConnectionHandler = playerConnectionHandler;
    }

    public synchronized Message getMessage() {
        if (!incomingMessages.isEmpty()) {
            return incomingMessages.removeFirst();
        } else {
            return null;
        }
    }

    public void sendMessage(Message object) {
        try {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    /**
     * Opponent reference used for routing chat messages between players.
     */

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    public void resetValuesForNewGameAndAddToQueue() {
        this.opponent = null;
        this.gameResult = new GameResult();
        this.getPlayerQueue().addPlayer(this);
    }

}
