package org.example.server;

import org.example.*;

import java.util.ArrayList;
import java.util.List;

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


    // question repo and category types~
    private final QuestionRepository questionRepo = new QuestionRepository();
    private List<QuizQuestion> questionsForThisRound;

    // flag to mark that the game was aborted due to unexpected disconnect
    private volatile boolean gameAborted = false;

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.config = new GameConfig();  // Reads settings from Game.properties
    }

    @Override
    public void run() {
        try {
            //  Get usernames first
            receiveUsernames();
            if (gameAborted) {
                return;
            }

            //  Start the match
            initGame();
            if (gameAborted) {
                return;
            }

            // Play all configured rounds
            for (int round = 1; round <= config.getTotalRoundsPerGame(); round++) {
                playRound(round);
                if (gameAborted) {
                    return;
                }
            }

            // End game and send results
            endGame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveUsernames() {
        String username1 = waitForUsername(player1);
        if (gameAborted || username1 == null) {
            return;
        }
        player1.setUsername(username1);

        String username2 = waitForUsername(player2);
        if (gameAborted || username2 == null) {
            return;
        }
        player2.setUsername(username2);
    }

    private String waitForUsername(Player player) {
        while (true) {
            Message msg = player.getPlayerConnectionHandler().getMessageFromQueue();

            if (msg.getType() == MessageTypes.USERNAME) {
                return (String) msg.getPayload();
            } else if (msg.getType() == MessageTypes.DISCONNECTED_UNEXPECTEDLY) {
                handleUnexpectedDisconnect(player);
                return null;
            } else {
                System.out.println("Unexpected message while waiting for USERNAME: " + msg.getType());
            }
        }
    }

    private void initGame() {
        // Let players know that the match has started and who the opponent is.
        player1.sendMessage(new Message(MessageTypes.MATCH_STARTED, player2.getUsername()));
        player2.sendMessage(new Message(MessageTypes.MATCH_STARTED, player1.getUsername()));
    }


    private void playRound(int round) throws Exception {

        if (gameAborted) {
            return;
        }

        // Decides who chooses category
        Player chooser = (round % 2 == 1) ? player1 : player2;
        Player other = (chooser == player1 ? player2 : player1);

        // Ask chooser to pick a category
        chooser.sendMessage(new Message(MessageTypes.CATEGORY_CHOICE, new CategoryPrompt()));

        // Tell the other player to wait
        other.sendMessage(new Message(MessageTypes.CATEGORY_CHOICE, "Waiting for opponent to choose a category for round "));

        // Receive category from chooser
        QuizCategory currentCategory = receiveCategoryChoice(chooser);
        if (gameAborted || currentCategory == null) {
            return;
        }

        // Send each player the questions for this round.
        questionsForThisRound = questionRepo.getRandomQuestions(currentCategory, config.getTotalQuestionsPerRound());

        RoundResult rr = new RoundResult(round);

        // Play all questions for this round
        for (int i = 0; i < questionsForThisRound.size(); i++) {
            if (gameAborted) {
                return;
            }
            playSingleQuestion(round, i + 1, questionsForThisRound.get(i));
        }

        if (gameAborted) {
            return;
        }

        // After round ends, send round summary
        sendRoundResult(round);
    }

    /**
     * Sends a single placeholder question to both players.
     * Later I replace this with real questions from QuestionRepository.
     */

    private QuizCategory receiveCategoryChoice(Player chooser) {
        while (true) {
            Message msg = chooser.getPlayerConnectionHandler().getMessageFromQueue();

            if (msg.getType() == MessageTypes.CATEGORY_CHOICE) {
                return (QuizCategory) msg.getPayload();
            } else if (msg.getType() == MessageTypes.DISCONNECTED_UNEXPECTEDLY) {
                handleUnexpectedDisconnect(chooser);
                return null;
            } else {
                System.out.println("Unexpected message while waiting for CATEGORY_CHOICE: " + msg.getType());
            }
        }
    }

    private void playSingleQuestion(int round, int questionNumber, QuizQuestion question) {

        if (gameAborted) {
            return;
        }

        ///  add RoundResult to later receive our results.

        player1.getGameResult().addRoundResult(round);
        player2.getGameResult().addRoundResult(round);

        // Send actual QUESTION messages to both clients
        broadcast(new Message(MessageTypes.QUESTION, question));

        // Wait for both players to respond with ANSWER messages
        String player1Answer = collectAnswer(player1);
        if (gameAborted || player1Answer == null) {
            return;
        }
        String player2Answer = collectAnswer(player2);
        if (gameAborted || player2Answer == null) {
            return;
        }

        // add round result
        if (player1Answer.equals(question.getCorrectAnswer())) {
            player1.getGameResult().getRoundResult(round).addResult(true);
        } else {
            player1.getGameResult().getRoundResult(round).addResult(false);
        }
        if (player2Answer.equals(question.getCorrectAnswer())) {
            player2.getGameResult().getRoundResult(round).addResult(true);
        } else {
            player2.getGameResult().getRoundResult(round).addResult(false);
        }
    }

    private String collectAnswer(Player player) {
        while (true) {
            Message msg = player.getPlayerConnectionHandler().getMessageFromQueue();

            if (msg.getType() == MessageTypes.ANSWER) {
                if (msg.getPayload() instanceof QuizCategory) {
                    System.out.println(((QuizCategory) msg.getPayload()).getName());
                }
                return (String) msg.getPayload();
            } else if (msg.getType() == MessageTypes.DISCONNECTED_UNEXPECTEDLY) {
                handleUnexpectedDisconnect(player);
                return null;
            } else {
                if (msg.getPayload() instanceof QuizCategory) {
                    System.out.println(((QuizCategory) msg.getPayload()).getName());
                }
                System.out.println("Unexpected message while waiting for ANSWER: " + msg.getType());
            }
        }
    }

    //   ROUND & GAME RESULTS

    private void sendRoundResult(int round) {
        List<RoundResult> roundResultsForPlayer1 = new ArrayList<RoundResult>();
        roundResultsForPlayer1.add(player1.getGameResult().getRoundResult(round));
        roundResultsForPlayer1.add(player2.getGameResult().getRoundResult(round));

        List<RoundResult> roundResultsForPlayer2 = new ArrayList<RoundResult>();
        roundResultsForPlayer2.add(player2.getGameResult().getRoundResult(round));
        roundResultsForPlayer2.add(player1.getGameResult().getRoundResult(round));

        player1.sendMessage(new Message(MessageTypes.ROUND_RESULT, roundResultsForPlayer1));
        player2.sendMessage(new Message(MessageTypes.ROUND_RESULT, roundResultsForPlayer2));
    }

    /**
     * Determines the winner based on total scores.
     *
     */
    private void endGame() {
        int player1Result = player1.getGameResult().getResult();
        int player2Result = player2.getGameResult().getResult();

        String winner;
        if (player1Result > player2Result) {
            winner = player1.getUsername();
        } else if (player2Result > player1Result) {
            winner = player2.getUsername();
        } else {
            winner = "DRAW"; //
        }

        // Send only the winner name "DRAW" to both clients.
        broadcast(new Message(MessageTypes.GAME_RESULT, winner));
    }

    /**
     * Handle unexpected disconnect from one of the players.
     * Opponent auto-wins. If no opponent/username yet → DRAW bit this should not happen during a regular game, it is
     * just for safety (I hope...)
     */
    private void handleUnexpectedDisconnect(Player leaver) {
        if (gameAborted) {
            return;
        }

        System.out.println("Game: unexpected disconnect from player " + leaver.getUsername());
        gameAborted = true;

        Player opponent = getOpponent(leaver);

        // Only notify the opponent; leaver's socket is already inactive
        if (opponent != null) {
            opponent.sendMessage(new Message(MessageTypes.DISCONNECTED_UNEXPECTEDLY, null));
        }
    }

    //   HELPERS
    private void broadcast(Message msg) {
        player1.sendMessage(msg);
        player2.sendMessage(msg);
    }

    private Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }
}
