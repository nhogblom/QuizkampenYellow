package org.example.server;

import org.example.Message;
import org.example.MessageTypes;

/**
 * ChatRouter
 *
 * Utility class responsible for routing chat messages
 * from one Player to their opponent.
 *
 */
public final class ChatRouter {

    // Utility class → no need to instantiate
    private ChatRouter() { }

    /**
     * Relay a chat message from sender to their opponent.
     *
     *  sender - The player who sent the message
     *  text -  The raw chat text (without name prefix)
     */
    public static void relay(Player sender, String text) {
        if (sender == null || text == null || text.isBlank()) {
            return;
        }

        Player receiver = sender.getOpponent();
        if (receiver == null) {
            System.out.println("ChatRouter: no opponent set for " + sender.getUsername());
            return;
        }

        //this sends to opponent

        String formatted = sender.getUsername() + ": " + text;
        receiver.sendMessage(new Message(MessageTypes.CHAT, formatted));

        //this sends back to sender to follow chat history
        sender.sendMessage(new Message(MessageTypes.CHAT, formatted));
    }
}
