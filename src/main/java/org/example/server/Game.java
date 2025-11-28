package org.example.server;

import org.example.GameConfig;
import org.example.Message;
import org.example.MyMessageTypes;

/**
 * Game controls the entire match between two players.
 * It runs in its own thread via Runnable.
 *
 * MVP Version:
 *  - Uses player.send(...) and player.receive() directly.
 *  - Sends String messages.
 *  - Handles rounds, score counting, and final game summary.
 *
 * Game Flow (MVP):
 *  1. initGame()
 *  2. For each round:
 *     - promptForCategory()
 *     - promptCategoryChoice()
 *     - sendQuestions()
 *     - receiveAnswers()
 *     - endRound()
 *     - sendRoundSummary()
 *  3. endGame()
 *  4. sendGameSummary()
 *
 * NOTE: QuestionRepository / QuizQuestion are NOT used yet.
 *       All questions are simple placeholder strings.
 */
public class Game implements Runnable {

    private final Player player1;
    private final Player player2;
    private final GameConfig config;

    // score tracking for MVP
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.config = new GameConfig();  // Reads values from Game.properties
    }

    /**
     * The main game loop.
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

                // Step 2.1: Ask player to choose category (placeholder)
                promptForCategory(round);

                // Step 2.2: Receive category choice from client (placeholder)
                promptCategoryChoice(round);

                // Step 2.3 & 2.4: Send questions and receive answers
                playRound(round);

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
     * - Send a "match started" message to both players
     */
    private void initGame() throws Exception {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        System.out.println("initGame() called");

        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"MATCH_STARTED: You are playing against " + safeUsername(player2)));
        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"MATCH_STARTED: You are playing against " + safeUsername(player1)));
    }

    /**
     * Helper to get the next message from a player.
     * MVP: directly read from the socket.
     * Later: can be changed to use Player's internal queue.
     */
    private Object nextMessage(Player player) {
        return player.receive();  // MVP fallback
    }

    /**
     * This will later choose who selects the category.
     * MVP: only prints on console and sends info text.
     */
    private void promptForCategory(int round) throws Exception {
        System.out.println("promptForCategory() called for round " + round);

        // MVP placeholder: just inform both players whose turn it would be.

        Player chooser = (round % 2 == 1) ? player1 : player2;

        chooser.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"CHOOSE_CATEGORY: It is your turn to choose a category for round " + round));
        getOpponent(chooser).send(new Message(MyMessageTypes.DEVELOPMENTMSG,"INFO: Waiting for opponent to choose a category for round " + round));
    }

    /**
     * Player sends back category choice to server.
     * MVP:  read a single String and log it.
     */
    private void promptCategoryChoice(int round) throws Exception {
        System.out.println("promptCategoryChoice() called for round " + round);

        Player chooser = (round % 2 == 1) ? player1 : player2;

        Object response = nextMessage(chooser);
        String chosenCategory = (response instanceof String) ? (String) response : "UNKNOWN";

        System.out.println("Round " + round + " chosen category by "
                + safeUsername(chooser) + ": " + chosenCategory);

        // Inform both players which category was chosen (even if it's just a placeholder).
        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"CATEGORY_CHOSEN: " + chosenCategory));
        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"CATEGORY_CHOSEN: " + chosenCategory));
    }

    /**
     * Plays a whole round:
     * - For each question:
     *   - send question text to both
     *   - receive answers
     *   - update scores
     */
    private void playRound(int round) throws Exception {
        int questionsPerRound = config.getTotalQuestionsPerRound();

        for (int qIndex = 1; qIndex <= questionsPerRound; qIndex++) {
            sendQuestion(round, qIndex);
            receiveAndScoreAnswers(round, qIndex);
        }
    }

    /**
     * Sends a single placeholder question to both players.
     * Later I replace this with real questions from QuestionRepository.
     */
    private void sendQuestion(int round, int questionNumber) throws Exception {
        System.out.println("sendQuestion() called for round " + round
                + ", question " + questionNumber);

        String questionText = "QUESTION R" + round + "Q" + questionNumber + ": "
                + "This is a placeholder question. Answer with '1', '2', '3', or '4'. "
                + "Correct answer is '1' in MVP.";

        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG,questionText));
        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG,questionText));
    }

    /**
     * Receives answers from both players for a single question and updates scores.
     * MVP rule: if the player answers exactly "1" → +1 point.
     */
    private void receiveAndScoreAnswers(int round, int questionNumber) throws Exception {
        System.out.println("receiveAndScoreAnswers() called for round " + round
                + ", question " + questionNumber);

        // Receive
        String answerP1 = readStringAnswer(player1);
        String answerP2 = readStringAnswer(player2);

        // MVP scoring: "1" is always the correct answer.
        boolean p1Correct = "1".equals(answerP1);
        boolean p2Correct = "1".equals(answerP2);

        if (p1Correct) {
            scorePlayer1++;
        }
        if (p2Correct) {
            scorePlayer2++;
        }

        // Inform players about correctness of this question
        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"QUESTION_RESULT R" + round + "Q" + questionNumber + ": "
                + (p1Correct ? "CORRECT" : "WRONG")));
        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"QUESTION_RESULT R" + round + "Q" + questionNumber + ": "
                + (p2Correct ? "CORRECT" : "WRONG")));

        System.out.println("Answers for R" + round + "Q" + questionNumber
                + " -> " + safeUsername(player1) + ": " + answerP1 + " (" + (p1Correct ? "correct" : "wrong") + "), "
                + safeUsername(player2) + ": " + answerP2 + " (" + (p2Correct ? "correct" : "wrong") + ")");
    }

    private String readStringAnswer(Player player) {
        Object o = nextMessage(player);
        if (o instanceof String s) {
            return s;
        }
        return "";
    }

    /**
     * Calculates end of round statistics.
     */
    private void endRound(int round) {
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

        String summary = "ROUND_SUMMARY R" + round + ": "
                + safeUsername(player1) + "=" + scorePlayer1 + ", "
                + safeUsername(player2) + "=" + scorePlayer2;

        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG,summary));
        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG,summary));
    }

    /**
     * Determines the winner based on total scores.
     * MVP version: only console output.
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
     * Sends a final summary to both players (MVP).
     */
    private void sendGameSummary() {
        System.out.println("sendGameSummary() called");

        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"GAME_SUMMARY: Your score = " + scorePlayer1 +
                ", Opponent score = " + scorePlayer2));

        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG,"GAME_SUMMARY: Your score = " + scorePlayer2 +
                ", Opponent score = " + scorePlayer1));
    }

    /**
     * method to avoid null usernames in logs.
     */
    private String safeUsername(Player player) {
        return player != null && player.getUsername() != null
                ? player.getUsername()
                : "Unknown";
    }

    private Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }
}
