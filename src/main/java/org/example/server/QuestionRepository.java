package org.example.server;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class QuestionRepository {
    private Map<String, List<QuizQuestion>> categoryQuestions = new HashMap<>();
    private Random random = new Random();

    public QuestionRepository() {
        Map<String, List<QuizQuestion>> loadedFile = QuestionStorage.loadQuestions();
        if (loadedFile.isEmpty()) {
            System.out.println("No saved questions found, loading hardcode.");
            loadHardcodedQuestions();
            QuestionStorage.saveQuestion(categoryQuestions);
        } else {
            categoryQuestions = loadedFile;
            System.out.println("Questions loaded successfully from file.");
        }

    }

    public List<QuizCategory> getCategories() {
        List<String> keys = new ArrayList<>(categoryQuestions.keySet());
        Collections.shuffle(keys, random);

        return keys.stream()
                .map(QuizCategory::new)
                .toList();
    }

    public List<QuizQuestion> getRandomQuestions(QuizCategory category, int count) {
        List<QuizQuestion> questions = categoryQuestions.get(category.getName());
        if (questions == null || questions.isEmpty()) {
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
                // 1
                new QuizQuestion("Hur många spelare har ett fotbollslag på planen?",
                        List.of("9", "10", "11", "12"), "11"),
                // 2
                new QuizQuestion("Vad kallas det när man gör tre mål i samma match?",
                        List.of("Hat-trick", "Triple", "Three-Goal", "Combo"), "Hat-trick"),
                // 3
                new QuizQuestion("Vilken sport spelas i Wimbledon?",
                        List.of("Tennis", "Basket", "Rugby", "Golf"), "Tennis"),
                // 4
                new QuizQuestion("Hur lång är en hel fotbollsmatch i ordinarie tid?",
                        List.of("80 minuter", "90 minuter", "70 minuter", "100 minuter"), "90 minuter"),
                // 5
                new QuizQuestion("Vilket land vann fotbolls-VM 2018 för herrar?",
                        List.of("Tyskland", "Frankrike", "Brasilien", "Spanien"), "Frankrike"),
                // 6
                new QuizQuestion("Vilken färg har ledartröjan i Tour de France?",
                        List.of("Gul", "Grön", "Vit", "Röd"), "Gul"),
                // 7
                new QuizQuestion("Hur många poäng ger en trepoängare i basket?",
                        List.of("1", "2", "3", "4"), "3"),
                // 8
                new QuizQuestion("I vilken sport används klubba och puck?",
                        List.of("Ishockey", "Bandy", "Lacrosse", "Baseboll"), "Ishockey"),
                // 9
                new QuizQuestion("Vad heter Sveriges största inomhusarena för fotboll och konserter i Solna?",
                        List.of("Friends Arena", "Ullevi", "Tele2 Arena", "Avicii Arena"), "Friends Arena"),
                // 10
                new QuizQuestion("Vilket land kommer tennisspelaren Roger Federer ifrån?",
                        List.of("Frankrike", "USA", "Schweiz", "Tyskland"), "Schweiz"),
                // 11
                new QuizQuestion("I vilken sport kan man göra en 'strike'?",
                        List.of("Bowling", "Tennis", "Baseboll", "Kricket"), "Bowling"),
                // 12
                new QuizQuestion("Hur många ringar finns i OS-symbolen?",
                        List.of("4", "5", "6", "7"), "5"),
                // 13
                new QuizQuestion("Vilken svensk fotbollsspelare är känd för uttrycket 'Zlatanera'?",
                        List.of("Henrik Larsson", "Zlatan Ibrahimovic", "Fredrik Ljungberg", "Anders Svensson"), "Zlatan Ibrahimovic"),
                // 14
                new QuizQuestion("Vilken sport utövar man i NHL?",
                        List.of("Ishockey", "Basket", "Amerikansk fotboll", "Baseboll"), "Ishockey"),
                // 15
                new QuizQuestion("Hur många set krävs normalt för seger i en Grand Slam-final för herrar i tennis?",
                        List.of("Bäst av 3", "Bäst av 5", "Bäst av 7", "Bäst av 9"), "Bäst av 5"),
                // 16
                new QuizQuestion("Vilken gren ingår INTE i friidrott?",
                        List.of("Diskus", "Spjutfangst", "Tresteg", "Diskus"), "Spjutfangst"),
                // 17
                new QuizQuestion("Vad kallas målvakten i handboll på engelska?",
                        List.of("Goalkeeper", "Netkeeper", "Handkeeper", "Goalie"), "Goalkeeper"),
                // 18
                new QuizQuestion("Vilket land arrangerade sommar-OS 2016?",
                        List.of("Kina", "Brasilien", "Storbritannien", "Japan"), "Brasilien"),
                // 19
                new QuizQuestion("Hur många spelare finns på planen i ett innebandylag (inklusive målvakt)?",
                        List.of("4", "5", "6", "7"), "6"),
                // 20
                new QuizQuestion("Vilken sport utövar man när man gör en 'hole in one'?",
                        List.of("Golf", "Baseboll", "Cricket", "Rugby"), "Golf"),
                // 21
                new QuizQuestion("Vad heter den största internationella fotbollsturneringen för landslag?",
                        List.of("Champions League", "Europa League", "VM", "Copa America"), "VM"),
                // 22
                new QuizQuestion("Vilken svensk skidåkare kallas 'Gunde'?",
                        List.of("Gunde Svan", "Thomas Wassberg", "Ingemar Stenmark", "Sixten Jernberg"), "Gunde Svan"),
                // 23
                new QuizQuestion("I vilken sport används en 'shuttlecock'?",
                        List.of("Badminton", "Tennis", "Squash", "Pingis"), "Badminton"),
                // 24
                new QuizQuestion("Hur många minuter är en period i ishockey i SHL?",
                        List.of("15", "20", "25", "30"), "20"),
                // 25
                new QuizQuestion("Vilken sport förknippas med Formel 1?",
                        List.of("Motorsport", "Cykling", "Friidrott", "Simning"), "Motorsport"),
                // 26
                new QuizQuestion("Vad heter platsen där man hoppar längdhopp?",
                        List.of("Bana", "Grop", "Ränna", "Ring"), "Grop"),
                // 27
                new QuizQuestion("Vilket land är mest känt för kampsporten sumobrottning?",
                        List.of("Kina", "Japan", "Korea", "Thailand"), "Japan"),
                // 28
                new QuizQuestion("Hur många poäng ger ett mål i handboll?",
                        List.of("1", "2", "3", "4"), "1"),
                // 29
                new QuizQuestion("Vad heter Sveriges högsta ishockeyliga för herrar?",
                        List.of("Allsvenskan", "SHL", "Elitserien", "Hockeyligan"), "SHL"),
                // 30
                new QuizQuestion("Vilken sport är känd som 'kung fotboll' på svenska?",
                        List.of("Fotboll", "Handboll", "Basket", "Innebandy"), "Fotboll")
        )));

        // Historia
        categoryQuestions.put("Historia", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("När startade andra världskriget?",
                        List.of("1914", "1939", "1945", "1960"), "1939"),
                // 2
                new QuizQuestion("Vilket land byggde pyramiderna i Giza?",
                        List.of("Indien", "Persien", "Egypten", "Kina"), "Egypten"),
                // 3
                new QuizQuestion("Vem var drottning i England under större delen av 1800-talet?",
                        List.of("Victoria", "Elizabeth I", "Anne", "Mary"), "Victoria"),
                // 4
                new QuizQuestion("Vilken stad delades i två delar av en mur mellan 1961 och 1989?",
                        List.of("Berlin", "Paris", "Rom", "London"), "Berlin"),
                // 5
                new QuizQuestion("Vad hette skeppet som Columbus seglade med 1492?",
                        List.of("Santa Maria", "Mayflower", "Victoria", "Beagle"), "Santa Maria"),
                // 6
                new QuizQuestion("Vilket rike styrdes av faraoner?",
                        List.of("Romarriket", "Egypten", "Perserriket", "Mongolriket"), "Egypten"),
                // 7
                new QuizQuestion("Vad kallades den franska revolutionens avrättningsmaskin?",
                        List.of("Galge", "Giljotin", "Stupa", "Ställning"), "Giljotin"),
                // 8
                new QuizQuestion("Vilket årtal föll det romerska riket i väst traditionellt?",
                        List.of("476", "800", "1066", "1492"), "476"),
                // 9
                new QuizQuestion("Vilken svensk kung kallades 'Lejonet från Norden'?",
                        List.of("Gustav II Adolf", "Karl XII", "Gustav Vasa", "Karl XI"), "Gustav II Adolf"),
                // 10
                new QuizQuestion("Vilket land släppte atombomber över Hiroshima och Nagasaki?",
                        List.of("Sovjetunionen", "Tyskland", "USA", "Japan"), "USA"),
                // 11
                new QuizQuestion("Vilken epok brukar följa efter medeltiden i Europa?",
                        List.of("Antiken", "Renässansen", "Upplysningen", "Stormaktstiden"), "Renässansen"),
                // 12
                new QuizQuestion("Vad hette Sveriges första kvinnliga statsminister?",
                        List.of("Karin Söder", "Magdalena Andersson", "Mona Sahlin", "Anna Lindh"), "Magdalena Andersson"),
                // 13
                new QuizQuestion("Vilken pest spreds i Europa på 1300-talet och dödade miljontals människor?",
                        List.of("Digerdöden", "Spanska sjukan", "Kolera", "SARS"), "Digerdöden"),
                // 14
                new QuizQuestion("Vad kallades handelsorganisationen runt Östersjön under medeltiden?",
                        List.of("Hanseförbundet", "NATO", "EU", "FN"), "Hanseförbundet"),
                // 15
                new QuizQuestion("Vilket år blev Sverige medlem i EU?",
                        List.of("1986", "1995", "2001", "2010"), "1995"),
                // 16
                new QuizQuestion("Vem grundade religionen islam enligt traditionen?",
                        List.of("Jesus", "Abraham", "Muhammed", "Buddha"), "Muhammed"),
                // 17
                new QuizQuestion("Vad kallades det när många svenskar utvandrade till USA på 1800-talet?",
                        List.of("Den stora vandringen", "Emigrationen", "Koloniseringen", "Urbaniseringen"), "Emigrationen"),
                // 18
                new QuizQuestion("Vilken amerikansk president mördades i Dallas 1963?",
                        List.of("Abraham Lincoln", "John F. Kennedy", "Richard Nixon", "Harry Truman"), "John F. Kennedy"),
                // 19
                new QuizQuestion("Vilket land leddes av Nelson Mandela efter apartheid?",
                        List.of("Nigeria", "Kenya", "Sydafrika", "Egypten"), "Sydafrika"),
                // 20
                new QuizQuestion("Vilken forntida stad förstördes av vulkanen Vesuvius år 79 e.Kr.?",
                        List.of("Pompeji", "Kartago", "Aten", "Sparta"), "Pompeji"),
                // 21
                new QuizQuestion("Vilket år bröt första världskriget ut?",
                        List.of("1905", "1914", "1920", "1933"), "1914"),
                // 22
                new QuizQuestion("Vem var känd som 'Järnladyn'?",
                        List.of("Angela Merkel", "Margaret Thatcher", "Indira Gandhi", "Golda Meir"), "Margaret Thatcher"),
                // 23
                new QuizQuestion("Vilken svensk kung införde arvskungadöme på 1500-talet?",
                        List.of("Gustav Vasa", "Karl IX", "Erik XIV", "Johan III"), "Gustav Vasa"),
                // 24
                new QuizQuestion("Vilken religion grundades av Siddharta Gautama?",
                        List.of("Kristendom", "Islam", "Buddhism", "Judendom"), "Buddhism"),
                // 25
                new QuizQuestion("Vad kallades konflikten mellan USA och Sovjetunionen efter andra världskriget?",
                        List.of("Det heta kriget", "Kalla kriget", "Frihetskriget", "Stora kriget"), "Kalla kriget"),
                // 26
                new QuizQuestion("Vilket år föll Berlinmuren?",
                        List.of("1969", "1979", "1989", "1999"), "1989"),
                // 27
                new QuizQuestion("Vilken berömd kinesisk mur byggdes för att skydda mot invasioner från norr?",
                        List.of("Silkesvägen", "Kinesiska muren", "Porslinsmuren", "Drakmuren"), "Kinesiska muren"),
                // 28
                new QuizQuestion("Vem skrev Den kommunistiska manifestet tillsammans med Friedrich Engels?",
                        List.of("Karl Marx", "Lenin", "Stalin", "Mao Zedong"), "Karl Marx"),
                // 29
                new QuizQuestion("Vilket land koloniserade stora delar av Indien fram till 1900-talet?",
                        List.of("Frankrike", "Storbritannien", "Spanien", "Portugal"), "Storbritannien"),
                // 30
                new QuizQuestion("Vilken revolution startade år 1917 och ledde till Sovjetunionens bildande?",
                        List.of("Franska revolutionen", "Ryska revolutionen", "Industriella revolutionen", "Kinesiska revolutionen"), "Ryska revolutionen")
        )));

        // Film & TV
        categoryQuestions.put("Film & TV", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("Vem spelade Jack i filmen Titanic?",
                        List.of("Leonardo DiCaprio", "Brad Pitt", "Tom Cruise", "Keanu Reeves"), "Leonardo DiCaprio"),
                // 2
                new QuizQuestion("Vilken superhjälte kallas 'The Dark Knight'?",
                        List.of("Superman", "Batman", "Spiderman", "Iron Man"), "Batman"),
                // 3
                new QuizQuestion("Vilken filmserie innehåller trollkarlen Voldemort?",
                        List.of("Star Wars", "Harry Potter", "Sagan om Ringen", "Narnia"), "Harry Potter"),
                // 4
                new QuizQuestion("Vad heter den gula familjen i den animerade TV-serien som bor i Springfield?",
                        List.of("The Griffins", "The Simpsons", "The Flynns", "The Owens"), "The Simpsons"),
                // 5
                new QuizQuestion("Vilken streamingtjänst är känd för sin röda logotyp och originalserier som Stranger Things?",
                        List.of("Netflix", "HBO Max", "Disney+", "Amazon Prime Video"), "Netflix"),
                // 6
                new QuizQuestion("Vem regisserade Sagan om Ringen-trilogin?",
                        List.of("Peter Jackson", "Steven Spielberg", "James Cameron", "Ridley Scott"), "Peter Jackson"),
                // 7
                new QuizQuestion("Vilken svensk skådespelare spelar policyn Martin Beck i de nyare Beck-filmerna?",
                        List.of("Peter Haber", "Mikael Persbrandt", "Rolf Lassgård", "Krister Henriksson"), "Peter Haber"),
                // 8
                new QuizQuestion("I vilken filmserie förekommer rymdskeppet Millennium Falcon?",
                        List.of("Star Trek", "Star Wars", "Battlestar Galactica", "Alien"), "Star Wars"),
                // 9
                new QuizQuestion("Vad heter pojken som är huvudperson i filmen Ensam hemma?",
                        List.of("Kevin", "Tommy", "Billy", "Charlie"), "Kevin"),
                // 10
                new QuizQuestion("Vilken svensk komediserie utspelar sig i den fiktiva orten Molkom?",
                        List.of("Solsidan", "Ack Värmland", "Bonusfamiljen", "Kvarteret Skatan"), "Ack Värmland"),
                // 11
                new QuizQuestion("Vilket djur är huvudkaraktären i filmen Lejonkungen?",
                        List.of("Tiger", "Lejon", "Gepard", "Leopard"), "Lejon"),
                // 12
                new QuizQuestion("Vilken skådespelare spelar Iron Man i Marvel-filmerna?",
                        List.of("Robert Downey Jr.", "Chris Evans", "Mark Ruffalo", "Chris Hemsworth"), "Robert Downey Jr."),
                // 13
                new QuizQuestion("Vad heter trollkarlen med glasögon i en känd brittisk filmserie?",
                        List.of("Harry Potter", "Merlin", "Gandalf", "Dumbledore"), "Harry Potter"),
                // 14
                new QuizQuestion("Vilken svensk realityserie utspelar sig på en öde ö med tävlande som röstas ut?",
                        List.of("Farmen", "Expedition Robinson", "Big Brother", "Paradise Hotel"), "Expedition Robinson"),
                // 15
                new QuizQuestion("Vilken Pixarfilm handlar om leksaker som får liv när människorna inte ser?",
                        List.of("Toy Story", "Bilar", "Hitta Nemo", "Superhjältarna"), "Toy Story"),
                // 16
                new QuizQuestion("Vem spelar huvudrollen som Jack Sparrow i Pirates of the Caribbean?",
                        List.of("Johnny Depp", "Orlando Bloom", "Hugh Jackman", "Matt Damon"), "Johnny Depp"),
                // 17
                new QuizQuestion("Vilken TV-serie följer en kemi-lärare som börjar tillverka droger?",
                        List.of("Breaking Bad", "Narcos", "Ozark", "The Wire"), "Breaking Bad"),
                // 18
                new QuizQuestion("Vilken fantasyserie med drakar och maktspel bygger på böcker av George R.R. Martin?",
                        List.of("The Witcher", "Game of Thrones", "Shadow and Bone", "Wheel of Time"), "Game of Thrones"),
                // 19
                new QuizQuestion("Vad heter fiskpojken i filmen Hitta Nemo?",
                        List.of("Nemo", "Dory", "Marlin", "Bruce"), "Nemo"),
                // 20
                new QuizQuestion("Vilken svensk humorserie utspelar sig i villaområdet där Fredde och Mickan bor?",
                        List.of("Bonusfamiljen", "Solsidan", "Partaj", "Kvarteret Skatan"), "Solsidan"),
                // 21
                new QuizQuestion("Vilken film handlar om en isdrottning vid namn Elsa?",
                        List.of("Frost", "Tangled", "Brave", "Mulan"), "Frost"),
                // 22
                new QuizQuestion("Vem spelade Neo i filmen The Matrix?",
                        List.of("Keanu Reeves", "Tom Cruise", "Brad Pitt", "Will Smith"), "Keanu Reeves"),
                // 23
                new QuizQuestion("Vad heter pandabjörnen som lär sig kung fu i en animerad filmserie?",
                        List.of("Po", "Bao", "Li", "Ping"), "Po"),
                // 24
                new QuizQuestion("Vilken film utspelar sig på en skola för mutanter ledda av Professor X?",
                        List.of("Avengers", "X-Men", "Fantastic Four", "Justice League"), "X-Men"),
                // 25
                new QuizQuestion("Vad heter rymdpatrullen i den animerade filmen om Buzz Lightyear?",
                        List.of("Star Command", "Galaxy Force", "Space Patrol", "Lightyear Squad"), "Star Command"),
                // 26
                new QuizQuestion("Vilken svensk skådespelare spelar polisen Kurt Wallander i flera filmer?",
                        List.of("Krister Henriksson", "Rolf Lassgård", "Peter Haber", "Per Morberg"), "Krister Henriksson"),
                // 27
                new QuizQuestion("Vilken filmserie handlar om ringbäraren Frodo?",
                        List.of("Sagan om Ringen", "Harry Potter", "Narnia", "Star Wars"), "Sagan om Ringen"),
                // 28
                new QuizQuestion("Vilket yrke har karaktären Barney Stinson i How I Met Your Mother?",
                        List.of("Okänt/hemligt", "Läkare", "Skådespelare", "Jurist"), "Okänt/hemligt"),
                // 29
                new QuizQuestion("Vad heter roboten i Star Wars som är liten och vit/orange i de senare filmerna?",
                        List.of("R2-D2", "BB-8", "C-3PO", "K-2SO"), "BB-8"),
                // 30
                new QuizQuestion("Vilken filmklassiker innehåller repliken 'May the Force be with you'?",
                        List.of("Star Wars", "Star Trek", "Avatar", "Blade Runner"), "Star Wars")
        )));
