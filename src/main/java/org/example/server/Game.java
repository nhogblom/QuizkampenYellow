package org.example.server;

import org.example.GameConfig;
import org.example.Message;
import org.example.*;
import java.util.Random;

/**
 * Game
 *
 * This class controls the entire match between two players.
 * It runs inside its own thread (created by Matchmaker).
 *
 *  OVERVIEW
 *
 * Server responsibilities in a match:
 *  • Receive usernames from both players
 *  • Send MATCH_STARTED messages
 *  • Handle category selection (CATEGORY_CHOICE)
 *  • Send real QUESTION objects to both players
 *  • Receive answers (ANSWER)
 *  • Track scoring
 *  • Send ROUND_RESULT after each round
 *  • Send GAME_RESULT when the match ends
 *  • Handle CHAT messages at ANY time
 *
 *
 *  PROTOCOL (client <-> server)
 *
 * Server → Client:
 *   MATCH_STARTED(opponentName)
 *   QUESTION(Question object)
 *   ROUND_RESULT(String summary)
 *   GAME_RESULT(String summary)
 *   CATEGORY_CHOICE("WAITING" / chosenCategory)
 *   CHAT(String message)
 *
 * Client → Server:
 *   USERNAME(String)
 *   ANSWER(Answer object)
 *   CATEGORY_CHOICE(String)
 *   CHAT(String)
 *
 *  INTERNAL GAME FLOW
 *
 * 1) receiveUsernames()
 * 2) initGame() — send MATCH_STARTED
 * 3) For each round:
 *       a) request category from chooser
 *       b) wait for CATEGORY_CHOICE
 *       c) send QUESTION messages
 *       d) wait for ANSWER messages
 *       e) update scores
 *       f) send ROUND_RESULT
 * 4) endGame() — send GAME_RESULT
 *
 */
public class Game implements Runnable {

    private final Player player1;
    private final Player player2;
    private final GameConfig config;

    // score tracking for MVP
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    // question repo and category types~
    private final QuestionRepository questionRepo = new QuestionRepository();

