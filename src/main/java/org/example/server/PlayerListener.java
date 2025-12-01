package org.example.server;

import org.example.Message;
import org.example.MessageTypes;

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

                    // CHAT MESSAGES are held here
                    if (msg.getType() == MessageTypes.CHAT) {
                        System.out.println("CHAT message received: " + msg.getPayload());
                        ChatRouter.relay(player, msg.getPayload().toString());
                        continue;  // Do NOT queue chat messages for the game logic
                    }

                    // All other messages go into queue
                    synchronized (this) {
                        incomingMessages.add(msg);
                        notifyAll();
                    }

                } else {
                    // If incoming isn't a Message we ignore it
                }

            } catch (Exception e) {
                System.out.println("Error in incoming stream for player "
                        + player.getUsername() + ": " + e.getMessage());
                e.printStackTrace();
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

//    public Message receive() {
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
