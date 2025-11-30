package org.example.server;

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

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

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

    //   RECEIVE USERNAMES

    private void receiveUsernames() {
        Message m1 = player1.getPlayerListener().getGamePacket();
        Message m2 = player2.getPlayerListener().getGamePacket();

        if (m1.getType() == MyMessageTypes.USERNAME)
            player1.setUsername((String) m1.getPayload());

        if (m2.getType() == MyMessageTypes.USERNAME)
            player2.setUsername((String) m2.getPayload());
    }

    //   INIT GAME (send MATCH_STARTED)

    private void initGame() {
        scorePlayer1 = 0;
        scorePlayer2 = 0;

        // Let players know that the match has started and who the opponent is.
        player1.send(new Message(MyMessageTypes.MATCH_STARTED, player2.getUsername()));
        player2.send(new Message(MyMessageTypes.MATCH_STARTED, player1.getUsername()));
    }

    //   ROUND FLOW

    private void playRound(int round) throws Exception {

        // Decides who chooses category
        Player chooser = (round % 2 == 1) ? player1 : player2;
        Player other   = (chooser == player1 ? player2 : player1);

        // Ask chooser to pick a category
        chooser.send(new Message(MyMessageTypes.CATEGORY_CHOICE, "CHOOSE_CATEGORY"));

        // Tell the other player to wait
        other.send(new Message(MyMessageTypes.CATEGORY_CHOICE, "WAITING"));

        // Block until chooser responds with CATEGORY_CHOICE
        String category = waitForCategoryChoice(chooser);

        // Inform both players of final category
        broadcast(new Message(MyMessageTypes.CATEGORY_CHOICE, category));

        // Play all questions for this round
        for (int i = 0; i < config.getTotalQuestionsPerRound(); i++) {
            playSingleQuestion(round, i + 1);
        }

        // After round ends, send round summary
        sendRoundResult(round);
    }

    private String waitForCategoryChoice(Player chooser) {

        while (true) {
            Message msg = chooser.getPlayerListener().getGamePacket();

            // Allow chat during category selection
            if (msg.getType() == MyMessageTypes.CHAT) {
                relayChat(chooser, msg);
                continue;
            }

            if (msg.getType() == MyMessageTypes.CATEGORY_CHOICE) {
                return (String) msg.getPayload();
            }
        }
    }

    //   QUESTION FLOW

    private void playSingleQuestion(int round, int questionNumber) {

        // Generate a placeholder question for MVP
        Question q = generatePlaceholderQuestion(round, questionNumber);

        // Send actual QUESTION messages to both clients
        broadcast(new Message(MyMessageTypes.QUESTION, q));

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

    private int waitForAnswer(Player player) {

        while (true) {
            Message msg = player.getPlayerListener().getGamePacket();

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

    //   CHAT SUPPORT

    private void relayChat(Player sender, Message msg) {
        Player receiver = (sender == player1 ? player2 : player1);
        String text = sender.getUsername() + ": " + msg.getPayload();
        receiver.send(new Message(MyMessageTypes.CHAT, text));
    }

    //   HELPERS
    private void broadcast(Message msg) {
        player1.send(msg);
        player2.send(msg);
    }
}
