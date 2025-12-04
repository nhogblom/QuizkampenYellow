package org.example.server;

import org.example.Message;
import org.example.MessageTypes;

import java.util.List;

public class PlayerConnectionHandler extends Thread {
    private final Player player;
    private final List<Message> incomingMessagesQueue;

    public PlayerConnectionHandler(Player player, List<Message> incomingMessagesQueue) {
        this.player = player;
        this.incomingMessagesQueue = incomingMessagesQueue;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msg = player.receiveMessage();
                // CHAT MESSAGES are held here
                if (msg.getType() == MessageTypes.CHAT) {
                    System.out.println("CHAT message received: " + msg.getPayload());
                    ChatRouter.relay(player, msg.getPayload().toString());
                    // Cleanup & add to queue if message type play again.
                } else if (msg.getType() == MessageTypes.PLAYAGAIN) {
                    player.resetValuesForNewGameAndAddToQueue();
                } else {
                    // Other messages go into queue
                    synchronized (this) {
                        incomingMessagesQueue.add(msg);
                        notifyAll();
                    }
                }
            } catch (Exception e) {
                System.out.println("Client disconnected.");
                // Instead of just failing the game, we push a "synthetic" message
                // so Game - server logic can handle it like a normal event.
                synchronized (this) {
                    incomingMessagesQueue.add(
                            new Message(MessageTypes.DISCONNECTED_UNEXPECTEDLY, null)
                    );
                    notifyAll();
                }
                // Still remove player from the waiting queue if present
                player.getPlayerQueue().removePlayer(player);
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
