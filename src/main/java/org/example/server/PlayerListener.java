package org.example.server;

import org.example.Message;
import org.example.MessageTypes;

import java.io.IOException;
import java.util.List;

public class PlayerListener extends Thread {
    private final Player player;
    private final List<Message> incomingMessagesQueue;

    public PlayerListener(Player player, List<Message> incomingMessagesQueue) {
        this.player = player;
        this.incomingMessagesQueue = incomingMessagesQueue;
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
                        incomingMessagesQueue.add(msg);
                        notifyAll();
                    }
                }
            } catch (Exception e) {
                System.out.println("Client disconnected.");
                player.getPlayerQueue().remove(player);
                this.interrupt();
                break;
            }
        }
    }
    public synchronized Message getMessageFromQueue() {
        while (true) {
            if (!incomingMessagesQueue.isEmpty()) {
                return incomingMessagesQueue.removeFirst();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


