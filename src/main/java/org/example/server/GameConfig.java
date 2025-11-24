package org.example.server;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

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

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTotalQuestionsPerRound() {
        return totalQuestionsPerRound;
    }

    public void setTotalQuestionsPerRound(int totalQuestionsPerRound) {
        this.totalQuestionsPerRound = totalQuestionsPerRound;
    }

    public int getTotalRoundsPerGame() {
        return totalRoundsPerGame;
    }

    public void setTotalRoundsPerGame(int totalRoundsPerGame) {
        this.totalRoundsPerGame = totalRoundsPerGame;
    }

    public String getPathToProperties() {
        return pathToProperties;
    }

    public void setPathToProperties(String pathToProperties) {
        this.pathToProperties = pathToProperties;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

    // Fält som resten av spelet använder
    private final int totalQuestionsPerRound;
    private final int totalRoundsPerGame;

    public GameConfig() {
        // Standardvärden om filen saknas eller är fel
        int questions = 3;
        int rounds = 6;



