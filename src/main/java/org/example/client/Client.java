package org.example.client;

import org.example.client.panels.CategoryPanel;
import org.example.client.panels.MainWindow;
import org.example.client.panels.QuestionPanel;
import org.example.client.panels.RoundSummaryPanel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });


        NetworkClient client = new NetworkClient("Det här är ett namn");
        client.connect();

        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = console.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) break;
                client.sendMessage(new Message(MyMessageTypes.CHAT,input));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.disconnect();
        }
    }
}
