package org.example.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionStorage {

    private static final String fileName = "/src/main/resources/question.json";

    private static final Gson gson = new Gson();


    //Save all question to file
    public static void saveQuestion(Map<String, List<QuizQuestion>> questions) {
        try (FileWriter out = new FileWriter(fileName, StandardCharsets.UTF_8)) {
            gson.toJson(questions, out);
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

        try (FileReader in = new FileReader(fileName, StandardCharsets.UTF_8)) {
            return gson.fromJson(in, new TypeToken<Map<String,List<QuizQuestion>>>(){}.getType());
        } catch (Exception e) {
            System.out.println("Failed to load questions from file.");
            e.printStackTrace();
            return new HashMap<>();
        }
    }


}
