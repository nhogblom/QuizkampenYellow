package org.example.server;

import org.example.Player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
    private final int port;
    private final PlayerQueue playerQueue;

    public ServerListener(int port, PlayerQueue playerQueue) {
        this.port = port;
        this.playerQueue = playerQueue;
        this.start();
    }

    @Override
    public void run() {
        // Continuously receives new connections from players and adds them to playerQueue.
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                System.out.println("ServerListener: Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("ServerListener: Connection accepted");
                System.out.println("Creating ObjectOutputStream...");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();
                System.out.println("ObjectOutputStream created");
                System.out.println("Creating ObjectInputStream...");

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("ObjectInputStream created");
                System.out.println("Creating Player...");

                Player player = new Player(socket, objectInputStream, objectOutputStream);
                System.out.println("Player created, adding to queue...");

                playerQueue.add(player);
                System.out.println("Player added");

                System.out.println("ServerListener: connection established.");
            }
        } catch (Exception e) {
            System.out.println("ServerListener Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

//  TODO Lyssnar efter nya anslutningar. Skapar användare med användarnamn, socket, in- & out-ström.