// Geografi
        categoryQuestions.put("Geografi", new ArrayList<>(List.of(
                new QuizQuestion("Vilket är världens största land till ytan?",
                        List.of("Kanada", "Kina", "Ryssland", "USA"), "Ryssland"),
                new QuizQuestion("Vilken är Sveriges längsta flod?",
                        List.of("Göta älv", "Dalälven", "Klarälven", "Lule älv"), "Klarälven"),
                new QuizQuestion("Vilken ö är världens största?",
                        List.of("Grönland", "Nya Guinea", "Borneo", "Madagaskar"), "Grönland"),
                new QuizQuestion("Vilken stad är huvudstad i Sverige?",
                        List.of("Göteborg", "Malmö", "Stockholm", "Uppsala"), "Stockholm"),
                new QuizQuestion("Vilken kontinent ligger landet Brasilien på?",
                        List.of("Nordamerika", "Europa", "Asien", "Sydamerika"), "Sydamerika"),
                new QuizQuestion("Vilket hav ligger mellan Europa och Afrika?",
                        List.of("Atlanten", "Indiska oceanen", "Medelhavet", "Stilla havet"), "Medelhavet"),
                new QuizQuestion("Vad heter världens högsta berg?",
                        List.of("K2", "Kilimanjaro", "Mount Everest", "Mont Blanc"), "Mount Everest"),
                new QuizQuestion("Vilket land har städerna Sydney och Melbourne?",
                        List.of("Nya Zeeland", "Australien", "Kanada", "Sydafrika"), "Australien"),
                new QuizQuestion("Vilken är Norges huvudstad?",
                        List.of("Oslo", "Bergen", "Trondheim", "Stavanger"), "Oslo"),
                new QuizQuestion("Vilket land har formen av en stövel på kartan?",
                        List.of("Spanien", "Portugal", "Italien", "Grekland"), "Italien"),
                new QuizQuestion("Vad heter floden som rinner genom Egypten?",
                        List.of("Amazonas", "Mississippi", "Nilen", "Rhen"), "Nilen"),
                new QuizQuestion("Vilket land ligger INTE i Norden?",
                        List.of("Danmark", "Finland", "Tyskland", "Island"), "Tyskland"),
                new QuizQuestion("Vilken är världens största kontinent?",
                        List.of("Europa", "Asien", "Afrika", "Nordamerika"), "Asien"),
                new QuizQuestion("Vilket land är känt som 'soluppgångens land'?",
                        List.of("Kina", "Japan", "Thailand", "Vietnam"), "Japan"),
                new QuizQuestion("Vad heter huvudstaden i Finland?",
                        List.of("Helsingfors", "Åbo", "Tammerfors", "Vasa"), "Helsingfors"),
                new QuizQuestion("Vilken ögrupp tillhör Sverige?",
                        List.of("Färöarna", "Shetlandsöarna", "Åland", "Gotland"), "Gotland"),
                new QuizQuestion("Vilket land gränsar INTE till Sverige?",
                        List.of("Norge", "Finland", "Danmark", "Ryssland"), "Ryssland"),
                new QuizQuestion("Vilket hav ligger öster om Sverige?",
                        List.of("Nordsjön", "Medelhavet", "Östersjön", "Svarta havet"), "Östersjön"),
                new QuizQuestion("Vilken världsdel ligger helt söder om ekvatorn?",
                        List.of("Afrika", "Antarktis", "Australien", "Sydamerika"), "Antarktis"),
                new QuizQuestion("Vad heter Spaniens huvudstad?",
                        List.of("Barcelona", "Madrid", "Valencia", "Sevilla"), "Madrid"),
                new QuizQuestion("Vilket land har flest invånare?",
                        List.of("USA", "Indien", "Kina", "Indonesien"), "Kina"),
                new QuizQuestion("Vad kallas en mycket stor öken i norra Afrika?",
                        List.of("Gobi", "Sahara", "Kalahari", "Taklamakan"), "Sahara"),
                new QuizQuestion("Vilket land hör staden New York till?",
                        List.of("Kanada", "USA", "Mexiko", "Storbritannien"), "USA"),
                new QuizQuestion("Vilken huvudstad ligger närmast Stockholm?",
                        List.of("Oslo", "Köpenhamn", "Helsingfors", "Reykjavik"), "Helsingfors"),
                new QuizQuestion("Vilket land är känt för fjordar och olja i Nordsjön?",
                        List.of("Danmark", "Norge", "Nederländerna", "Irland"), "Norge"),
                new QuizQuestion("Vilken stor sjö ligger mellan Sverige och Finland?",
                        List.of("Vättern", "Vänern", "Mälaren", "Bottenviken"), "Bottenviken"),
                new QuizQuestion("Vad heter Tysklands huvudstad?",
                        List.of("Hamburg", "München", "Berlin", "Frankfurt"), "Berlin"),
                new QuizQuestion("Vilket land ligger både i Europa och Asien?",
                        List.of("Spanien", "Turkiet", "Polen", "Grekland"), "Turkiet"),
                new QuizQuestion("Vilket land är känt för städerna Edinburgh och Glasgow?",
                        List.of("Irland", "Skottland", "Wales", "England"), "Skottland")
        )));

