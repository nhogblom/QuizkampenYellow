package org.example.client;

import org.example.Message;
import org.example.Question;
import org.example.client.panels.QuestionPanel;
import org.example.server.CategoryPrompt;
import org.example.server.QuizQuestion;

import java.util.List;

/**
 * ClientProtocol
 * <p>
 * This class is responsible for:
 * - Receiving messages (already read from the socket)
 * - Deciding WHAT to do with them (update GUI, print info)
 * <p>
 * NetworkClient handles small networking like sockets, streams.
 * ClientProtocol handles the "game logic" on the client side.
 *
 */
public class ClientProtocol {

    // We keep a reference to NetworkClient if we ever need to send new messages later
    private final NetworkClient networkClient;

    // ClientBackpack gives us access to GUI stuff, like QuestionPanel
    private final ClientBackpack backpack;

    /**
     * client  -  the NetworkClient that owns this protocol
     * backpack - shared state object: holds active JFrame, QuestionPanel, usernames, etc.
     */
    public ClientProtocol(NetworkClient networkClient, ClientBackpack backpack) {
        this.networkClient = networkClient;
        this.backpack = backpack;
    }

    /**
     * Handle any message that comes from the server.
     * This method is called from NetworkClient.listen().
     */
    public void handleMessage(Message msg) {
        switch (msg.getType()) {
            case QUESTION:
                // Payload should be a Question object
                QuizQuestion question = (QuizQuestion) msg.getPayload();
                handleQuestion(question);
                break;

            case ROUND_RESULT:
                handleRoundResult(msg.getPayload());
                break;

            case GAME_RESULT:
                handleGameresult(msg.getPayload());
                break;

            case CHAT:
                handleChat(msg.getPayload());
                break;

            case CATEGORY_CHOICE:
                handleCategoryChoice(msg.getPayload());
                break;

            case GIVE_UP:
                handleGiveUp(msg.getPayload());
                break;

            default:
                System.out.println("Unknown message type in ClientProtocol: " + msg.getType());
        }
    }

    /**
     * Handle an incoming Question from the server.
     * This is where we update the GUI (QuestionPanel) instead of only printing in console.
     */
    private void handleQuestion(QuizQuestion question) {
        System.out.println("Received question: " + question.getQuestion());

        // 1) Try the QuestionPanel directly stored in the backpack
        QuestionPanel qp = backpack.getQuestionPanel();
        if (qp != null) {
            qp.updateQuestion(question);
            backpack.getCategoryPanel().setVisible(false);

            qp.setVisible(true);


            return;
        }

        // 2) As a security, check if the active JFrame is a QuestionPanel
        if (backpack.getActiveJframe() instanceof QuestionPanel qpFrame) {
            qpFrame.updateQuestion(question);
            return;
        }

        // 3) If no panel was found
        System.out.println("WARNING: No QuestionPanel available to show question");
        // This might happen if the question arrives before the QuestionPanel is created.
    }

    /**
     * Handle message that tells us the result of a round.
     * For now, we just print and leave a TODO for future GUI updates.
     */
    private void handleRoundResult(Object payload) {
        // TODO: later, update some score GUI or show summary panel
        System.out.println("Round result: " + payload);
        backpack.getQuestionPanel().setVisible(false);
        backpack.getRoundSummaryPanel().setVisible(true);

    }

    /**
     * Handle message that tells us the final game result.
     */
    private void handleGameresult(Object payload) {
        // TODO: later, show a "Game Over" / "Winner" screen
        System.out.println("Game result: " + payload);
    }

    /**
     * Handle chat messages.
     */
    private void handleChat(Object payload) {
        QuestionPanel qp = backpack.getQuestionPanel();
        if (qp != null) {
            qp.appendChatMessage(payload.toString());
        } else {
            System.out.println("Chat message (no panel available): " + payload);
        }
    }

    /**
     * Handle information about which category was chosen.
     */
    private void handleCategoryChoice(Object payload) {
        // TODO: update GUI to show chosen category (e.g. in a label)
        if (payload instanceof String s) {
            // show player that they have to wait for the opponent that is currently choosing the cat for next round
            backpack.getCategoryPanel().displayWaitMessage(s);
            // todo skriv ut vänte meddelande till den väntande spelaren
        } else if (payload instanceof CategoryPrompt categoryPrompt) {
            // hantera category prompt i ui
            backpack.getCategoryPanel().setCategories(categoryPrompt);
        }
    }

    /**
     * Handle when opponent gives up.
     */
    private void handleGiveUp(Object payload) {
        System.out.println("Opponent gave up!");
        // TODO: show some info in the GUI (e.g. dialog: "Opponent surrendered")
    }
}
