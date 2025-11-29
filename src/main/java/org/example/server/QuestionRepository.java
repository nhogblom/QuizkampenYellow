package org.example.server;
import java.util.*;

public class QuestionRepository {

    private List<String> categories = new ArrayList<>();
    private List<QuizQuestion> sportQuestions = new ArrayList<>();
    private List<QuizQuestion> historyQuestions = new ArrayList<>();
    private List<QuizQuestion> filmQuestions = new ArrayList<>();
    private Random random = new Random();

    public QuestionRepository() {
        loadHardcodedQuestions();
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<QuizQuestion> getRandomQuestions(String category, int count) {
        List<QuizQuestion> source;

        if(category.equals("Sport")) {
            source = sportQuestions;
        }  else if (category.equals("History")) {
            source = historyQuestions;
        } else if (category.equals("Film & TV")) {
            source = filmQuestions;
        } else {
            return Collections.emptyList();
        }

        List<QuizQuestion> copy = new ArrayList<>(source);
        Collections.shuffle(copy);
        if(count > copy.size()) {
            count = copy.size();
        }
        return copy.subList(0, count);
    }

    private void loadHardcodedQuestions() {

        categories.add("Sport");
        categories.add("History");
        categories.add("Film & TV");

        sportQuestions.add(new QuizQuestion(
                "How many players does a football team have on the field?",
                List.of("9", "10", "11", "12"), 2));
        sportQuestions.add(new QuizQuestion(
                "What is it called when a player scores 3 goals?",
                List.of("Hat-trick", "Tripple", "Three-Goal", "Combo"), 0));
        sportQuestions.add(new QuizQuestion(
                "Which sport is played in Wimbledon?",
                List.of("Tennis", "Basket", "Rugby", "Golf"), 0));


        historyQuestions.add(new QuizQuestion(
                "When did World war 2 start?",
                List.of("1914", "1939", "1945", "1960"), 1));
        historyQuestions.add(new QuizQuestion(
                "Which country built the pyramids?",
                List.of("India", "Persia", "Egypt", "China"), 2));
        historyQuestions.add(new QUizQuestion(
                "Who was the queen of England in 19th century",
                List.of("Victoria", "Elizabeth I", "Anne", "Mary"), 0));

        filmQuestions.add(new QuizQuestion(
                "Who played Jack in Titanic?",
                List.of("Leonardo DiCaprio", "Brad Pitt", "Tom Cruise", "Keanu Reeves"), 0));
        filmQuestions.add(new QuizQuestion(
                "Which superhero is the Dark Knight?",
                List.of("Superman", "Batman", "Spiderman", "Iron Man"), 1));
        filmQuestions.add(new QuizQuestion(
                "Which movie series has the character Voldemort?",
                List.of("Star Wars", "Harry Potter", "Lord of the rings", "Narnia"), 1));
    }
}

// TODO Lagrar och tillhandahåller frågor grupperade per kategori.