// Musik
        categoryQuestions.put("Musik", new ArrayList<>(List.of(
                new QuizQuestion("Vilket svenskt band vann Eurovision 1974 med låten 'Waterloo'?",
                        List.of("ABBA", "Roxette", "Ace of Base", "The Ark"), "ABBA"),
                new QuizQuestion("Vilket instrument har 88 tangenter?",
                        List.of("Piano", "Gitarr", "Orgel", "Saxofon"), "Piano"),
                new QuizQuestion("Vem kallas 'The King of Pop'?",
                        List.of("Elvis Presley", "Michael Jackson", "Prince", "David Bowie"), "Michael Jackson"),
                new QuizQuestion("Vilket svenskt band ligger bakom låten 'Dancing Queen'?",
                        List.of("ABBA", "Gyllene Tider", "Kent", "Europe"), "ABBA"),
                new QuizQuestion("Vilket instrument har strängar och spelas ofta med plektrum?",
                        List.of("Flöjt", "Trummor", "Gitarr", "Trumpet"), "Gitarr"),
                new QuizQuestion("Vilken musikstil förknippas med artister som Metallica och Iron Maiden?",
                        List.of("Pop", "Jazz", "Heavy metal", "Country"), "Heavy metal"),
                new QuizQuestion("Vilken svensk artist har gjort låten 'Fångad av en stormvind'?",
                        List.of("Carola", "Lena Philipsson", "Sanna Nielsen", "Charlotte Perrelli"), "Carola"),
                new QuizQuestion("Vilken genre förknippas med artisten Eminem?",
                        List.of("Rock", "Rap", "Country", "Klassiskt"), "Rap"),
                new QuizQuestion("Vilket land kommer gruppen The Beatles ifrån?",
                        List.of("USA", "Australien", "Storbritannien", "Kanada"), "Storbritannien"),
                new QuizQuestion("Vad kallas den person som leder en orkester?",
                        List.of("Dirigent", "Solist", "Producent", "Kompositör"), "Dirigent"),
                new QuizQuestion("Vilket instrument förknippas starkt med jazz?",
                        List.of("Harpa", "Saxofon", "Banjo", "Klarinett"), "Saxofon"),
                new QuizQuestion("Vilken svensk DJ har gjort låtar som 'Wake Me Up'?",
                        List.of("Avicii", "Swedish House Mafia", "Basshunter", "Alesso"), "Avicii"),
                new QuizQuestion("Vilken musikstil förknippas med Beethoven?",
                        List.of("Rock", "Klassisk musik", "Blues", "Techno"), "Klassisk musik"),
                new QuizQuestion("Vad heter Sveriges största musiktävling där vinnaren åker till Eurovision?",
                        List.of("Idol", "Talang", "Melodifestivalen", "Så ska det låta"), "Melodifestivalen"),
                new QuizQuestion("Vilket av dessa är ett truminstrument?",
                        List.of("Bastrumma", "Fiol", "Tvärflöjt", "Cello"), "Bastrumma"),
                new QuizQuestion("Vem sjunger 'Shape of You'?",
                        List.of("Ed Sheeran", "Justin Bieber", "Shawn Mendes", "Sam Smith"), "Ed Sheeran"),
                new QuizQuestion("Vilket av dessa är en känd svensk hårdrocksgrupp?",
                        List.of("Kent", "Europe", "First Aid Kit", "The Cardigans"), "Europe"),
                new QuizQuestion("Vilket instrument spelas oftast i klassisk piano–violin-duo?",
                        List.of("Trummor", "Gitarr", "Violin", "Saxofon"), "Violin"),
                new QuizQuestion("Vilken musikstil kommer ursprungligen från Jamaica?",
                        List.of("Reggae", "Blues", "Country", "Funk"), "Reggae"),
                new QuizQuestion("Vilken svensk grupp gjorde låten 'The Look'?",
                        List.of("ABBA", "Roxette", "Ace of Base", "A-Teens"), "Roxette"),
                new QuizQuestion("Vilken rösttyp är den ljusaste hos kvinnor?",
                        List.of("Alt", "Mezzosopran", "Sopran", "Baryton"), "Sopran"),
                new QuizQuestion("Vilket instrument är slagverk?",
                        List.of("Cello", "Piano", "Trummor", "Oboe"), "Trummor"),
                new QuizQuestion("Vem sjunger låten 'Rolling in the Deep'?",
                        List.of("Adele", "Taylor Swift", "Rihanna", "Beyoncé"), "Adele"),
                new QuizQuestion("Vilken musikal utspelar sig i Paris och har karaktären 'Upprorsledaren Enjolras'?",
                        List.of("Cats", "Les Misérables", "The Phantom of the Opera", "Mamma Mia!"), "Les Misérables"),
                new QuizQuestion("Vilket band gjorde albumet 'Nevermind'?",
                        List.of("Nirvana", "Pearl Jam", "Green Day", "Red Hot Chili Peppers"), "Nirvana"),
                new QuizQuestion("Vilken genre förknippas med artisten Dolly Parton?",
                        List.of("Country", "Hip hop", "House", "Metal"), "Country"),
                new QuizQuestion("Vilken svensk festival i Norrköping var tidigare en av de största rockfestivalerna?",
                        List.of("Sweden Rock", "Bråvalla", "Way Out West", "Hultsfred"), "Bråvalla"),
                new QuizQuestion("Vilket instrument används ofta i folkmusik och har stråke?",
                        List.of("Fiol", "Klarinett", "Saxofon", "Trombon"), "Fiol"),
                new QuizQuestion("Vilken svensk grupp ligger bakom låten 'Glorious'?",
                        List.of("Style", "Army of Lovers", "Måns Zelmerlöw", "Andreas Johnson"), "Andreas Johnson")
        )));

