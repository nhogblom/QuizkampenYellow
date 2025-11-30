package org.example.server;

import org.example.Message;

import java.io.IOException;
import java.util.List;

public class PlayerListener extends Thread {
    private final Player player;
    private final List<Message> incomingMessages;

    public PlayerListener(Player player, List<Message> incomingMessages) {
        this.player = player;
        this.incomingMessages = incomingMessages;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object incoming = player.getObjectInputStream().readObject();
                if (incoming instanceof Message msg) {
                    incomingMessages.add(msg);
                    notifyAll();
                }
            } catch (Exception e) {
                System.out.println("Fel inträffade i inkommande dataström för spelare" + player.getUsername() + "\n" + e.getMessage());
                this.interrupt();
                break;
            }
        }
    }

    public synchronized Message getMessage() {
        while (true) {
        if (!incomingMessages.isEmpty()) {
            return incomingMessages.removeFirst();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        }
    }

//    public synchronized Message receive() {
//        try {
//            return (Message) player.getObjectInputStream().readObject();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void send(Object object) {
        try {
            player.getObjectOutputStream().writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
