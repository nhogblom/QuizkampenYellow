package org.example.server;

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
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();


                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());


                Player player = new Player(socket, objectInputStream, objectOutputStream);


                playerQueue.add(player);
                System.out.println("Player created and added to queue.");

            }
        } catch (Exception e) {
            System.out.println("ServerListener Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
