package org.example.client;

import org.example.client.panels.MainWindow;

import javax.swing.*;

public class Client {
    private Flag moveToNextUI = new Flag();

    public Client() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow(moveToNextUI).setVisible(true);
            }
        });
    }

    void main() {




//        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
//            String input;
//            while ((input = console.readLine()) != null) {
//                if (input.equalsIgnoreCase("exit")) break;
//                client.sendMessage(new Message(MyMessageTypes.CHAT,input));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            client.disconnect();
//        }
    }
}
