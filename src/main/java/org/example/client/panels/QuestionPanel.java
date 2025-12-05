package org.example.client.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import org.example.shared.Message;
import org.example.shared.MessageTypes;
import org.example.client.ClientBackpack;
import org.example.shared.QuizQuestion;

/**
 * QuestionPanel
 *
 * This panel displays a single quiz question + 4 answer buttons.
 *
 * DESIGN:
 * - GUI-only: no networking, no socket logic.
 * - Communicates via QuestionAnsweredListener, provided from outside
 *   (WaitingPanel -> NetworkClient).
 * - Uses ClientBackpack only as shared context (stores a reference and
 *   registers itself, so NetworkClient can call updateQuestion(...)).
 */
public class QuestionPanel extends JFrame implements ActionListener {


    private final ClientBackpack backpack;

    /** GUI components updated when new questions arrive */
    private JLabel questionLabel;
    private JButton optionButton1;
    private JButton optionButton2;
    private JButton optionButton3;
    private JButton optionButton4;
    private JLabel avatar;
    private JLabel opponentAvatar;




    /** Chat GUI components */
    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton sendChatButton;


    public QuestionPanel(ClientBackpack backpack) {
        super("Quizkampen - Question");
        this.backpack = backpack;
        backpack.setQuestionPanel(this);



        // Register this panel in the shared state so NetworkClient can find it
        backpack.setQuestionPanel(this);
        backpack.setActiveJframe(this);
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Constants.DARK_BLUE);
        addGuiComponents();
    }



    /** Creates and places all GUI components */
    private void addGuiComponents() {
        // Main question box
        questionLabel = new JLabel("QUESTION");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        questionLabel.setBounds(100, 50, 400, 200);
        questionLabel.setBackground(new Color(0, 0, 0, 0.2f));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        add(questionLabel);

        ImageIcon avatarImg = new ImageIcon(new ImageIcon(backpack.getAvatar()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        ImageIcon opponentAvatarImg = new ImageIcon(new ImageIcon(backpack.getOpponentAvatar()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));


        avatar = new JLabel(avatarImg);
        avatar.setBounds(20, 10, 100, 100);
        add(avatar);

        opponentAvatar = new JLabel(opponentAvatarImg);
        opponentAvatar.setBounds(500, 10, 100, 100);
        add(opponentAvatar);
        revalidate();
        repaint();

        // Option 1
        optionButton1 = makeOptionButton(100, 240);
        optionButton1.addActionListener(e -> sendAnswerAndDoNecessaryStuff(this.optionButton1)); // notify listener
        add(optionButton1);

        // Option 2
        optionButton2 = makeOptionButton(320, 240);
        optionButton2.addActionListener(e ->  sendAnswerAndDoNecessaryStuff(this.optionButton2));
        add(optionButton2);

        // Option 3
        optionButton3 = makeOptionButton(100, 400);
        optionButton3.addActionListener(e ->  sendAnswerAndDoNecessaryStuff(this.optionButton3));
        add(optionButton3);

        // Option 4
        optionButton4 = makeOptionButton(320, 400);
        optionButton4.addActionListener(e ->  sendAnswerAndDoNecessaryStuff(this.optionButton4));
        add(optionButton4);



        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(Color.WHITE);
        chatArea.setForeground(Color.BLACK);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setBounds(100, 660, 400, 60);
        add(chatScrollPane);

        // Chat input field
        chatInput = new JTextField();
        chatInput.setBounds(100, 730, 320, 30);
        add(chatInput);

        chatInput.addActionListener(this);

        // Send button
        sendChatButton = new JButton("Send");
        sendChatButton.setBounds(430, 730, 70, 30);
        add(sendChatButton);

        // Wire send button to NetworkClient via backpack
        sendChatButton.addActionListener(this);
    }



    private void sendAnswerAndDoNecessaryStuff(JButton jb){
        backpack.getNetworkClient().sendMessage(new Message(MessageTypes.ANSWER,jb.getActionCommand()));
        setButtonState(false);
    }

    private void setButtonState(boolean state) {
        if (SwingUtilities.isEventDispatchThread()) {
            optionButton1.setEnabled(state);
            optionButton2.setEnabled(state);
            optionButton3.setEnabled(state);
            optionButton4.setEnabled(state);
        } else {
            SwingUtilities.invokeLater(() -> setButtonState(state));
        }
    }

    /** Helper for styling of all answer buttons */
    private JButton makeOptionButton(int x, int y) {
        JButton b = new JButton("option");
        b.setActionCommand("option");
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBounds(x, y, 180, 150);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        return b;
    }

    // Update option buttons to apply html formatting outside of button text
    private void updateOptionButton(JButton button, String answer) {

        button.setText("<html><body style='width: 120px; text-align: center'>" + answer + "</h1></html>");
        button.setActionCommand(answer);
    }

    /**
     * Update the text on all GUI components when a new question arrives.
     * This is called from NetworkClient - handleQuestion().
     */
    public void updateQuestion(QuizQuestion question) {
        if (question == null) {
            System.out.println("WARNING: invalid question/options in updateQuestion");
            return;
        }

        setButtonState(true);

        questionLabel.setText("<html><h1>"+question.getQuestion()+"</h1></html>");
        // todo remove hard corded buttons to make it work with more questions per round etc~
        updateOptionButton(optionButton1, question.getAnswers().get(0));
        updateOptionButton(optionButton2, question.getAnswers().get(1));
        updateOptionButton(optionButton3, question.getAnswers().get(2));
        updateOptionButton(optionButton4, question.getAnswers().get(3));

    }

    /**
     * Append a chat message to the chat area.
     * Can be called from ClientProtocol.handleChat(...)
     */
    public void appendChatMessage(String message) {
        if (message == null) return;
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    /**
     * Listener interface
     * Implemented in WaitingPanel like:
     *
     *      optionIndex -> client.sendAnswer(optionIndex)
     *
     * This keeps QuestionPanel completely GUI-only.
     */


    public void setChatAreaText(String text) {
        this.chatArea.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = chatInput.getText().trim();
        if (!text.isEmpty()) {
            backpack.getNetworkClient().sendChatMessage(text);
            chatInput.setText("");
        }
    }
}
