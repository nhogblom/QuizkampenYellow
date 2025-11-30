package org.example.client;

import org.example.client.panels.CategoryPanel;
import org.example.client.panels.MainWindow;
import org.example.client.panels.QuestionPanel;
import org.example.client.panels.RoundSummaryPanel;

import javax.swing.*;

public class Client {
    private ClientBackpack backpack = new ClientBackpack();
    private CategoryPanel categoryPanel = new CategoryPanel(backpack);
    private QuestionPanel questionPanel = new QuestionPanel(backpack);
    private RoundSummaryPanel roundSummaryPanel = new RoundSummaryPanel(backpack);

    //TODO lägga in alla paneler här och sen bara styra om dom syns eller ej senare i programmet.

    public Client() {
        backpack.setCategoryPanel(categoryPanel);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow(backpack).setVisible(true);
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