// Vetenskap
        categoryQuestions.put("Vetenskap", new ArrayList<>(List.of(
                new QuizQuestion("Vilket grundämne har kemiska beteckningen O?",
                        List.of("Guld", "Silver", "Syre", "Väte"), "Syre"),
                new QuizQuestion("Hur många planeter finns i vårt solsystem?",
                        List.of("7", "8", "9", "10"), "8"),
                new QuizQuestion("Vilken vetenskapsman formulerade relativitetsteorin?",
                        List.of("Isaac Newton", "Albert Einstein", "Nikola Tesla", "Stephen Hawking"), "Albert Einstein"),
                new QuizQuestion("Vilken planet kallas 'den röda planeten'?",
                        List.of("Venus", "Mars", "Jupiter", "Merkurius"), "Mars"),
                new QuizQuestion("Vilken del av kroppen pumpar runt blodet?",
                        List.of("Lungorna", "Magsäcken", "Hjärtat", "Levern"), "Hjärtat"),
                new QuizQuestion("Vilken enhet mäter elektrisk ström?",
                        List.of("Volt", "Ampere", "Watt", "Ohm"), "Ampere"),
                new QuizQuestion("Vilket organ i kroppen ansvarar för andningen tillsammans med diafragman?",
                        List.of("Njurar", "Lungor", "Lever", "Magsäck"), "Lungor"),
                new QuizQuestion("Vad kallas den kraft som drar oss mot jordens centrum?",
                        List.of("Tryck", "Magnetism", "Gravitation", "Friction"), "Gravitation"),
                new QuizQuestion("Vilken del av blomman innehåller frön?",
                        List.of("Stjälk", "Krona", "Fruktämne", "Blad"), "Fruktämne"),
                new QuizQuestion("Vilken av följande är en gas vid rumstemperatur?",
                        List.of("Järn", "Vatten", "Syre", "Salt"), "Syre"),
                new QuizQuestion("Vilken vetenskapsgren handlar om himlakroppar och universum?",
                        List.of("Biologi", "Astronomi", "Geologi", "Kemi"), "Astronomi"),
                new QuizQuestion("Vad heter den process där växter omvandlar ljus till energi?",
                        List.of("Fermentation", "Fotosyntes", "Glykolys", "Respiration"), "Fotosyntes"),
                new QuizQuestion("Vilken metall finns det mycket av i blodets hemoglobin?",
                        List.of("Koppar", "Zink", "Järn", "Silver"), "Järn"),
                new QuizQuestion("Vilken planet är störst i vårt solsystem?",
                        List.of("Jorden", "Saturnus", "Jupiter", "Neptunus"), "Jupiter"),
                new QuizQuestion("Vilken vetenskapsgren studerar levande organismer?",
                        List.of("Fysik", "Kemi", "Biologi", "Geologi"), "Biologi"),
                new QuizQuestion("Vilken temperatur motsvarar vattenets fryspunkt i Celsius?",
                        List.of("-10", "0", "5", "10"), "0"),
                new QuizQuestion("Vilket sinne använder vi främst för att känna smak?",
                        List.of("Öronen", "Ögonen", "Tungan", "Huden"), "Tungan"),
                new QuizQuestion("Vilken av dessa är en ädelgas?",
                        List.of("Syre", "Kväve", "Helium", "Koldioxid"), "Helium"),
                new QuizQuestion("Vad kallas den minsta enheten i alla levande organismer?",
                        List.of("Organ", "Vävnad", "Cell", "Atom"), "Cell"),
                new QuizQuestion("Vad kallas övergången när vatten går från flytande form till gas?",
                        List.of("Frysning", "Smältning", "Avdunstning", "Kondensering"), "Avdunstning"),
                new QuizQuestion("Vilken del av ögat reglerar hur mycket ljus som släpps in?",
                        List.of("Linsen", "Näthinnan", "Pupillen", "Hornhinnan"), "Pupillen"),
                new QuizQuestion("Vad kallas läran om jordens lager och bergarter?",
                        List.of("Astronomi", "Geologi", "Ekologi", "Meteorologi"), "Geologi"),
                new QuizQuestion("Vilken kraft gör det svårt att skjuta ett tungt skåp över golvet?",
                        List.of("Gravitation", "Friction", "Magnetism", "Tryck"), "Friction"),
                new QuizQuestion("Vad kallas en blandning där man inte kan se de olika delarna, till exempel salt i vatten?",
                        List.of("Lösning", "Suspension", "Emulsion", "Gas"), "Lösning"),
                new QuizQuestion("Vad heter vår galax?",
                        List.of("Andromeda", "Vintergatan", "Orion", "Pegasus"), "Vintergatan"),
                new QuizQuestion("Vilken celltyp transporterar syre i blodet?",
                        List.of("Vita blodkroppar", "Röda blodkroppar", "Blodplättar", "Stamceller"), "Röda blodkroppar"),
                new QuizQuestion("Vilket av följande är ett fossilt bränsle?",
                        List.of("Solenergi", "Vindkraft", "Stenkol", "Vattenkraft"), "Stenkol"),
                new QuizQuestion("Vilken av dessa planeter har ringar som är tydligt synliga?",
                        List.of("Venus", "Mars", "Jupiter", "Saturnus"), "Saturnus"),
                new QuizQuestion("Vad kallas resultatet när två vätskor blandas och det bildas små droppar av den ena i den andra (t.ex. olja i vatten)?",
                        List.of("Emulsion", "Lösning", "Gas", "Fast form"), "Emulsion")
        )));

