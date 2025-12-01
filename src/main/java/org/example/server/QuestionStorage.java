package org.example.server;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionStorage {

    private static final String fileName = "question.txt";

    //Save all question to file
    public static void saveQuestion(Map<String, List<QuizQuestion>> questions) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(questions);
            System.out.println("Questions saved to file.");
        } catch (Exception e) {
            System.out.println("Failed to save questions to file.");
            e.printStackTrace();
        }
    }

    //Load all question from file
    public static Map<String, List<QuizQuestion>> loadQuestions() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No saved question or file not found.");
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, List<QuizQuestion>>) in.readObject();

        } catch (Exception e) {
            System.out.println("Failed to load questions from file.");
            e.printStackTrace();
            return new HashMap<>();
        }
    }


}
