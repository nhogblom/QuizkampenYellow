package org.example.server;
import java.util.*;

public class QuestionRepository {
    private Map<String, List<QuizQuestion>> categoryQuestions = new HashMap<>();
    private Random random = new Random();

    public QuestionRepository() {
        loadHardcodedQuestions();
    }

    public List<QuizCategory> getCategories() {
        return categoryQuestions.keySet()
                .stream()
                .map(QuizCategory::new)
                .toList();
    }

    public List<QuizQuestion> getRandomQuestions(String categoryName, int count) {
        List<QuizQuestion> questions = categoryQuestions.get(categoryName);
        if(questions == null || questions.isEmpty()) {
            return Collections.emptyList();
        }
        if(count > questions.size()) {
            count = questions.size();
        }
        Collections.shuffle(questions, random);
        return new ArrayList<>(questions.subList(0, count));
    }

    private void loadHardcodedQuestions() {
        // Sport
        categoryQuestions.put("Sport", new ArrayList<>(List.of(
                new QuizQuestion("Hur många spelare har ett fotbollslag på planen?",
                        List.of("9", "10", "11", "12"), "11"),
                new QuizQuestion("Vad kallas det när man gör tre mål?",
                        List.of("Hat-trick", "Triple", "Three-Goal", "Combo"), "Hat-trick"),
                new QuizQuestion("Vilken sport spelas i Wimbledon?",
                        List.of("Tennis", "Basket", "Rugby", "Golf"), "Tennis")
        )));
        // Historia
        categoryQuestions.put("Historia", new ArrayList<>(List.of(
                new QuizQuestion("När startade andra världskriget?",
                        List.of("1914", "1939", "1945", "1960"), "1939"),
                new QuizQuestion("Vilket land byggde pyramiderna?",
                        List.of("Indien", "Persien", "Egypten", "Kina"), "Egypten"),
                new QuizQuestion("Vem var drottning i England under 1800-talet?",
                        List.of("Victoria", "Elizabeth I", "Anne", "Mary"), "Victoria")
        )));
        // FILM och TV
        categoryQuestions.put("Film & TV", new ArrayList<>(List.of(
                new QuizQuestion("Vem spelade Jack i Titanic?",
                        List.of("Leonardo DiCaprio", "Brad Pitt", "Tom Cruise", "Keanu Reeves"), "Leonardo DiCaprio"),
                new QuizQuestion("Vilken superhjälte är 'The Dark Knight'?",
                        List.of("Superman", "Batman", "Spiderman", "Iron Man"), "Batman"),
                new QuizQuestion("Vilken filmserie innehåller Voldemort?",
                        List.of("Star Wars", "Harry Potter", "Sagan om Ringen", "Narnia"), "Harry Potter")
        )));

    }
}

