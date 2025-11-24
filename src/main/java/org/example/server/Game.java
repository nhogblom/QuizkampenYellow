package org.example.server;

import org.example.Player;

public class Game implements Runnable {

    private final Player player1;
    private final Player player2;
    private final GameConfig config;  // <-- new

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.config = new GameConfig(); // <-- load settings once per game
    }

    @Override
    public void run() {
        // Temporary debug output to see that GameConfig works
        System.out.println("Starting new game:");
        System.out.println("Questions per round: " + config.getTotalQuestionsPerRound());
        System.out.println("Rounds per game: " + config.getTotalRoundsPerGame());

        initGame();

        // Basic game loop – structure only, logic added later
        for (int round = 1; round <= config.getTotalRoundsPerGame(); round++) {
            System.out.println("=== Round " + round + " ===");

            promptForCategory();
            promptCategoryChoice();

            sendQuestions();
            receiveAnswers();

            endRound();
            sendRoundSummary();
        }

        endGame();
        sendGameSummary();
    }

    private void initGame() {
        // TODO: reset scores, load questions etc.
        System.out.println("initGame() called");
    }

    private void promptForCategory() {
        // TODO: ask one player to choose category
        System.out.println("promptForCategory() called");
    }

    private void promptCategoryChoice() {
        // TODO: receive category choice from player
        System.out.println("promptCategoryChoice() called");
    }

    private void sendQuestions() {
        // TODO: send questions for this round to both players
        System.out.println("sendQuestions() called");
    }

    private void receiveAnswers() {
        // TODO: receive answers for all questions in this round
        System.out.println("receiveAnswers() called");
    }

    private void endRound() {
        // TODO: calculate round score
        System.out.println("endRound() called");
    }

    private void sendRoundSummary() {
        // TODO: send round result to both players
        System.out.println("sendRoundSummary() called");
    }

    private void endGame() {
        // TODO: calculate final result
        System.out.println("endGame() called");
    }

    private void sendGameSummary() {
        // TODO: send final result to both players
        System.out.println("sendGameSummary() called");
    }
}
