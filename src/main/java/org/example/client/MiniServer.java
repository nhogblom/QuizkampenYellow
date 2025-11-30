package org.example.client;

import org.example.Answer;
import org.example.Message;
import org.example.MessageTypes;
import org.example.Question;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MiniServer {



        private static final int PORT = 12346;

        public static void main(String[] args) {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server started on port " + PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    new Thread(() -> handleClient(clientSocket)).start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void handleClient(Socket socket) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                String[] options = {"Option A", "Option B", "Option C", "Option D"};
                Question question = new Question("What is 2 + 2?", options);
                Message questionMsg = new Message(MessageTypes.QUESTION, question);
                out.writeObject(questionMsg);
                out.flush();

                // Vänta på svar
                Message answerMsg = (Message) in.readObject();
                if (answerMsg.getType() == MessageTypes.ANSWER) {
                    Answer answer = (Answer) answerMsg.getPayload();
                    System.out.println("Received answer from " + answer.getPlayerName() +
                            ": option " + answer.getChosenOption());
                }

                //Håll socketen öppen för fler meddelanden**
                while (true) {
                    try {
                        Message msg = (Message) in.readObject();
                        System.out.println("Received: " + msg.getType());
                    } catch (Exception e) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                }

                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
