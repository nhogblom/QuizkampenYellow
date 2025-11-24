package org.example.server;

import org.example.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
    private final int port;
    private final PlayerQueue playerQueue;
    private final Matchmaker matchmaker;

    public ServerListener(int port, PlayerQueue playerQueue, Matchmaker matchmaker) {
        this.port = port;
        this.playerQueue = playerQueue;
        this.matchmaker = matchmaker;
        this.start();
    }

    @Override
    public void run() {
        // Continuously receives new connections from players and adds them to playerQueue.
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Player player = new Player(socket, objectInputStream, objectOutputStream);
                playerQueue.add(player);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//  TODO Lyssnar efter nya anslutningar. Skapar användare med användarnamn, socket, in- & out-ström.
