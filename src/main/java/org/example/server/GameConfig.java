package org.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

/**
 * GameConfig loads game settings from the Game.properties file.
 * It reads values such as IP address, port, number of questions per round,
 * and number of rounds per game. The file is loaded from the classpath
 * (src/main/resources), which ensures that all machines in the project
 * use the same configuration without hardcoded paths.
 */


public class GameConfig {

    // Values loaded from Game.properties
    private InetAddress ip;
    private int port;
    private int totalQuestionsPerRound;
    private int totalRoundsPerGame;

    // Internal storage for the properties file
    private final Properties properties = new Properties();

    public GameConfig() {
        try (InputStream in = GameConfig.class
                .getClassLoader()
                .getResourceAsStream("Game.properties")) {

            // If this happens → the file is in the wrong folder
            if (in == null) {
                throw new RuntimeException("Game.properties not found on classpath");
            }

            // Load the file content
            properties.load(in);

            // Read values by key
            ip = InetAddress.getByName(properties.getProperty("ip"));
            port = Integer.parseInt(properties.getProperty("port"));
            totalQuestionsPerRound =
                    Integer.parseInt(properties.getProperty("totalQuestionsPerRound"));
            totalRoundsPerGame =
                    Integer.parseInt(properties.getProperty("totalRoundsPerGame"));

        } catch (IOException e) {
            // If loading fails, the entire server cannot start
            throw new RuntimeException("Failed to load Game.properties", e);
        }
    }

    // Getters and setters for Game and Server classes

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

    public Properties getProperties() {
        return properties;
    }
}
