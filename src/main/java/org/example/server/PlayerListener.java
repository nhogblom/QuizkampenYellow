package org.example.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PlayerListener extends Thread {
    private final Player player;
    private final List<Object> incomingGamePackets;

    public PlayerListener(Player player, List<Object> incomingGamePackets) {
        this.player = player;
        this.incomingGamePackets = incomingGamePackets;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object incoming = player.getObjectInputStream().readObject();
                if (incoming instanceof String s) {
                    player.getIncomingGamePackets().add(s);
                } else if (incoming instanceof ChatMessage cm) {
                    ;
                } else if (incoming instanceof Player) {

                }
            } catch (Exception e) {
                System.out.println("Fel inträffade i inkommande dataström för spelare" + player.getUsername() + "\n" + e.getMessage());
                this.interrupt();
            }
        }
    }

    public synchronized Object getGamePacket() {
        if (!incomingGamePackets.isEmpty()) {
            return incomingGamePackets.removeFirst();
        } else {
            return null;
        }
    }

    public Object receive() {
        try {
            return player.getObjectInputStream().readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Object object) {
        try {
            player.getObjectOutputStream().writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
