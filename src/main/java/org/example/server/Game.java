package org.example.server;

import org.example.Player;

/**
 * Game controls the entire match between two players.
 * It runs in its own thread via Runnable.
 *
 * MVP Version:
 *  - Uses player.send(...) and player.receive() directly.
 *  - Sends simple String messages (will be replaced by Message classes later).
 *  - Handles rounds, score counting, and final game summary.
 *  - Future versions will consume messages from PlayerListener's queue.
 *
 * Game Flow (MVP):
 *  1. initGame()
 *  2. For each round:
 *     - promptForCategory()
 *     - promptCategoryChoice()
 *     - sendQuestions()
 *     - receiveAnswers()
 *     - sendRoundSummary()
 *  3. endGame()
 *  4. sendGameSummary()
 */

public class Game implements Runnable {

    private final Player player1;
    private final Player player2;
    private final GameConfig config;

    // Basic score tracking for MVP
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.config = new GameConfig();  // Reads values from Game.properties
    }

    /**
     * This is the main game loop.
     * It runs automatically when Matchmaker starts a new Game thread.
     */
    @Override
    public void run() {
        System.out.println("Starting new game between "
                + safeUsername(player1) + " and " + safeUsername(player2));

        System.out.println("Questions per round: " + config.getTotalQuestionsPerRound());
        System.out.println("Rounds per game: " + config.getTotalRoundsPerGame());

        try {
            // Step 1: Start game, send basic info to both players
            initGame();

            // Step 2: Loop through all configured rounds
            for (int round = 1; round <= config.getTotalRoundsPerGame(); round++) {
                System.out.println("=== Round " + round + " ===");

                // Step 2.1: Ask player to choose category (later)
                promptForCategory(round);

                // Step 2.2: Receive category choice from client (later)
                promptCategoryChoice(round);

                // Step 2.3: Send questions to both players
                sendQuestions(round);

                // Step 2.4: Receive their answers
                receiveAnswers(round);

                // Step 2.5: Process round result
                endRound(round);

                // Step 2.6: Send summary to both players
                sendRoundSummary(round);
            }

            // Step 3: After all rounds
            endGame();
            sendGameSummary();

        } catch (Exception e) {
            System.out.println("Error in Game.run(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * MVP version:
     * - Reset scores
     * - Send a simple "match started" message to both players
     */
    private void initGame() throws Exception {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        System.out.println("initGame() called");

        // MVP: we use simple Strings (later replaced with MatchFoundMessage)
        player1.send("MATCH_STARTED: You are playing against " + safeUsername(player2));
        player2.send("MATCH_STARTED: You are playing against " + safeUsername(player1));
    }

    /**
     * Placeholder for future message queue integration.
     * For MVP we still read directly from player.receive(),
     * but later this will take messages from Player's queue.
     */
    private Object nextMessage(Player player) {
        return player.receive();  // MVP fallback
    }


    /**
     * This will later choose who selects the category.
     * MVP: only prints on console.
     */
    private void promptForCategory(int round) throws Exception {
        System.out.println("promptForCategory() called for round " + round);

        // TODO (later):
        // - Decide which player chooses category
        // - Send a prompt message to that player
        // - Example preview: player1.send("CHOOSE_CATEGORY")
    }

    /**
     * Player sends back category choice to server.
     * MVP: no real logic yet.
     */
    private void promptCategoryChoice(int round) throws Exception {
        System.out.println("promptCategoryChoice() called for round " + round);

        // TODO (later):
        // - Object response = player.receive();
        // - Cast it to String or a future CategoryChoiceMessage
        // - Save it in a variable like currentCategory
    }

    /**
     * Sends a number of questions to both players.
     * MVP: just console logs.
     */
    private void sendQuestions(int round) throws Exception {
        System.out.println("sendQuestions() called for round " + round);

        // TODO (later):
        // - Get questions from QuestionRepository
        // - For MVP, send simple Strings like:
        //   player1.send("QUESTION: What is 2+2?;A:3;B:4;C:5;D:6")
    }

    /**
     * Receives answers from both players.
     * MVP: no real implementation yet.
     */
    private void receiveAnswers(int round) throws Exception {
        System.out.println("receiveAnswers() called for round " + round);

        // TODO (later):
        // - Use player.receive() for both players
        // - Convert to chosen answer
        // - Compare with correct answer
        // - Update scorePlayer1 / scorePlayer2
    }

    /**
     * Calculates end of round statistics.
     */
    private void endRound(int round) throws Exception {
        System.out.println("endRound() called for round " + round);
        System.out.println("Current scores after round " + round + ": "
                + safeUsername(player1) + "=" + scorePlayer1 + ", "
                + safeUsername(player2) + "=" + scorePlayer2);
    }

    /**
     * Sends a summary of the round to both players.
     */
    private void sendRoundSummary(int round) throws Exception {
        System.out.println("sendRoundSummary() called for round " + round);

        // TODO (later):
        // player1.send("ROUND_SUMMARY: p1=" + scorePlayer1 + ", p2=" + scorePlayer2);
        // player2.send("ROUND_SUMMARY: p1=" + scorePlayer1 + ", p2=" + scorePlayer2);
    }

    /**
     * Determines the winner based on total scores.
     * (MVP version: only console output)
     */
    private void endGame() {
        System.out.println("endGame() called");

        if (scorePlayer1 > scorePlayer2) {
            System.out.println("Winner: " + safeUsername(player1));
        } else if (scorePlayer2 > scorePlayer1) {
            System.out.println("Winner: " + safeUsername(player2));
        } else {
            System.out.println("The game ended in a tie.");
        }
    }


    /**
     * Sends a simple final summary to both players (MVP).
     */
    private void sendGameSummary() {
        System.out.println("sendGameSummary() called");

        // MVP: plain text messages
        player1.send("GAME_SUMMARY: Your score = " + scorePlayer1 +
                ", Opponent score = " + scorePlayer2);

        player2.send("GAME_SUMMARY: Your score = " + scorePlayer2 +
                ", Opponent score = " + scorePlayer1);
    }


    /**
     * method to avoid null usernames in logs.
     */
    private String safeUsername(Player player) {
        return player != null && player.getUsername() != null
                ? player.getUsername()
                : "Unknown";
    }
}
