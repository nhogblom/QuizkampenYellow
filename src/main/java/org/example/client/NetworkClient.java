package org.example.client;

import org.example.shared.Introduction;
import org.example.shared.Message;
import org.example.shared.MessageTypes;
import org.example.shared.GameConfig;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * NetworkClient
 *
 * This class is responsible for:
 *  - Connecting to the server via TCP socket
 *  - Sending and receiving Message objects
 *  - Starting a listening thread that continuously reads server messages
 *
 * It does NOT decide what to do with all messages itself.
 * For that we use ClientProtocol, which handles the logic.
 */
public class NetworkClient {

    private Socket socket;
    private ObjectInputStream objectReader;
    private ObjectOutputStream objectWriter;

    private final String SERVER_IP;
    private final int SERVER_PORT;

    private final ClientBackpack backpack;

    // This is where we delegate message handling logic
    private final ClientProtocol protocol;

    /**
     *  backpack  - Shared state passed in from the GUI (WaitingPanel etc.)
     */
    public NetworkClient(ClientBackpack backpack) {
        GameConfig gameConfig = new GameConfig();
        gameConfig.setBackpack(backpack);
        this.backpack = backpack;
        this.backpack.setNetworkClient(this);   // allow other classes to find this client

        SERVER_IP = gameConfig.getIpAsString();
        SERVER_PORT = gameConfig.getPort();

        // Create the ClientProtocol that will handle most incoming messages
        this.protocol = new ClientProtocol(this, this.backpack);
    }

    /**
     * Connect to the server.
     * Creates the socket and the object streams.
     * Starts a new thread that listens for messages (listen()).
     */
    public boolean connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);

            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectReader = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);

            // Tell server our username first
            sendMessage(new Message(MessageTypes.INTRODUCTION, new Introduction(backpack.getUsername(),backpack.getAvatar())));

            // Start the listener thread
            new Thread(this::listen).start();
            return true;

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method runs in its own thread.
     * It continuously waits for messages from the server.
     */
    private void listen() {
        try {
            while (true) {
                // Block here until a Message object is received from the server
                Message msg = (Message) objectReader.readObject();

                // Some message types we keep in NetworkClient (because they affect navigation)
                switch (msg.getType()) {
                    case MATCH_STARTED:
                        // Server says: the match has started and gives opponent's username
                        backpack.setGoToNextScreen(true);
                        backpack.setOpponentUsername(((Introduction) msg.getPayload()).getUsername());
                        backpack.setOpponentAvatar(((Introduction) msg.getPayload()).getAvatar());
                        backpack.getQuestionPanel().setAvatarAndUsernames();

                        backpack.getRoundSummaryPanel().setAvatar();
                        System.out.println("Match started, you are playing against " + backpack.getOpponentUsername());
                        break;

                    case DEVELOPMENTMSG:
                        // Temporary messages from server.
                        backpack.setGoToNextScreen(true);
                        System.out.println("Development message received: " + msg.getPayload());
                        break;

                    default:
                        // For all other message types, we go to ClientProtocol.
                        // and we try to keep this class focused on networking, not game logic.
                        protocol.handleMessage(msg);
                        break;
                }
            }
        } catch (SocketException | EOFException e) {
            // This is expected to happen when the user clicks "No" (don't play again)
            // and we close the socket.
            System.out.println("Connection closed, stopping listener thread.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unexpected error in listen()");
            e.printStackTrace();
        }
    }

    /**
     * Method to send a generic Message to the server.
     */
    public void sendMessage(Message msg) {
        try {
            objectWriter.writeObject(msg);
            objectWriter.flush();
        } catch (IOException e) {
            System.out.println("Failed to send message: ");
            e.printStackTrace();
        }
    }

    /**
     * Sending a chat message to the server.
     * This is called from QuestionPanel's chat input.
     */
    public void sendChatMessage(String text) {
        if (text == null || text.isBlank()) {
            return; // don't send empty messages
        }

        try {
            Message message = new Message(MessageTypes.CHAT, text);
            objectWriter.writeObject(message);
            objectWriter.flush();
            System.out.println("Sent chat message: " + text);
        } catch (IOException e) {
            System.out.println("Failed to send chat message.");
            e.printStackTrace();
        }
    }

    /**
     * Close all streams and the socket.
     */
    public void disconnect() {
        try {
            if (objectReader != null) objectReader.close();
            if (objectWriter != null) objectWriter.close();
            if (socket != null) socket.close();
            System.out.println("Disconnected from server");
        } catch (IOException e) {
            // ignore, we're closing anyway
        }
    }
}