// Djur
        categoryQuestions.put("Djur", new ArrayList<>(List.of(
                new QuizQuestion("Vilket är världens största däggdjur?",
                        List.of("Elefant", "Blåval", "Giraff", "Flodhäst"), "Blåval"),
                new QuizQuestion("Vilket djur kallas 'skogarnas konung' i Sverige?",
                        List.of("Björn", "Varg", "Älg", "Lodjur"), "Älg"),
                new QuizQuestion("Vilket av dessa djur kan flyga?",
                        List.of("Piggsvin", "Struts", "Fladdermus", "Pingvin"), "Fladdermus"),
                new QuizQuestion("Vilket djur säger man 'vov' om?",
                        List.of("Katt", "Hund", "Ko", "Get"), "Hund"),
                new QuizQuestion("Vad kallas en unge till en ko?",
                        List.of("Kalv", "Lamm", "Killing", "Föl"), "Kalv"),
                new QuizQuestion("Vilket djur har svartvita ränder?",
                        List.of("Giraff", "Zebra", "Leopard", "Tiger"), "Zebra"),
                new QuizQuestion("Vilket djur sover vintersömn?",
                        List.of("Häst", "Ko", "Björn", "Räv"), "Björn"),
                new QuizQuestion("Vilket av dessa djur lever mest i vatten?",
                        List.of("Räv", "Utter", "Varg", "Hare"), "Utter"),
                new QuizQuestion("Vilket djur är känt för att vara människans bästa vän?",
                        List.of("Hund", "Katt", "Kanin", "Hamster"), "Hund"),
                new QuizQuestion("Vilken fågel kan inte flyga?",
                        List.of("Skata", "Örn", "And", "Struts"), "Struts"),
                new QuizQuestion("Vilket djur producerar honung?",
                        List.of("Geting", "Humla", "Bi", "Mygga"), "Bi"),
                new QuizQuestion("Vilket djur har en lång snabel?",
                        List.of("Noshörning", "Flodhäst", "Elefant", "Giraff"), "Elefant"),
                new QuizQuestion("Vilket djur lever främst i Arktis?",
                        List.of("Lejon", "Ispanda", "Isbjörn", "Zebra"), "Isbjörn"),
                new QuizQuestion("Vilket djur kan byta färg för att kamouflera sig?",
                        List.of("Bläckfisk", "Kameleont", "Krabba", "Delfin"), "Kameleont"),
                new QuizQuestion("Vad kallas en grupp vargar?",
                        List.of("Hjord", "Flock", "Skock", "Stim"), "Flock"),
                new QuizQuestion("Vilket djur är känt för att hänga upp och ner i träden?",
                        List.of("Apa", "Slöjdjur (trefingerlat)", "Panter", "Leopard"), "Slöjdjur (trefingerlat)"),
                new QuizQuestion("Vilket djur lägger ägg?",
                        List.of("Hund", "Katt", "Höna", "Ko"), "Höna"),
                new QuizQuestion("Vilket djur är en rovfågel?",
                        List.of("Örn", "Gås", "Höna", "And"), "Örn"),
                new QuizQuestion("Vilket djur har en pung för sina ungar?",
                        List.of("Tiger", "Känguru", "Björn", "Delfin"), "Känguru"),
                new QuizQuestion("Vilket djur kommunicerar med klickljud under vattnet?",
                        List.of("Haj", "Delfin", "Valhaj", "Säl"), "Delfin"),
                new QuizQuestion("Vilket djur kallas 'savannens konung'?",
                        List.of("Lejon", "Gepard", "Hyena", "Buffel"), "Lejon"),
                new QuizQuestion("Vilket djur har en mankhöjd på över fem meter och lång hals?",
                        List.of("Giraff", "Elefant", "Kameldjur", "Noshörning"), "Giraff"),
                new QuizQuestion("Vilket djur är känt för att vara långsamt och bära sitt hus på ryggen?",
                        List.of("Snigel", "Krabba", "Sköldpadda", "Snäcka"), "Snigel"),
                new QuizQuestion("Vilket djur har åtta ben?",
                        List.of("Insekt", "Spindel", "Mask", "Fisk"), "Spindel"),
                new QuizQuestion("Vilket djur är störst av kattdjuren?",
                        List.of("Leopard", "Gepard", "Lejon", "Tiger"), "Tiger"),
                new QuizQuestion("Vilket djur kallas 'kungen av djungeln'?",
                        List.of("Tiger", "Lejon", "Gorilla", "Panter"), "Lejon"),
                new QuizQuestion("Vilket av dessa djur är ett gnagare?",
                        List.of("Katt", "Hund", "Kanin", "Får"), "Kanin"),
                new QuizQuestion("Vilket djur har fjäll och andas med gälar?",
                        List.of("Fågel", "Fisk", "Groda", "Orm"), "Fisk"),
                new QuizQuestion("Vilket djur har en kraftig svans och bygger dammar?",
                        List.of("Bäver", "Räv", "Varg", "Iller"), "Bäver")
        )));

