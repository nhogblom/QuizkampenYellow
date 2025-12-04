package org.example.server;

import org.example.shared.GameResult;
import org.example.shared.Message;
import org.example.shared.RoundResult;

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

    // flag so we don't try to disconnect / clean up multiple times
    private boolean disconnected = false;

    public Player(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, PlayerQueue playerQueue) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.playerQueue = playerQueue;
        this.playerConnectionHandler = new PlayerConnectionHandler(this, incomingMessages);
    }

    public PlayerConnectionHandler getPlayerConnectionHandler() {
        return playerConnectionHandler;
    }

    public Message getMessageFromQueue(){
        return playerConnectionHandler.getMessageFromQueue();
    }

    public void addRoundResult(int round) {
        gameResult.addRoundResult(round);
    }


    public void sendMessage(Message object) {
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Failed to send message to " + username + " — client probably disconnected.");
            handleUnexpectedDisconnect();   // I hope this works...
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public Message receiveMessage() throws IOException, ClassNotFoundException {
        Object incoming = objectInputStream.readObject();
        if (incoming instanceof Message) {
            return (Message) incoming;
        }else{
            return null;
        }
    }

    public GameResult getGameResult() {
        return gameResult;
    }
    public void addResult(int round,boolean result) {
        gameResult.getRoundResult(round).addResult(result);
    }

    public RoundResult getRoundResult(int round) {
       return gameResult.getRoundResult(round);
    }

    public int getEndGameResult() {
        return gameResult.getGameResult();
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

    public synchronized void removePlayerFromQueue(){
        playerQueue.removePlayer(this);
    }


    public void resetValuesForNewGameAndAddToQueue() {
        this.opponent = null;
        this.gameResult = new GameResult();
        this.getPlayerQueue().addPlayer(this);
    }

    /**
     * Called when the server detects that this player has unexpectedly disconnected.
     * Responsible for cleaning up resources and removing the player from any queues.
     */
    public synchronized void handleUnexpectedDisconnect() {
        if (disconnected) {
            return;
        }
        disconnected = true;

        System.out.println("Handling unexpected disconnect for player: " + username);

        // Remove from the matchmaking queue if present
        if (playerQueue != null) {
            playerQueue.removePlayer(this);
        }

        // Here is where we later notify Game.java if we store a reference there.

        // Clean up network resources on the server side
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException ignored) {
        }

        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException ignored) {
        }

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
    }
}
