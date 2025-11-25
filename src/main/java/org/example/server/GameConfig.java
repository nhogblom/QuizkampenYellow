package org.example.server;
import java.io.IOException;
import java.util.Properties;
import java.io.FileReader;
import java.net.InetAddress;


public class GameConfig {
    private InetAddress ip;
    private int port;
    private int totalQuestionsPerRound;
    private int totalRoundsPerGame;
    private String pathToProperties = "src/main/java/org/example/Game.properties";
    private Properties properties = new Properties();

    public GameConfig() {
        try {
            properties.load(new FileReader(pathToProperties));
            ip = InetAddress.getByName(properties.getProperty("ip"));
            port = Integer.parseInt(properties.getProperty("port"));
            totalQuestionsPerRound = Integer.parseInt(properties.getProperty("totalQuestionsPerRound"));
            totalRoundsPerGame = Integer.parseInt(properties.getProperty("totalRoundsPerGame"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }


    public int getTotalQuestionsPerRound() {
        return totalQuestionsPerRound;
    }


    public int getTotalRoundsPerGame() {
        return totalRoundsPerGame;
    }

}


