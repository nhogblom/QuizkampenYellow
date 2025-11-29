package org.example.client;

import org.example.Message;
import org.example.Question;

public class ClientProtocol {
    private final NetworkClient client;

    public ClientProtocol(NetworkClient client) {
        this.client = client;
    }

    public void handleMessage(Message msg) {
        switch (msg.getType()) {
            case QUESTION:
                Question question = (Question) msg.getPayload();
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
                System.out.println("Unknown message type: " + msg.getType());
        }
    }

    private void handleQuestion(Question question) {
        // Uppdate QuestionPanel
        System.out.println("Recieved question: " + question.getQuestionText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println("Option " + i+1 + ": " + options[i]);
        }


    }

    private void handleRoundResult(Object payload) {
        //kod från panel, uppdatera poäng
    }

    private void handleGameresult(Object payload) {
        //kod från panel
    }

    private void handleChat(Object payload) {
        //det kommer utvecklas
    }

    private void handleCategoryChoice(Object payload) {
        //Uppdatera GUI med vald kategori

    }

    private void handleGiveUp(Object payload) {
        System.out.println("Opponent gave up!");
        //Vidare åtgärd från panel
    }
}
