package org.example.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.GameConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionStorage {

    private final String fileName = "/Questions.json";

    private final Gson gson = new Gson();


    //Save all question to file
    public void saveQuestion(Map<String, List<QuizQuestion>> questions) {
        try (FileWriter fw = new FileWriter(Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).getPath())+fileName, StandardCharsets.UTF_8)) {
            gson.toJson(questions, fw);
            System.out.println("Questions.json saved to file.");
        } catch (Exception e) {
            System.out.println("Failed to save questions to file.");
            e.printStackTrace();
        }
    }

    //Load all question from file
    public Map<String, List<QuizQuestion>> loadQuestions() {

        try (InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)))) {
            return gson.fromJson(isr, new TypeToken<Map<String, List<QuizQuestion>>>(){}.getType());
        } catch (Exception e) {
            System.out.println("Failed to load questions from file.");
            e.printStackTrace();
            return new HashMap<>();
        }

    }


}
