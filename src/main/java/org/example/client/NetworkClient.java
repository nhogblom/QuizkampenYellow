package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkClient {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

            // Starta lyssnarthread
            new Thread(this::listen).start();

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    private void listen() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println("Server: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        if (writer != null) {
            writer.println(msg);
        }
    }

    public void disconnect() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
