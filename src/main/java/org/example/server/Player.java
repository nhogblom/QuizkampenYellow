package org.example.server;

import org.example.Message;
import org.example.GameResult;

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
    private PlayerListener playerListener;
    private GameResult gameResult = new GameResult();
    private Player opponent;
    private PlayerQueue playerQueue;


    public Player(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, PlayerQueue playerQueue) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.playerQueue = playerQueue;
        this.playerListener = new PlayerListener(this,incomingMessages);
    }

    public PlayerListener getPlayerListener() {
        return playerListener;
    }

    public void setPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
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
}
