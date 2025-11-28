package org.example.client;

import org.example.server.GameConfig;

import java.io.*;
import java.net.Socket;

public class NetworkClient {

    private Socket socket;
    private ObjectInputStream objectReader;
    private ObjectOutputStream objectWriter;
    private final String SERVER_IP;
    private final int SERVER_PORT;
    private final String playerName;

    public NetworkClient(String playerName) {
        GameConfig gameConfig = new GameConfig();
        SERVER_IP = gameConfig.getIpAsString();
        SERVER_PORT = gameConfig.getPort();

        this.playerName = playerName;
        }

        //Koppla upp mot server
        public void connect() {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);

                objectWriter= new ObjectOutputStream(socket.getOutputStream());
                objectReader = new ObjectInputStream(socket.getInputStream());

                System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

                // Starta lyssnarthread
                new Thread(this::listen).start();

            } catch (Exception e) {
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }

        //Lyssna på meddelande från server
        private void listen() {
            try {
                while (true) {
                    Message msg = (Message) objectReader.readObject();

                    switch (msg.getType()) {
                        case QUESTION:
                            handleQuestion((Question) msg.getPayload());
                            break;
                        case ROUND_RESULT:
                            System.out.println("Round result received: " + msg.getPayload());
                            break;
                        case GAME_RESULT:
                            System.out.println("Game result received: " + msg.getPayload());
                            break;
                        case CHAT:
                            System.out.println("Chat message: " + msg.getPayload());
                            break;
                        case DEVELOPMENTMSG:
                            System.out.println("Development message received: " + msg.getPayload());
                            break;
                        default:
                            System.out.println("Unknown message type: " + msg.getType());
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection closed or error in listen()");
                e.printStackTrace();
            }

        }

        // Hantera fråga: visa och skicka svar
        private void handleQuestion(Question question) {
            System.out.println("Question: " + question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ": " + options[i]);
            }

            // OBS, Tillfälligt demo
            int chosenOption = 0;
            Answer answer = new Answer(playerName, chosenOption);

            sendMessage(new Message(MyMessageTypes.ANSWER, answer));
            System.out.println("Answer sent: option " + chosenOption);
        }

        public void sendMessage(Message msg) {
            try {
                objectWriter.writeObject(msg);
                objectWriter.flush();
            } catch (IOException e) {
                System.out.println("Failed to send message: ");
                e.printStackTrace();
            }
        }

        public void disconnect() {
            try {
                if (objectReader != null) objectReader.close();
                if (objectWriter != null) objectWriter.close();
                if (socket != null) socket.close();
                System.out.println("Disconnected from server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //test klient
        public static void main(String[] args) {
            NetworkClient client = new NetworkClient("Player1");
            client.connect();
        }
    }