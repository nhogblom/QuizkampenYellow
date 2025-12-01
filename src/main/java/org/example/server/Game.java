package org.example.server;

import org.example.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game
 * <p>
 * This class controls the entire match between two players.
 * It runs inside its own thread (created by Matchmaker).
 * <p>
 * OVERVIEW
 * <p>
 * Server responsibilities in a match:
 * • Receive usernames from both players
 * • Send MATCH_STARTED messages
 * • Handle category selection (CATEGORY_CHOICE)
 * • Send real QUESTION objects to both players
 * • Receive answers (ANSWER)
 * • Track scoring
 * • Send ROUND_RESULT after each round
 * • Send GAME_RESULT when the match ends
 * • Handle CHAT messages at ANY time
 * <p>
 * <p>
 * PROTOCOL (client <-> server)
 * <p>
 * Server → Client:
 * MATCH_STARTED(opponentName)
 * QUESTION(Question object)
 * ROUND_RESULT(String summary)
 * GAME_RESULT(String summary)
 * CATEGORY_CHOICE("WAITING" / chosenCategory)
 * CHAT(String message)
 * <p>
 * Client → Server:
 * USERNAME(String)
 * ANSWER(Answer object)
 * CATEGORY_CHOICE(String)
 * CHAT(String)
 * <p>
 * INTERNAL GAME FLOW
 * <p>
 * 1) receiveUsernames()
 * 2) initGame() — send MATCH_STARTED
 * 3) For each round:
 * a) request category from chooser
 * b) wait for CATEGORY_CHOICE
 * c) send QUESTION messages
 * d) wait for ANSWER messages
 * e) update scores
 * f) send ROUND_RESULT
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
    private List<QuizQuestion> questionsForThisRound;

    GameResult gameResult = new GameResult();
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
                (String) player1.playerListener.getMessageFromQueue().getPayload()
        );
        player2.setUsername(
                (String) player2.getPlayerListener().getMessageFromQueue().getPayload()
        );
    }

    private void initGame() {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        // Let players know that the match has started and who the opponent is.
        player1.send(new Message(MessageTypes.MATCH_STARTED, player2.getUsername()));
        player2.send(new Message(MessageTypes.MATCH_STARTED, player1.getUsername()));
    }


    private void playRound(int round) throws Exception {

        // Decides who chooses category
        Player chooser = (round % 2 == 1) ? player1 : player2;
        Player other = (chooser == player1 ? player2 : player1);

        // Ask chooser to pick a category
        chooser.send(new Message(MessageTypes.CATEGORY_CHOICE, new CategoryPrompt()));

        // Tell the other player to wait
        other.send(new Message(MessageTypes.CATEGORY_CHOICE, "Waiting for opponent to choose a category for round "));

        // Receive category from chooser
        QuizCategory currentCategory = receiveCategoryChoice(chooser);

        // Send each player the questions for this round.
        questionsForThisRound = questionRepo.getRandomQuestions(currentCategory, config.getTotalQuestionsPerRound());


        RoundResult rr = new RoundResult(round);

        // Play all questions for this round
        for (int i = 0; i < questionsForThisRound.size(); i++) {
            playSingleQuestion(round, i + 1, questionsForThisRound.get(i));
        }

        // After round ends, send round summary
        sendRoundResult(round);
    }

    /**
     * Sends a single placeholder question to both players.
     * Later I replace this with real questions from QuestionRepository.
     */


    private QuizCategory receiveCategoryChoice(Player chooser) {
        Message msg = chooser.getPlayerListener().getMessageFromQueue();
        if (msg.getType() == MessageTypes.CATEGORY_CHOICE) {
            return (QuizCategory) msg.getPayload();
        }
        return null;
    }


    private void playSingleQuestion(int round, int questionNumber, QuizQuestion question) {

        ///  add RoundResult to later receive our results.
        player1.getGameResult().addRoundResult(round);
        player2.getGameResult().addRoundResult(round);

        // Send actual QUESTION messages to both clients
        broadcast(new Message(MessageTypes.QUESTION, question));

        // Wait for both players to respond with ANSWER messages
        String answer1 = collectAnswer(player1);
        String answer2 = collectAnswer(player2);

        // add round result
        if ((answer1.equals(question.getCorrectAnswer()))) {
            player1.getGameResult().getRoundResult(round).addResult(true);
        } else {
            player1.getGameResult().getRoundResult(round).addResult(false);
        }
        if (answer2.equals(question.getCorrectAnswer())) {
            player2.getGameResult().getRoundResult(round).addResult(true);
        } else {
            player2.getGameResult().getRoundResult(round).addResult(false);
        }
    }




    private String collectAnswer(Player player) {
        Message msg = player.getPlayerListener().getMessageFromQueue();
        if (msg.getPayload() instanceof QuizCategory) {
            System.out.println(((QuizCategory) msg.getPayload()).getName());
        }
        return (String) msg.getPayload();
    }


    //   ROUND & GAME RESULTS

    private void sendRoundResult(int round) {
        List<RoundResult> roundResultsForPlayer1 = new ArrayList<RoundResult>();
        roundResultsForPlayer1.add(player1.getGameResult().getRoundResult(round));
        roundResultsForPlayer1.add(player2.getGameResult().getRoundResult(round));

        List<RoundResult> roundResultsForPlayer2 = new ArrayList<RoundResult>();
        roundResultsForPlayer2.add(player2.getGameResult().getRoundResult(round));
        roundResultsForPlayer2.add(player1.getGameResult().getRoundResult(round));

        player1.send(new Message(MessageTypes.ROUND_RESULT, roundResultsForPlayer1));
        player2.send(new Message(MessageTypes.ROUND_RESULT, roundResultsForPlayer2));
    }


    /**
     * Determines the winner based on total scores.
     * MVP version: only console output.
     */
    // todo fix this logic. :(

    private void endGame() {
        String result;

        if (player1.getGameResult().getScorePlayer1() > player2.getGameResult().getScorePlayer2())
            result = player1.getUsername() + " wins!";
        else if (player1.getGameResult().getScorePlayer1() < player2.getGameResult().getScorePlayer2())
            result = player2.getUsername() + " wins!";
        else
            result = "It's a tie!";

        broadcast(new Message(MessageTypes.GAME_RESULT, result));
    }


    //   HELPERS
    private void broadcast(Message msg) {
        player1.send(msg);
        player2.send(msg);
    }



    private Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }
}