// Mat & Dryck
        categoryQuestions.put("Mat & Dryck", new ArrayList<>(List.of(
                new QuizQuestion("Vilket land kommer pizzan ursprungligen ifrån?",
                        List.of("USA", "Spanien", "Italien", "Portugal"), "Italien"),
                new QuizQuestion("Vilken ingrediens är huvudbasen i guacamole?",
                        List.of("Tomat", "Avokado", "Majs", "Paprika"), "Avokado"),
                new QuizQuestion("Vilket land är känt för att producera sushi?",
                        List.of("Kina", "Japan", "Thailand", "Vietnam"), "Japan"),
                new QuizQuestion("Vilket spannmål används traditionellt för att göra pasta?",
                        List.of("Havre", "Vete", "Råg", "Korn"), "Vete"),
                new QuizQuestion("Vad heter den svenska rätten med rå fisk som äts på julbord och midsommar?",
                        List.of("Gravad lax", "Sill", "Strömming", "Surströmming"), "Sill"),
                new QuizQuestion("Vilken dryck görs av pressade vindruvor?",
                        List.of("Juice", "Vin", "Cider", "Saft"), "Vin"),
                new QuizQuestion("Vilken krydda är gul och används i t.ex. paella?",
                        List.of("Saffran", "Paprika", "Kanel", "Koriander"), "Saffran"),
                new QuizQuestion("Vilket land är känt för rätten 'tacos'?",
                        List.of("Mexiko", "Spanien", "Argentina", "Chile"), "Mexiko"),
                new QuizQuestion("Vad heter den italienska efterrätten med kaffe och mascarponeost?",
                        List.of("Panna cotta", "Tiramisu", "Gelato", "Cannoli"), "Tiramisu"),
                new QuizQuestion("Vilken dryck görs traditionellt av äpplen och kan vara både alkoholfri och alkoholhaltig?",
                        List.of("Cider", "Saft", "Läsk", "Smoothie"), "Cider"),
                new QuizQuestion("Vilken ingrediens behövs för att bröd ska jäsa?",
                        List.of("Salt", "Jäst", "Socker", "Mjölk"), "Jäst"),
                new QuizQuestion("Vilken nationalrätt förknippas ofta med Sverige?",
                        List.of("Lasagne", "Tacos", "Köttbullar", "Paella"), "Köttbullar"),
                new QuizQuestion("Vilken frukt är gul och böjd?",
                        List.of("Banan", "Päron", "Äpple", "Citron"), "Banan"),
                new QuizQuestion("Vilken grönsak är huvudingredienser i klassisk tomatsoppa?",
                        List.of("Morot", "Tomat", "Purjolök", "Broccoli"), "Tomat"),
                new QuizQuestion("Vad kallas frysta vattenkuber som ofta läggs i dryck?",
                        List.of("Snö", "Isbitar", "Granulat", "Frost"), "Isbitar"),
                new QuizQuestion("Vilken typ av kött kommer från gris?",
                        List.of("Nötkött", "Lamm", "Fläskkött", "Vilt"), "Fläskkött"),
                new QuizQuestion("Vad heter den typiska japanska sojasoppan med tofu?",
                        List.of("Ramen", "Miso-soppa", "Pho", "Tom Yum"), "Miso-soppa"),
                new QuizQuestion("Vilken grönsak är orange och växer i jorden?",
                        List.of("Tomat", "Morot", "Gurka", "Paprika"), "Morot"),
                new QuizQuestion("Vad är huvudingrediensen i hummus?",
                        List.of("Linser", "Kikärtor", "Bönor", "Potatis"), "Kikärtor"),
                new QuizQuestion("Vilket land förknippas med tapas?",
                        List.of("Frankrike", "Spanien", "Italien", "Portugal"), "Spanien"),
                new QuizQuestion("Vilket bär används ofta för att göra sylt till pannkakor i Sverige?",
                        List.of("Jordgubbar", "Blåbär", "Lingon", "Hallon"), "Lingon"),
                new QuizQuestion("Vilken dryck görs av malda kaffebönor?",
                        List.of("Te", "Kaffe", "Läsk", "Juice"), "Kaffe"),
                new QuizQuestion("Vad är tofu gjort av?",
                        List.of("Ris", "Sojabönor", "Majs", "Vete"), "Sojabönor"),
                new QuizQuestion("Vilken soppa görs traditionellt på ärtor och äts ofta med fläsk?",
                        List.of("Tomatsoppa", "Ärtsoppa", "Potatissoppa", "Svampsoppa"), "Ärtsoppa"),
                new QuizQuestion("Vad heter den franska rätten där man bränner socker på toppen av en vaniljkräm?",
                        List.of("Crème brûlée", "Éclair", "Profiterole", "Tarte tatin"), "Crème brûlée"),
                new QuizQuestion("Vilken frukt är huvudingredien i en klassisk äppelpaj?",
                        List.of("Päron", "Äpple", "Banan", "Apelsin"), "Äpple"),
                new QuizQuestion("Vad kallas den klassiska rätten med köttfärssås och pasta?",
                        List.of("Lasagne", "Spaghetti Bolognese", "Carbonara", "Pesto"), "Spaghetti Bolognese"),
                new QuizQuestion("Vilken grönsak är grön, avlång och ofta används i sallad?",
                        List.of("Gurka", "Rödbeta", "Palsternacka", "Majs"), "Gurka"),
                new QuizQuestion("Vad heter den italienska rätten där ris kokas krämigt med buljong?",
                        List.of("Risotto", "Gnocchi", "Polenta", "Focaccia"), "Risotto")
        )));


    }
}

