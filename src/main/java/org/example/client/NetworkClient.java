package org.example.client;

import java.io.*;
import java.net.Socket;

public class NetworkClient {

        private Socket socket;
        private ObjectOutputStream objectWriter;
        private ObjectInputStream objectReader;

        private static final String SERVER_IP = "127.0.0.1";
        private static final int SERVER_PORT = 12346;

        private final String playerName;

        public NetworkClient(String playerName) {
            this.playerName = playerName;
        }

        //Koppla upp mot server
        public void connect() {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);

                objectWriter= new ObjectOutputStream(socket.getOutputStream());
                objectReader = new ObjectInputStream(socket.getInputStream());

                System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

                //Skicka användarnamn
                sendUsername();


                // Starta lyssnarthread
                new Thread(this::listen).start();

            } catch (Exception e) {
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }

        //Lyssna på meddelande från server
        private void listen() {
            ClientProtocol protocol =new ClientProtocol(this);
            try {
                while (true) {
                    Message msg = (Message) objectReader.readObject();
                    protocol.handleMessage(msg);
                }
            } catch (Exception e) {
                System.out.println("Connection closed or error in listen()");
                e.printStackTrace();
            }

        }

        // Hantera fråga: visa och skicka svar
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

        public void sendUsername() {
            UsernameMessage usernameMsg = new UsernameMessage(playerName);
            Message msg = new Message(MyMessageTypes.CHAT, usernameMsg);
            sendMessage(msg);
        }

        public void sendAnswer(int chosenption) {
            Answer answer=new Answer(playerName,chosenption);
            AnswerMessage answerMsg =new AnswerMessage(answer);
            Message msg= new Message(MyMessageTypes.ANSWER, answerMsg);
            sendMessage(msg);
        }

        public void sendCategory(String category) {
            CategoryChoiceMessage categoryMsg = new CategoryChoiceMessage(category);
            sendMessage(new Message(MyMessageTypes.CATEGORY_CHOICE, categoryMsg));
        }

        public void sendGive(){
            GiveUpMessage giveUpMessage = new GiveUpMessage();
            sendMessage(new Message(MyMessageTypes.GIVE_UP, giveUpMessage));

        }

        //test klient
        public static void main(String[] args) {
            NetworkClient client = new NetworkClient("Player1");
            client.connect();
        }
    }