package org.example.server;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {

    // Fält som resten av spelet använder
    private final int totalQuestionsPerRound;
    private final int totalRoundsPerGame;

    public GameConfig() {
        // Standardvärden om filen saknas eller är fel
        int questions = 3;
        int rounds = 6;