    private final Random random = new Random();

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.config = new GameConfig();  // Reads settings from Game.properties
    }

    @Override
    public void run() {
        try {
            // Step 0: Get usernames first
            receiveUsernames();

            // Step 1: Start the match
            initGame();

            // Step 2: Play all configured rounds
            for (int round = 1; round <= config.getTotalRoundsPerGame(); round++) {
                playRound(round);
            }

            // Step 3: End game and send results
            endGame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveUsernames() {
        player1.setUsername(
                (String) player1.playerListener.getMessage().getPayload()
        );
        player2.setUsername(
                (String) player2.getPlayerListener().getMessage().getPayload()
        );
    }

    private void initGame() {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        // Let players know that the match has started and who the opponent is.
        player1.send(new Message(MessageTypes.MATCH_STARTED, player2.getUsername()));
        player2.send(new Message(MessageTypes.MATCH_STARTED, player1.getUsername()));
    }

    /**
     * Helper to get the next message from a player.
     * MVP: directly read from the socket.
     * Later: can be changed to use Player's internal queue.
     */
    private Object nextMessage(Player player) {
        return player.getPlayerListener().getMessage();
    }

    /**
     * This will later choose who selects the category.
     * MVP: only prints on console and sends info text.
     */
    private void promptForCategory(int round) throws Exception {

        // Send 3 categories to user

        System.out.println("promptForCategory() called for round " + round);

        // MVP placeholder: just inform both players whose turn it would be.

        Player chooser = (round % 2 == 1) ? player1 : player2;

        Message msg = new Message(MessageTypes.CATEGORY_CHOICE, new CategoryPrompt());

        chooser.send(msg);
        getOpponent(chooser).send(new Message(MessageTypes.CATEGORY_CHOICE, "Waiting for opponent to choose a category for round "));
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
        player1.send(new Message(MessageTypes.DEVELOPMENTMSG, "CATEGORY_CHOSEN: " + chosenCategory));
        player2.send(new Message(MessageTypes.DEVELOPMENTMSG, "CATEGORY_CHOSEN: " + chosenCategory));
    }

    /**
     * Plays a whole round:
     * - For each question:
     * - send question text to both
     * - receive answers
     * - update scores
     */
    private void playRound(int round) throws Exception {

        // Decides who chooses category
        Player chooser = (round % 2 == 1) ? player1 : player2;
        Player other   = (chooser == player1 ? player2 : player1);

        // Ask chooser to pick a category
        chooser.send(new Message(MessageTypes.CATEGORY_CHOICE, "CHOOSE_CATEGORY"));

        // Tell the other player to wait
        other.send(new Message(MessageTypes.CATEGORY_CHOICE, "WAITING"));

        // Block until chooser responds with CATEGORY_CHOICE
        String category = waitForCategoryChoice(chooser);

        // Inform both players of final category
        broadcast(new Message(MessageTypes.CATEGORY_CHOICE, category));

        // Play all questions for this round
        for (int i = 0; i < config.getTotalQuestionsPerRound(); i++) {
            playSingleQuestion(round, i + 1);
        }

        // After round ends, send round summary
        sendRoundResult(round);
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

        player1.send(new Message(MessageTypes.DEVELOPMENTMSG, questionText));
        player2.send(new Message(MessageTypes.DEVELOPMENTMSG, questionText));
    }

    private String waitForCategoryChoice(Player chooser) {

        while (true) {
            Message msg = chooser.getPlayerListener().getMessage();

            // Allow chat during category selection
            if (msg.getType() == MessageTypes.CHAT) {
                relayChat(chooser, msg);
                continue;
            }

            if (msg.getType() == MessageTypes.CATEGORY_CHOICE) {
                return (String) msg.getPayload();
            }
        }
    }
    private void playSingleQuestion(int round, int questionNumber) {

        // Generate a placeholder question for MVP
        Question q = generatePlaceholderQuestion(round, questionNumber);

        // Send actual QUESTION messages to both clients
        broadcast(new Message(MessageTypes.QUESTION, q));

        // Wait for both players to respond with ANSWER messages
        int answer1 = waitForAnswer(player1);
        int answer2 = waitForAnswer(player2);

        // MVP scoring
        if (answer1 == 0) scorePlayer1++;
        if (answer2 == 0) scorePlayer2++;
    }

    private Question generatePlaceholderQuestion(int round, int questionNumber) {
        String text = "Round " + round + ", Question " + questionNumber + ". What is correct?";
        String[] options = {"Correct", "Incorrect 1", "Incorrect 2", "Incorrect 3"};
        return new Question(text, options);
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
        player1.send(new Message(MessageTypes.DEVELOPMENTMSG, "QUESTION_RESULT R" + round + "Q" + questionNumber + ": "
                + (p1Correct ? "CORRECT" : "WRONG")));
        player2.send(new Message(MessageTypes.DEVELOPMENTMSG, "QUESTION_RESULT R" + round + "Q" + questionNumber + ": "
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
    private int waitForAnswer(Player player) {

        while (true) {
            Message msg = player.getPlayerListener().getMessage();

            switch (msg.getType()) {

                case CHAT:
                    // Chat must work allthe time
                    relayChat(player, msg);
                    continue;

                case ANSWER:
                    Answer a = (Answer) msg.getPayload();
                    return a.getChosenOption();

            }
        }
    }

    //   ROUND & GAME RESULTS

    private void sendRoundResult(int round) {
        String summary = "Round " + round + " results: "
                + player1.getUsername() + "=" + scorePlayer1 + ", "
                + player2.getUsername() + "=" + scorePlayer2;

        broadcast(new Message(MyMessageTypes.ROUND_RESULT, summary));
    }

    private void endGame() {
        String result;

        if (scorePlayer1 > scorePlayer2)
            result = player1.getUsername() + " wins!";
        else if (scorePlayer2 > scorePlayer1)
            result = player2.getUsername() + " wins!";
        else
            result = "It's a tie!";

        broadcast(new Message(MyMessageTypes.GAME_RESULT, result));
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

        player1.send(new Message(MyMessageTypes.DEVELOPMENTMSG, summary));
        player2.send(new Message(MyMessageTypes.DEVELOPMENTMSG, summary));
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

    //   CHAT SUPPORT

    private void relayChat(Player sender, Message msg) {
        Player receiver = (sender == player1 ? player2 : player1);
        String text = sender.getUsername() + ": " + msg.getPayload();
        receiver.send(new Message(MessageTypes.CHAT, text));
    }

    //   HELPERS
    private void broadcast(Message msg) {
        player1.send(msg);
        player2.send(msg);
    }

    /**
     * Sends a final summary to both players (MVP).
     */
    private void sendGameSummary() {
        System.out.println("sendGameSummary() called");

        player1.send(new Message(MessageTypes.DEVELOPMENTMSG, "GAME_SUMMARY: Your score = " + scorePlayer1 +
                ", Opponent score = " + scorePlayer2));

        player2.send(new Message(MessageTypes.DEVELOPMENTMSG, "GAME_SUMMARY: Your score = " + scorePlayer2 +
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
