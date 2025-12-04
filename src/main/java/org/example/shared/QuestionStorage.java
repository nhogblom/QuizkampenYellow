package org.example.shared;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionStorage {

    private final String fileName = "/data/Questions.json";
    private final String path = "src/main/resources";
    private final Gson gson = new Gson();


    //Save all question to file
    public void saveQuestion(Map<String, List<QuizQuestion>> questions) {
        try (FileWriter fw = new FileWriter(path+fileName, StandardCharsets.UTF_8)) {
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
            return gson.fromJson(isr, new TypeToken<Map<String, List<QuizQuestion>>>() {
            }.getType());
        } catch (Exception e) {
            System.out.println("Failed to load questions from file.");
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
