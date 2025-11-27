package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient();
        client.connect();

        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = console.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) break;
                client.sendMessage(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.disconnect();
        }
    }
}
