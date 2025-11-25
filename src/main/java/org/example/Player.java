package org.example;

import org.example.server.Chat;
import org.example.server.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private List<Object> incommingGamePackets = new ArrayList<>();
    private Chat chat = new Chat();

    public Player(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;


        new Thread(() -> {
            while (true) {
                try {
                    Object incomming = objectInputStream.readObject();
                    if (incomming instanceof String s) {
                        incommingGamePackets.add(s);
                    } else if (incomming instanceof ChatMessage cm) {
                        chat.broadcast(cm);
                    }
                } catch (Exception e) {
                    System.out.println("Fel inträffade i inkommande dataström för spelare" + username + "\n" + e.getMessage());
                }
            }
        }).start();
    }

    public Object getGamePacket() {
        if (!incommingGamePackets.isEmpty()) {
        return incommingGamePackets.removeFirst();
        }else{
            return null;
        }
    }

    public Object receive() {
        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Object object) {
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

    public List<Object> getIncommingGamePackets() {
        return incommingGamePackets;
    }

    public void setIncommingGamePackets(List<Object> incommingGamePackets) {
        this.incommingGamePackets = incommingGamePackets;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

// TODO Representerar en ansluten spelare; lagrar användarnamn och kommunikationsströmmar.
