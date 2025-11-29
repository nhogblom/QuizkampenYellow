package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private String ipString;

    // Internal storage for the properties file
    private final Properties properties = new Properties();



    public GameConfig() {
        InputStream in = null;
        try {
            // 1) Försök först via classpath (det "rätta" sättet)
            in = GameConfig.class.getResourceAsStream("/Game.properties");
            System.out.println("Class URL: " + GameConfig.class.getResource("/Game.properties"));

            // 2) Om den är null → fallback till filsystemet (så det funkar i IntelliJ)
            if (in == null) {
                Path path = Paths.get("src", "main", "resources", "Game.properties");
                System.out.println("Classpath misslyckades, testar: " + path.toAbsolutePath());

                if (Files.exists(path)) {
                    in = Files.newInputStream(path);
                } else {
                    throw new RuntimeException("Game.properties not found on classpath or at "
                            + path.toAbsolutePath());
                }
            }
            // Load the file content
            properties.load(in);

            // Read values by key
            ipString = properties.getProperty("ip");
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

    public String getIpAsString() {
        return ipString;
    }
}
