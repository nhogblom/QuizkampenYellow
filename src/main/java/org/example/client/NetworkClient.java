package org.example.client;

import org.example.Player;

import java.io.*;
import java.net.Socket;

public class NetworkClient {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 55554;

    public void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

            // Starta lyssnarthread
            new Thread(this::listen).start();
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    private void listen() {
        Object incommingObject;
        try {
            while ((incommingObject = in.readObject()) != null) {
                if (incommingObject instanceof String str) {
                System.out.println("Server: " + str);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        if (out != null) {
            try {
                out.writeObject(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disconnect() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
