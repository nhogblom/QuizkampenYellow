package org.example.server;

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
    private Chat chat = new Chat();
    PlayerListener playerListener;

    public Player(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;

         playerListener = new PlayerListener(this,incomingMessages);
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

    public  Object receive() {
        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message object) {
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public List<Message> getIncomingGamePackets() {
        return incomingMessages;
    }

    public void setIncomingGamePackets(List<Message> incomingGamePackets) {
        this.incomingMessages = incomingGamePackets;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

// TODO Representerar en ansluten spelare; lagrar användarnamn och kommunikationsströmmar.
