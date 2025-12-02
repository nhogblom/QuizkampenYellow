package org.example.server;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class QuestionRepository {
    private Map<String, List<QuizQuestion>> categoryQuestions = new HashMap<>();
    private Random random = new Random();
    private QuestionStorage questionStorage = new QuestionStorage();

    public QuestionRepository() {
        Map<String, List<QuizQuestion>> loadedFile = questionStorage.loadQuestions();
        if (loadedFile.isEmpty()) {
            System.out.println("No saved questions found, loading hardcode.");
//            loadHardcodedQuestions();
//            questionStorage.saveQuestion(categoryQuestions);
        } else {
            categoryQuestions = loadedFile;
            System.out.println("Questions.json loaded successfully from file.");
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
        // Teknik – 30 questions, English (Technology & Computers)
        categoryQuestions.put("Teknik", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("What does the acronym 'CPU' stand for?",
                        List.of("Central Processing Unit", "Core Programming Unit", "Central Peripheral Unit", "Computer Processing Utility"),
                        "Central Processing Unit"),
                // 2
                new QuizQuestion("Which company developed the operating system Windows?",
                        List.of("Apple", "Microsoft", "IBM", "Google"),
                        "Microsoft"),
                // 3
                new QuizQuestion("What does the 'http' in a web address stand for?",
                        List.of("HyperText Transfer Protocol", "High Transmission Text Protocol", "Hyperlink Transport Process", "Host Transfer Text Protocol"),
                        "HyperText Transfer Protocol"),
                // 4
                new QuizQuestion("In computing, what is the main function of RAM?",
                        List.of("Permanent storage", "Processing graphics", "Temporary working memory", "Internet connection"),
                        "Temporary working memory"),
                // 5
                new QuizQuestion("Which programming language is commonly used for Android app development?",
                        List.of("Swift", "Kotlin", "Ruby", "PHP"),
                        "Kotlin"),
                // 6
                new QuizQuestion("What is the name of the worldwide network of interconnected computers?",
                        List.of("The Web", "The Internet", "The Cloud", "The Grid"),
                        "The Internet"),
                // 7
                new QuizQuestion("Which company created the iPhone?",
                        List.of("Samsung", "Nokia", "Apple", "Huawei"),
                        "Apple"),
                // 8
                new QuizQuestion("What does 'SSD' stand for in computer storage?",
                        List.of("System Storage Device", "Solid State Drive", "Serial Storage Disk", "Secure State Disk"),
                        "Solid State Drive"),
                // 9
                new QuizQuestion("Which number system do computers primarily use internally?",
                        List.of("Decimal", "Binary", "Hexadecimal", "Octal"),
                        "Binary"),
                // 10
                new QuizQuestion("What does the 'G' in '5G' stand for?",
                        List.of("Gigabyte", "Generation", "Global", "Grid"),
                        "Generation"),
                // 11
                new QuizQuestion("Which company created the Android operating system (before it was acquired and developed further)?",
                        List.of("Android Inc.", "Google", "IBM", "Nokia"),
                        "Android Inc."),
                // 12
                new QuizQuestion("What does 'GPU' stand for?",
                        List.of("General Processing Unit", "Graphics Processing Unit", "Global Processing Unit", "Graphic Peripheral Unit"),
                        "Graphics Processing Unit"),
                // 13
                new QuizQuestion("Which protocol is commonly used to send emails?",
                        List.of("FTP", "HTTP", "SMTP", "SSH"),
                        "SMTP"),
                // 14
                new QuizQuestion("What does 'URL' stand for?",
                        List.of("Universal Resource Locator", "Uniform Resource Locator", "Unified Reference Link", "Universal Reference Locator"),
                        "Uniform Resource Locator"),
                // 15
                new QuizQuestion("Which of these is an example of an open-source operating system?",
                        List.of("Windows 11", "macOS", "Linux", "Chrome OS"),
                        "Linux"),
                // 16
                new QuizQuestion("In cybersecurity, what is 'phishing'?",
                        List.of("A type of data encryption", "A fake attempt to steal personal information", "A method to speed up networks", "A hardware failure"),
                        "A fake attempt to steal personal information"),
                // 17
                new QuizQuestion("Which device converts digital signals to analog for display on a monitor?",
                        List.of("CPU", "RAM", "GPU", "SSD"),
                        "GPU"),
                // 18
                new QuizQuestion("What is the name of the small picture or symbol representing a program on your desktop?",
                        List.of("Cursor", "Icon", "Tab", "Widget"),
                        "Icon"),
                // 19
                new QuizQuestion("Which technology is commonly used to protect wireless networks?",
                        List.of("WPA2/WPA3", "HTML", "FTP", "PHP"),
                        "WPA2/WPA3"),
                // 20
                new QuizQuestion("What does 'IoT' stand for in technology?",
                        List.of("Internet of Tools", "Internet of Things", "Interface of Technology", "Integration of Terminals"),
                        "Internet of Things"),
                // 21
                new QuizQuestion("Which company created the voice assistant 'Siri'?",
                        List.of("Google", "Amazon", "Apple", "Microsoft"),
                        "Apple"),
                // 22
                new QuizQuestion("Which unit is commonly used to measure the speed of a processor?",
                        List.of("Megabytes", "Gigahertz", "Gigabytes", "Milliseconds"),
                        "Gigahertz"),
                // 23
                new QuizQuestion("What is 'open source' software?",
                        List.of("Software that is free to download illegally", "Software with publicly available source code", "Software that never needs updates", "Software only used by companies"),
                        "Software with publicly available source code"),
                // 24
                new QuizQuestion("Which of these is a version control system used by developers?",
                        List.of("Git", "Bash", "Excel", "Docker"),
                        "Git"),
                // 25
                new QuizQuestion("What does 'HTML' stand for?",
                        List.of("HyperText Markup Language", "HighText Machine Language", "HyperTransfer Media Language", "Hyperlink Markup Language"),
                        "HyperText Markup Language"),
                // 26
                new QuizQuestion("Which device is commonly used to connect multiple network devices within the same local network?",
                        List.of("Router", "Switch", "Modem", "Firewall"),
                        "Switch"),
                // 27
                new QuizQuestion("Which company developed the game engine 'Unreal Engine'?",
                        List.of("Valve", "Epic Games", "Electronic Arts", "Unity Technologies"),
                        "Epic Games"),
                // 28
                new QuizQuestion("What does the 'cloud' usually refer to in IT?",
                        List.of("Wireless routers", "Remote servers accessed over the internet", "Local external hard drives", "Web browsers"),
                        "Remote servers accessed over the internet"),
                // 29
                new QuizQuestion("Which term describes malicious software designed to damage or disrupt systems?",
                        List.of("Malware", "Firmware", "Shareware", "Middleware"),
                        "Malware"),
                // 30
                new QuizQuestion("Which of these is an example of a web browser?",
                        List.of("Chrome", "Linux", "Photoshop", "Dropbox"),
                        "Chrome")
        )));


// Gaming – 30 questions, English (Video Games)
        categoryQuestions.put("Gaming", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("Which company created the video game character Mario?",
                        List.of("Sega", "Sony", "Nintendo", "Microsoft"),
                        "Nintendo"),
                // 2
                new QuizQuestion("In 'The Legend of Zelda' series, what is the name of the main hero?",
                        List.of("Zelda", "Link", "Ganon", "Sheik"),
                        "Link"),
                // 3
                new QuizQuestion("Which game series features the regions Kanto, Johto and Hoenn?",
                        List.of("Final Fantasy", "Pokémon", "Monster Hunter", "Dragon Quest"),
                        "Pokémon"),
                // 4
                new QuizQuestion("In 'Minecraft', which material do you need to mine to craft a Nether portal?",
                        List.of("Obsidian", "Diamond", "Emerald", "Quartz"),
                        "Obsidian"),
                // 5
                new QuizQuestion("Which game developer created the 'Half-Life' series and the Steam platform?",
                        List.of("Valve", "id Software", "Blizzard", "Ubisoft"),
                        "Valve"),
                // 6
                new QuizQuestion("In 'Counter-Strike: Global Offensive', which two main teams face each other?",
                        List.of("Red vs Blue", "Terrorists vs Counter-Terrorists", "Humans vs Aliens", "Attackers vs Defenders"),
                        "Terrorists vs Counter-Terrorists"),
                // 7
                new QuizQuestion("Which massively popular online game features a battle royale mode set on an island with 100 players?",
                        List.of("World of Warcraft", "League of Legends", "Fortnite", "Overwatch"),
                        "Fortnite"),
                // 8
                new QuizQuestion("Which company produces the PlayStation console series?",
                        List.of("Microsoft", "Nintendo", "Sony", "Sega"),
                        "Sony"),
                // 9
                new QuizQuestion("In 'League of Legends', what are the computer-controlled creatures in lanes commonly called?",
                        List.of("Mobs", "Creeps/Minions", "NPCs", "Bots"),
                        "Creeps/Minions"),
                // 10
                new QuizQuestion("Which game puts players on the run from a huge, rolling boulder in its early levels and stars a treasure hunter named Nathan Drake?",
                        List.of("Uncharted", "Tomb Raider", "Prince of Persia", "Assassin's Creed"),
                        "Uncharted"),
                // 11
                new QuizQuestion("In 'Dark Souls', what do players typically collect from defeated enemies to level up?",
                        List.of("Coins", "Souls", "Blood", "Crystals"),
                        "Souls"),
                // 12
                new QuizQuestion("Which first-person shooter series is known for the phrase 'Finish the fight' and features Master Chief?",
                        List.of("Doom", "Halo", "Battlefield", "Call of Duty"),
                        "Halo"),
                // 13
                new QuizQuestion("In 'Overwatch', which character is known for the line 'Cheers, love! The cavalry's here!'?",
                        List.of("Widowmaker", "Tracer", "Mercy", "D.Va"),
                        "Tracer"),
                // 14
                new QuizQuestion("Which sandbox crime game series features cities like Liberty City, Vice City and Los Santos?",
                        List.of("Mafia", "Grand Theft Auto", "Saints Row", "Sleeping Dogs"),
                        "Grand Theft Auto"),
                // 15
                new QuizQuestion("Which famous puzzle game involves matching differently shaped falling blocks?",
                        List.of("Tetris", "Bejeweled", "Candy Crush", "Portal"),
                        "Tetris"),
                // 16
                new QuizQuestion("In 'World of Warcraft', what is the name of the world where most of the game takes place?",
                        List.of("Tamriel", "Azeroth", "Sanctuary", "Hyrule"),
                        "Azeroth"),
                // 17
                new QuizQuestion("Which game series features the assassin Ezio Auditore da Firenze?",
                        List.of("Assassin's Creed", "Hitman", "Thief", "Metal Gear Solid"),
                        "Assassin's Creed"),
                // 18
                new QuizQuestion("In 'Among Us', what is the role of the players trying to sabotage and kill crewmates?",
                        List.of("Crewmate", "Impostor", "Spy", "Saboteur"),
                        "Impostor"),
                // 19
                new QuizQuestion("Which game features a post-apocalyptic USA and a character named Joel escorting a girl named Ellie?",
                        List.of("The Last of Us", "Fallout 4", "Days Gone", "Metro Exodus"),
                        "The Last of Us"),
                // 20
                new QuizQuestion("Which handheld console was released by Nintendo after the Game Boy Advance?",
                        List.of("Nintendo DS", "PSP", "Game Gear", "Switch Lite"),
                        "Nintendo DS"),
                // 21
                new QuizQuestion("What is the name of the virtual world in 'The Elder Scrolls V: Skyrim'?",
                        List.of("Skellige", "Skyrim", "Sosaria", "Thedas"),
                        "Skyrim"),
                // 22
                new QuizQuestion("Which game is known for the phrase 'The cake is a lie'?",
                        List.of("Portal", "Bioshock", "Half-Life 2", "Duke Nukem"),
                        "Portal"),
                // 23
                new QuizQuestion("In 'Pokémon', what is the name of the electric-type mascot of the franchise?",
                        List.of("Charmander", "Bulbasaur", "Squirtle", "Pikachu"),
                        "Pikachu"),
                // 24
                new QuizQuestion("Which popular battle royale game was developed by Respawn Entertainment and set in the Titanfall universe?",
                        List.of("PUBG", "Apex Legends", "Warzone", "Valorant"),
                        "Apex Legends"),
                // 25
                new QuizQuestion("Which game franchise features the character Lara Croft?",
                        List.of("Tomb Raider", "Uncharted", "The Witcher", "Mass Effect"),
                        "Tomb Raider"),
                // 26
                new QuizQuestion("In 'FIFA' games, what does the abbreviation 'GK' stand for in team line-ups?",
                        List.of("Game Keeper", "Goalkeeper", "Ground Kicker", "Guard Kick"),
                        "Goalkeeper"),
                // 27
                new QuizQuestion("Which game introduced the horror character Pyramid Head?",
                        List.of("Silent Hill 2", "Resident Evil 2", "Dead Space", "Outlast"),
                        "Silent Hill 2"),
                // 28
                new QuizQuestion("Which game mode in 'Minecraft' focuses on building without health or hunger restrictions?",
                        List.of("Survival", "Creative", "Adventure", "Hardcore"),
                        "Creative"),
                // 29
                new QuizQuestion("Which competitive online game is played on a three-lane map and features champions with different roles and abilities?",
                        List.of("Overwatch", "Dota 2", "League of Legends", "Valorant"),
                        "League of Legends"),
                // 30
                new QuizQuestion("In which game series do you catch and battle creatures using 'Poké Balls'?",
                        List.of("Digimon", "Yu-Gi-Oh", "Pokémon", "Monster Hunter"),
                        "Pokémon")
        )));


// Litteratur – 30 questions, English (Literature & Books)
        categoryQuestions.put("Litteratur", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("Who wrote the play 'Romeo and Juliet'?",
                        List.of("William Shakespeare", "Charles Dickens", "Jane Austen", "Oscar Wilde"),
                        "William Shakespeare"),
                // 2
                new QuizQuestion("Which novel features the character Sherlock Holmes?",
                        List.of("Dracula", "The Hound of the Baskervilles", "Frankenstein", "Treasure Island"),
                        "The Hound of the Baskervilles"),
                // 3
                new QuizQuestion("Who wrote the fantasy series 'The Lord of the Rings'?",
                        List.of("C.S. Lewis", "J.R.R. Tolkien", "George R.R. Martin", "Terry Pratchett"),
                        "J.R.R. Tolkien"),
                // 4
                new QuizQuestion("Which author created the character Harry Potter?",
                        List.of("J.K. Rowling", "Philip Pullman", "Rick Riordan", "Suzanne Collins"),
                        "J.K. Rowling"),
                // 5
                new QuizQuestion("Which classic novel begins with the line 'Call me Ishmael'?",
                        List.of("Moby-Dick", "Great Expectations", "The Old Man and the Sea", "Heart of Darkness"),
                        "Moby-Dick"),
                // 6
                new QuizQuestion("Which Swedish author wrote the 'Millennium' trilogy that begins with 'The Girl with the Dragon Tattoo'?",
                        List.of("Camilla Läckberg", "Henning Mankell", "Stieg Larsson", "Lars Kepler"),
                        "Stieg Larsson"),
                // 7
                new QuizQuestion("Who wrote 'Pride and Prejudice'?",
                        List.of("Emily Brontë", "Jane Austen", "Charlotte Brontë", "Mary Shelley"),
                        "Jane Austen"),
                // 8
                new QuizQuestion("Which dystopian novel is set in Airstrip One and introduces Big Brother?",
                        List.of("Brave New World", "Fahrenheit 451", "1984", "The Handmaid's Tale"),
                        "1984"),
                // 9
                new QuizQuestion("Who wrote the epic fantasy series 'A Song of Ice and Fire'?",
                        List.of("George R.R. Martin", "Patrick Rothfuss", "Robert Jordan", "Brandon Sanderson"),
                        "George R.R. Martin"),
                // 10
                new QuizQuestion("Which novel by Mary Shelley is considered one of the first science fiction works?",
                        List.of("Dracula", "Frankenstein", "The Time Machine", "The Strange Case of Dr Jekyll and Mr Hyde"),
                        "Frankenstein"),
                // 11
                new QuizQuestion("Which Swedish author created the character Pippi Longstocking?",
                        List.of("Selma Lagerlöf", "Astrid Lindgren", "Karin Boye", "Moa Martinson"),
                        "Astrid Lindgren"),
                // 12
                new QuizQuestion("Who wrote 'The Catcher in the Rye'?",
                        List.of("J.D. Salinger", "F. Scott Fitzgerald", "Ernest Hemingway", "John Steinbeck"),
                        "J.D. Salinger"),
                // 13
                new QuizQuestion("Which novel by George Orwell features talking farm animals as an allegory for totalitarianism?",
                        List.of("Animal Farm", "1984", "Homage to Catalonia", "Lord of the Flies"),
                        "Animal Farm"),
                // 14
                new QuizQuestion("Who wrote 'The Chronicles of Narnia'?",
                        List.of("C.S. Lewis", "J.R.R. Tolkien", "Roald Dahl", "Lewis Carroll"),
                        "C.S. Lewis"),
                // 15
                new QuizQuestion("Which Russian author wrote 'Crime and Punishment'?",
                        List.of("Leo Tolstoy", "Fyodor Dostoevsky", "Anton Chekhov", "Nikolai Gogol"),
                        "Fyodor Dostoevsky"),
                // 16
                new QuizQuestion("Which Swedish author wrote 'The Wonderful Adventures of Nils'?",
                        List.of("Selma Lagerlöf", "August Strindberg", "Pär Lagerkvist", "Hjalmar Söderberg"),
                        "Selma Lagerlöf"),
                // 17
                new QuizQuestion("Which novel begins with the line 'It was the best of times, it was the worst of times'?",
                        List.of("Great Expectations", "A Tale of Two Cities", "Oliver Twist", "David Copperfield"),
                        "A Tale of Two Cities"),
                // 18
                new QuizQuestion("Who wrote 'The Hobbit'?",
                        List.of("J.R.R. Tolkien", "C.S. Lewis", "George R.R. Martin", "Ursula K. Le Guin"),
                        "J.R.R. Tolkien"),
                // 19
                new QuizQuestion("Which American author wrote 'To Kill a Mockingbird'?",
                        List.of("Harper Lee", "Toni Morrison", "Margaret Mitchell", "Sylvia Plath"),
                        "Harper Lee"),
                // 20
                new QuizQuestion("Which English playwright is known for works like 'Hamlet' and 'Macbeth'?",
                        List.of("Christopher Marlowe", "Ben Jonson", "William Shakespeare", "John Webster"),
                        "William Shakespeare"),
                // 21
                new QuizQuestion("Which novel by J.R.R. Tolkien directly follows 'The Fellowship of the Ring'?",
                        List.of("The Two Towers", "The Return of the King", "The Hobbit", "Silmarillion"),
                        "The Two Towers"),
                // 22
                new QuizQuestion("Which famous detective was created by Agatha Christie?",
                        List.of("Sherlock Holmes", "Hercule Poirot", "Philip Marlowe", "Sam Spade"),
                        "Hercule Poirot"),
                // 23
                new QuizQuestion("Which Swedish author wrote 'Kallocain', a classic dystopian novel?",
                        List.of("Karin Boye", "Pär Lagerkvist", "Vilhelm Moberg", "Harry Martinson"),
                        "Karin Boye"),
                // 24
                new QuizQuestion("Who wrote the science fiction novel 'Dune'?",
                        List.of("Isaac Asimov", "Arthur C. Clarke", "Frank Herbert", "Philip K. Dick"),
                        "Frank Herbert"),
                // 25
                new QuizQuestion("Which book series features a school called Camp Half-Blood?",
                        List.of("Percy Jackson & the Olympians", "The Hunger Games", "Divergent", "The Maze Runner"),
                        "Percy Jackson & the Olympians"),
                // 26
                new QuizQuestion("Which novel by F. Scott Fitzgerald is set during the Jazz Age and features Jay Gatsby?",
                        List.of("The Great Gatsby", "Tender Is the Night", "This Side of Paradise", "The Beautiful and Damned"),
                        "The Great Gatsby"),
                // 27
                new QuizQuestion("Who wrote the Swedish emigrant series starting with 'The Emigrants' ('Utvandrarna')?",
                        List.of("Vilhelm Moberg", "Per Anders Fogelström", "Pär Lagerkvist", "Jan Guillou"),
                        "Vilhelm Moberg"),
                // 28
                new QuizQuestion("Which epic poem begins with the line 'Sing, O goddess, the anger of Achilles'?",
                        List.of("The Iliad", "The Odyssey", "The Aeneid", "Beowulf"),
                        "The Iliad"),
                // 29
                new QuizQuestion("Who wrote 'The Picture of Dorian Gray'?",
                        List.of("Oscar Wilde", "Bram Stoker", "H.G. Wells", "Robert Louis Stevenson"),
                        "Oscar Wilde"),
                // 30
                new QuizQuestion("Which Swedish author wrote 'Doktor Glas'?",
                        List.of("Hjalmar Söderberg", "August Strindberg", "Pär Lagerkvist", "Moa Martinson"),
                        "Hjalmar Söderberg")
        )));


// Natur & Miljö – 30 questions, English (Nature & Environment)
        categoryQuestions.put("Natur & Miljö", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("What is the term for an area where all living things interact with each other and their non-living environment?",
                        List.of("Population", "Habitat", "Ecosystem", "Biome"),
                        "Ecosystem"),
                // 2
                new QuizQuestion("Which gas is the main cause of the enhanced greenhouse effect linked to climate change?",
                        List.of("Oxygen", "Nitrogen", "Carbon dioxide", "Helium"),
                        "Carbon dioxide"),
                // 3
                new QuizQuestion("What do we call animals that are active during the night and rest during the day?",
                        List.of("Diurnal", "Nocturnal", "Crepuscular", "Migratory"),
                        "Nocturnal"),
                // 4
                new QuizQuestion("Which biome is characterised by very low temperatures and permafrost?",
                        List.of("Desert", "Tundra", "Rainforest", "Savanna"),
                        "Tundra"),
                // 5
                new QuizQuestion("What term is used for a species that is at serious risk of extinction?",
                        List.of("Endemic", "Invasive", "Endangered", "Domesticated"),
                        "Endangered"),
                // 6
                new QuizQuestion("Which layer of the atmosphere contains most of the Earth's weather?",
                        List.of("Stratosphere", "Troposphere", "Mesosphere", "Thermosphere"),
                        "Troposphere"),
                // 7
                new QuizQuestion("What is the process by which water vapour turns into liquid water in the atmosphere?",
                        List.of("Evaporation", "Condensation", "Precipitation", "Sublimation"),
                        "Condensation"),
                // 8
                new QuizQuestion("What do you call the variety of different species and ecosystems on Earth?",
                        List.of("Biomass", "Biodiversity", "Biochemistry", "Biomechanics"),
                        "Biodiversity"),
                // 9
                new QuizQuestion("Which natural disaster is measured using the Richter scale?",
                        List.of("Hurricanes", "Earthquakes", "Floods", "Volcanoes"),
                        "Earthquakes"),
                // 10
                new QuizQuestion("Which gas do plants release as a by-product of photosynthesis?",
                        List.of("Carbon dioxide", "Nitrogen", "Oxygen", "Methane"),
                        "Oxygen"),
                // 11
                new QuizQuestion("What is the name of the large current in the Atlantic Ocean that helps keep northern Europe milder?",
                        List.of("Gulf Stream", "Kuroshio Current", "Humboldt Current", "Labrador Current"),
                        "Gulf Stream"),
                // 12
                new QuizQuestion("What do we call a species that is not native to an area and may cause harm to the ecosystem?",
                        List.of("Endemic species", "Invasive species", "Flagship species", "Keystone species"),
                        "Invasive species"),
                // 13
                new QuizQuestion("Which term describes the long-term average of weather conditions in a region?",
                        List.of("Weather", "Climate", "Forecast", "Front"),
                        "Climate"),
                // 14
                new QuizQuestion("Which gas forms the largest part of Earth's atmosphere?",
                        List.of("Oxygen", "Nitrogen", "Carbon dioxide", "Argon"),
                        "Nitrogen"),
                // 15
                new QuizQuestion("Which natural phenomenon is caused by the refraction and dispersion of light in water droplets?",
                        List.of("Aurora", "Rainbow", "Thunder", "Fog"),
                        "Rainbow"),
                // 16
                new QuizQuestion("What is the term for animals moving from one region to another, often seasonally?",
                        List.of("Hibernation", "Migration", "Territoriality", "Metamorphosis"),
                        "Migration"),
                // 17
                new QuizQuestion("Which of these is a renewable energy source?",
                        List.of("Coal", "Oil", "Wind", "Natural gas"),
                        "Wind"),
                // 18
                new QuizQuestion("What is the process by which fertile land becomes desert, typically due to drought and overuse?",
                        List.of("Deforestation", "Erosion", "Desertification", "Urbanisation"),
                        "Desertification"),
                // 19
                new QuizQuestion("Which biome is characterised by high rainfall and dense forest, often found near the equator?",
                        List.of("Tundra", "Savanna", "Temperate forest", "Tropical rainforest"),
                        "Tropical rainforest"),
                // 20
                new QuizQuestion("What is an apex predator?",
                        List.of("A plant at the top of the food chain", "An animal with no natural predators", "A predator that only eats plants", "A species that lives underground"),
                        "An animal with no natural predators"),
                // 21
                new QuizQuestion("Which process involves the wearing away of rocks and soil by wind, water or ice?",
                        List.of("Sedimentation", "Erosion", "Condensation", "Fusion"),
                        "Erosion"),
                // 22
                new QuizQuestion("Which large forest near the equator is often called 'the lungs of the Earth'?",
                        List.of("Congo Rainforest", "Amazon Rainforest", "Taiga", "Borneo Rainforest"),
                        "Amazon Rainforest"),
                // 23
                new QuizQuestion("What is the name of the boundary where two air masses meet?",
                        List.of("Fault line", "Front", "Fjord", "Fissure"),
                        "Front"),
                // 24
                new QuizQuestion("Which natural resource is most directly threatened by overfishing?",
                        List.of("Freshwater", "Fossil fuels", "Marine biodiversity", "Soil quality"),
                        "Marine biodiversity"),
                // 25
                new QuizQuestion("What is a keystone species?",
                        List.of("A species that is easy to hunt", "A species that has a disproportionately large effect on its ecosystem", "A species that lives only on mountains", "A species used for decoration"),
                        "A species that has a disproportionately large effect on its ecosystem"),
                // 26
                new QuizQuestion("Which term describes the warming of Earth's atmosphere due to trapped radiation?",
                        List.of("Greenhouse effect", "Ozone effect", "Reflection effect", "Ionosphere effect"),
                        "Greenhouse effect"),
                // 27
                new QuizQuestion("Which biome covers much of northern Canada, Scandinavia and Siberia and is dominated by coniferous forests?",
                        List.of("Savanna", "Taiga", "Tundra", "Steppe"),
                        "Taiga"),
                // 28
                new QuizQuestion("Which layer of Earth is directly beneath the crust?",
                        List.of("Inner core", "Outer core", "Mantle", "Lithosphere"),
                        "Mantle"),
                // 29
                new QuizQuestion("What term is used for the variety of life within a particular habitat or ecosystem?",
                        List.of("Biomass", "Biodiversity", "Biome", "Biotope"),
                        "Biodiversity"),
                // 30
                new QuizQuestion("Which international agreement aims to limit global warming to well below 2°C above pre-industrial levels?",
                        List.of("Kyoto Protocol", "Paris Agreement", "Montreal Protocol", "Rio Declaration"),
                        "Paris Agreement")
        )));


// Allmänbildning – 30 questions, English (General Knowledge)
        categoryQuestions.put("Allmänbildning", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("How many degrees are there in a full circle?",
                        List.of("90", "180", "270", "360"),
                        "360"),
                // 2
                new QuizQuestion("What is the chemical symbol for gold?",
                        List.of("Ag", "Au", "Gd", "Go"),
                        "Au"),
                // 3
                new QuizQuestion("In which city would you find the Eiffel Tower?",
                        List.of("London", "Paris", "Rome", "Berlin"),
                        "Paris"),
                // 4
                new QuizQuestion("Who painted the 'Mona Lisa'?",
                        List.of("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"),
                        "Leonardo da Vinci"),
                // 5
                new QuizQuestion("What is the capital city of Japan?",
                        List.of("Tokyo", "Kyoto", "Osaka", "Nagoya"),
                        "Tokyo"),
                // 6
                new QuizQuestion("Which language has the most native speakers worldwide?",
                        List.of("English", "Spanish", "Mandarin Chinese", "Hindi"),
                        "Mandarin Chinese"),
                // 7
                new QuizQuestion("How many continents are there on Earth (by most common definition)?",
                        List.of("5", "6", "7", "8"),
                        "7"),
                // 8
                new QuizQuestion("Which planet in our solar system is closest to the Sun?",
                        List.of("Mercury", "Venus", "Earth", "Mars"),
                        "Mercury"),
                // 9
                new QuizQuestion("In which sport can you score a 'hat-trick'?",
                        List.of("Football (soccer)", "Tennis", "Basketball", "Swimming"),
                        "Football (soccer)"),
                // 10
                new QuizQuestion("Which country is famous for the pyramids of Giza?",
                        List.of("Mexico", "Egypt", "Peru", "India"),
                        "Egypt"),
                // 11
                new QuizQuestion("What is H2O more commonly known as?",
                        List.of("Salt", "Oxygen", "Water", "Hydrogen"),
                        "Water"),
                // 12
                new QuizQuestion("Which famous scientist is associated with the law of universal gravitation?",
                        List.of("Albert Einstein", "Isaac Newton", "Niels Bohr", "Galileo Galilei"),
                        "Isaac Newton"),
                // 13
                new QuizQuestion("In which city are the headquarters of the United Nations located?",
                        List.of("Geneva", "New York City", "Paris", "Vienna"),
                        "New York City"),
                // 14
                new QuizQuestion("Which ocean lies between Africa and Australia?",
                        List.of("Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"),
                        "Indian Ocean"),
                // 15
                new QuizQuestion("Which instrument has black and white keys and is played with both hands?",
                        List.of("Violin", "Flute", "Piano", "Drum kit"),
                        "Piano"),
                // 16
                new QuizQuestion("Who was the first man to walk on the Moon?",
                        List.of("Yuri Gagarin", "Buzz Aldrin", "Neil Armstrong", "Michael Collins"),
                        "Neil Armstrong"),
                // 17
                new QuizQuestion("How many players are there on a standard football (soccer) team on the pitch?",
                        List.of("9", "10", "11", "12"),
                        "11"),
                // 18
                new QuizQuestion("Which metal is liquid at room temperature?",
                        List.of("Mercury", "Iron", "Copper", "Aluminium"),
                        "Mercury"),
                // 19
                new QuizQuestion("What is the largest internal organ in the human body?",
                        List.of("Heart", "Liver", "Lungs", "Kidneys"),
                        "Liver"),
                // 20
                new QuizQuestion("Which country is famous for the city Venice, built on canals?",
                        List.of("France", "Spain", "Italy", "Greece"),
                        "Italy"),
                // 21
                new QuizQuestion("Which currency is used in most countries of the European Union?",
                        List.of("Dollar", "Pound", "Euro", "Franc"),
                        "Euro"),
                // 22
                new QuizQuestion("What do bees collect from flowers to make honey?",
                        List.of("Water", "Nectar", "Leaves", "Seeds"),
                        "Nectar"),
                // 23
                new QuizQuestion("Which blood type is known as the universal donor?",
                        List.of("A", "B", "AB", "O negative"),
                        "O negative"),
                // 24
                new QuizQuestion("Which element do humans and animals need to breathe?",
                        List.of("Carbon dioxide", "Nitrogen", "Oxygen", "Helium"),
                        "Oxygen"),
                // 25
                new QuizQuestion("What is the largest planet in our solar system?",
                        List.of("Earth", "Saturn", "Jupiter", "Neptune"),
                        "Jupiter"),
                // 26
                new QuizQuestion("Which famous tower in Italy is known for leaning?",
                        List.of("Eiffel Tower", "Tower of Pisa", "Big Ben", "CN Tower"),
                        "Tower of Pisa"),
                // 27
                new QuizQuestion("Which country is home to the city of Sydney?",
                        List.of("New Zealand", "Australia", "Canada", "United States"),
                        "Australia"),
                // 28
                new QuizQuestion("What is the freezing point of water in degrees Celsius?",
                        List.of("-5", "0", "5", "10"),
                        "0"),
                // 29
                new QuizQuestion("Which device do you use to measure temperature?",
                        List.of("Barometer", "Thermometer", "Altimeter", "Hygrometer"),
                        "Thermometer"),
                // 30
                new QuizQuestion("Which famous scientist proposed the theory of evolution by natural selection?",
                        List.of("Gregor Mendel", "Charles Darwin", "Louis Pasteur", "Marie Curie"),
                        "Charles Darwin")
        )));

// Sport – 30 questions, English & a bit harder
        categoryQuestions.put("Sport", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("Which country has won the most FIFA World Cup titles in men's football?",
                        List.of("Brazil", "Germany", "Italy", "Argentina"), "Brazil"),
                // 2
                new QuizQuestion("In tennis, what is it called when a player wins all four Grand Slam tournaments in a single calendar year?",
                        List.of("Calendar Grand Slam", "Career Grand Slam", "Golden Slam", "Masters Sweep"), "Calendar Grand Slam"),
                // 3
                new QuizQuestion("In ice hockey, how many minutes does a standard major penalty last in the NHL?",
                        List.of("2 minutes", "4 minutes", "5 minutes", "10 minutes"), "5 minutes"),
                // 4
                new QuizQuestion("Which city hosted the first modern Olympic Games in 1896?",
                        List.of("Athens", "Paris", "London", "Rome"), "Athens"),
                // 5
                new QuizQuestion("In basketball, how many personal fouls does it take for a player to foul out in the NBA?",
                        List.of("4", "5", "6", "7"), "6"),
                // 6
                new QuizQuestion("Which Swedish football club has won the most Allsvenskan titles?",
                        List.of("Malmö FF", "IFK Göteborg", "AIK", "Djurgårdens IF"), "Malmö FF"),
                // 7
                new QuizQuestion("In Formula 1, what does the DRS system primarily reduce?",
                        List.of("Fuel consumption", "Tyre wear", "Aerodynamic drag", "Engine temperature"), "Aerodynamic drag"),
                // 8
                new QuizQuestion("Which country did sprinter Usain Bolt represent when setting the 100m world record?",
                        List.of("USA", "Jamaica", "Great Britain", "Canada"), "Jamaica"),
                // 9
                new QuizQuestion("In association football, how many players per team must remain on the pitch for the match to continue according to FIFA rules?",
                        List.of("7", "8", "9", "10"), "7"),
                // 10
                new QuizQuestion("Which team won the very first Premier League season (1992–1993)?",
                        List.of("Liverpool", "Manchester United", "Arsenal", "Leeds United"), "Manchester United"),
                // 11
                new QuizQuestion("In handball, how many steps may a player take without dribbling the ball?",
                        List.of("2 steps", "3 steps", "4 steps", "5 steps"), "3 steps"),
                // 12
                new QuizQuestion("In tennis, what is the score called when both players have won three points in a game?",
                        List.of("Deuce", "Advantage", "Tie", "Breakpoint"), "Deuce"),
                // 13
                new QuizQuestion("Which country hosted the first FIFA Women's World Cup in 1991?",
                        List.of("USA", "China", "Sweden", "Germany"), "China"),
                // 14
                new QuizQuestion("Which country is home to the 'All Blacks', one of the most famous rugby union teams?",
                        List.of("South Africa", "Australia", "England", "New Zealand"), "New Zealand"),
                // 15
                new QuizQuestion("In volleyball, how many points does a team normally need to win a set (excluding a tie-break set)?",
                        List.of("21 points", "23 points", "25 points", "30 points"), "25 points"),
                // 16
                new QuizQuestion("In which sport would you perform a 'double axel'?",
                        List.of("Gymnastics", "Diving", "Figure skating", "Snowboarding"), "Figure skating"),
                // 17
                new QuizQuestion("Which male tennis player has won the most Grand Slam singles titles?",
                        List.of("Roger Federer", "Rafael Nadal", "Novak Djokovic", "Pete Sampras"), "Novak Djokovic"),
                // 18
                new QuizQuestion("In cycling, what is the name of the polka-dot jersey in the Tour de France awarded for?",
                        List.of("Overall leader", "Best sprinter", "Best climber", "Best young rider"), "Best climber"),
                // 19
                new QuizQuestion("Which country hosted the 2016 Summer Olympic Games?",
                        List.of("China", "Brazil", "United Kingdom", "Japan"), "Brazil"),
                // 20
                new QuizQuestion("In American football, how many points is a touchdown worth?",
                        List.of("3 points", "6 points", "7 points", "8 points"), "6 points"),
                // 21
                new QuizQuestion("In snooker, how many points is the black ball worth?",
                        List.of("5", "6", "7", "8"), "7"),
                // 22
                new QuizQuestion("Which Swedish ice hockey player is nicknamed 'Foppa'?",
                        List.of("Mats Sundin", "Peter Forsberg", "Henrik Zetterberg", "Nicklas Lidström"), "Peter Forsberg"),
                // 23
                new QuizQuestion("In biathlon, which two sports are combined?",
                        List.of("Cross-country skiing and shooting", "Ski jumping and shooting", "Skating and shooting", "Cross-country and skating"), "Cross-country skiing and shooting"),
                // 24
                new QuizQuestion("In football, what is the minimum number of referees on the pitch in a standard professional match?",
                        List.of("1", "2", "3", "4"), "1"),
                // 25
                new QuizQuestion("Which country has won the most gold medals in total at the Summer Olympics?",
                        List.of("China", "Russia (incl. USSR)", "Germany", "United States"), "United States"),
                // 26
                new QuizQuestion("In ice hockey, how many players (including the goalkeeper) are on the ice per team in standard 5-on-5 play?",
                        List.of("4", "5", "6", "7"), "6"),
                // 27
                new QuizQuestion("In tennis, what surface is the French Open played on?",
                        List.of("Grass", "Clay", "Hard court", "Carpet"), "Clay"),
                // 28
                new QuizQuestion("Which Swedish cross-country skier won multiple Olympic gold medals and is closely associated with the 1980s?",
                        List.of("Gunde Svan", "Thomas Wassberg", "Per Elofsson", "Charlotte Kalla"), "Gunde Svan"),
                // 29
                new QuizQuestion("In basketball, how many points is a successful free throw worth?",
                        List.of("1", "2", "3", "4"), "1"),
                // 30
                new QuizQuestion("Which European club has won the most UEFA Champions League titles?",
                        List.of("FC Barcelona", "Bayern Munich", "AC Milan", "Real Madrid"), "Real Madrid")
        )));


// History – 30 questions, English
        categoryQuestions.put("Historia", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("In which year did the French Revolution begin?",
                        List.of("1776", "1789", "1815", "1848"), "1789"),
                // 2
                new QuizQuestion("Which treaty formally ended World War I between Germany and the Allied Powers?",
                        List.of("Treaty of Versailles", "Treaty of Paris", "Treaty of Vienna", "Treaty of Utrecht"), "Treaty of Versailles"),
                // 3
                new QuizQuestion("Which empire was ruled by Suleiman the Magnificent in the 16th century?",
                        List.of("The Ottoman Empire", "The Mughal Empire", "The Spanish Empire", "The Russian Empire"), "The Ottoman Empire"),
                // 4
                new QuizQuestion("Who was the first Chancellor of a unified German Empire in 1871?",
                        List.of("Otto von Bismarck", "Kaiser Wilhelm II", "Helmut Kohl", "Konrad Adenauer"), "Otto von Bismarck"),
                // 5
                new QuizQuestion("Which revolution is associated with the slogan 'Peace, Land, and Bread'?",
                        List.of("French Revolution", "Russian Revolution", "American Revolution", "Chinese Revolution"), "Russian Revolution"),
                // 6
                new QuizQuestion("Which ancient civilization built the city of Machu Picchu?",
                        List.of("The Aztecs", "The Incas", "The Mayans", "The Olmecs"), "The Incas"),
                // 7
                new QuizQuestion("Which English king is famous for having six wives?",
                        List.of("Henry V", "Henry VII", "Henry VIII", "James I"), "Henry VIII"),
                // 8
                new QuizQuestion("Which Scandinavian country was a major European power during the so-called 'Age of Greatness' (Stormaktstiden)?",
                        List.of("Norway", "Denmark", "Sweden", "Finland"), "Sweden"),
                // 9
                new QuizQuestion("Which city was divided into sectors by the Allies after World War II and later became a symbol of the Cold War?",
                        List.of("Vienna", "Berlin", "Prague", "Warsaw"), "Berlin"),
                // 10
                new QuizQuestion("Who was the leader of the Soviet Union during the Cuban Missile Crisis in 1962?",
                        List.of("Joseph Stalin", "Nikita Khrushchev", "Leonid Brezhnev", "Mikhail Gorbachev"), "Nikita Khrushchev"),
                // 11
                new QuizQuestion("Which famous trade route connected China with the Mediterranean world for centuries?",
                        List.of("Amber Road", "Silk Road", "Spice Route", "Royal Road"), "Silk Road"),
                // 12
                new QuizQuestion("Which event is commonly considered the immediate trigger of World War I?",
                        List.of("The invasion of Poland", "The bombing of Pearl Harbor", "The assassination of Archduke Franz Ferdinand", "The sinking of the Lusitania"), "The assassination of Archduke Franz Ferdinand"),
                // 13
                new QuizQuestion("Who was the first female Prime Minister of the United Kingdom?",
                        List.of("Theresa May", "Margaret Thatcher", "Indira Gandhi", "Golda Meir"), "Margaret Thatcher"),
                // 14
                new QuizQuestion("Which ancient city was destroyed by the eruption of Mount Vesuvius in 79 AD?",
                        List.of("Pompeii", "Carthage", "Athens", "Troy"), "Pompeii"),
                // 15
                new QuizQuestion("Which explorer's expedition completed the first recorded circumnavigation of the Earth?",
                        List.of("Christopher Columbus", "Ferdinand Magellan", "Vasco da Gama", "James Cook"), "Ferdinand Magellan"),
                // 16
                new QuizQuestion("In which year did the Berlin Wall fall?",
                        List.of("1969", "1979", "1989", "1999"), "1989"),
                // 17
                new QuizQuestion("Which war is sometimes called 'The Great War'?",
                        List.of("World War I", "World War II", "The Crimean War", "The Cold War"), "World War I"),
                // 18
                new QuizQuestion("Which country was led by Nelson Mandela after the end of apartheid?",
                        List.of("Nigeria", "Kenya", "South Africa", "Egypt"), "South Africa"),
                // 19
                new QuizQuestion("Which document, signed in 1215, limited the power of the English king and is often cited as an early step towards constitutional government?",
                        List.of("The Bill of Rights", "The Magna Carta", "The Petition of Right", "The Act of Union"), "The Magna Carta"),
                // 20
                new QuizQuestion("Which ancient people built a vast empire with their capital at Persepolis?",
                        List.of("Romans", "Persians", "Greeks", "Phoenicians"), "Persians"),
                // 21
                new QuizQuestion("Which city was the capital of the Byzantine Empire?",
                        List.of("Rome", "Athens", "Constantinople", "Alexandria"), "Constantinople"),
                // 22
                new QuizQuestion("Which European country colonised large parts of India until the mid-20th century?",
                        List.of("France", "Great Britain", "Spain", "Portugal"), "Great Britain"),
                // 23
                new QuizQuestion("Which conflict between the USA and the USSR after World War II never turned into direct large-scale war, but involved nuclear tension and proxy wars?",
                        List.of("The Hot War", "The Cold War", "The Hundred Years' War", "The Gulf War"), "The Cold War"),
                // 24
                new QuizQuestion("Who was the first President of the United States?",
                        List.of("Abraham Lincoln", "George Washington", "Thomas Jefferson", "John Adams"), "George Washington"),
                // 25
                new QuizQuestion("Which South American liberator is known for helping free several countries from Spanish rule, including Venezuela and Bolivia?",
                        List.of("Che Guevara", "Simón Bolívar", "Fidel Castro", "José de San Martín"), "Simón Bolívar"),
                // 26
                new QuizQuestion("Which German city hosted the infamous Nazi Party rallies and later the war crime trials after World War II?",
                        List.of("Berlin", "Hamburg", "Nuremberg", "Munich"), "Nuremberg"),
                // 27
                new QuizQuestion("In which year did Sweden join the European Union?",
                        List.of("1986", "1995", "2001", "2010"), "1995"),
                // 28
                new QuizQuestion("Which religion was founded by Siddhartha Gautama?",
                        List.of("Christianity", "Islam", "Buddhism", "Judaism"), "Buddhism"),
                // 29
                new QuizQuestion("Which political ideology is associated with 'The Communist Manifesto', written by Karl Marx and Friedrich Engels?",
                        List.of("Liberalism", "Communism", "Fascism", "Conservatism"), "Communism"),
                // 30
                new QuizQuestion("Which city was divided into East and West by a wall from 1961 until 1989?",
                        List.of("Vienna", "Berlin", "Prague", "Budapest"), "Berlin")
        )));


// Film & TV – 30 questions, English
        categoryQuestions.put("Film & TV", new ArrayList<>(List.of(
                // 1
                new QuizQuestion("In the film 'The Lord of the Rings: The Fellowship of the Ring', what is the name of the Elven realm ruled by Galadriel?",
                        List.of("Rivendell", "Lothlórien", "Mirkwood", "Gondolin"), "Lothlórien"),
                // 2
                new QuizQuestion("Which director is known for the films 'Inception', 'Interstellar' and 'Tenet'?",
                        List.of("Christopher Nolan", "James Cameron", "Ridley Scott", "Peter Jackson"), "Christopher Nolan"),
                // 3
                new QuizQuestion("In 'Breaking Bad', what is Walter White's street name?",
                        List.of("Heisenberg", "Gus", "Jesse", "Saul"), "Heisenberg"),
                // 4
                new QuizQuestion("Which 1982 science-fiction film directed by Ridley Scott is based on Philip K. Dick's novel 'Do Androids Dream of Electric Sheep?'",
                        List.of("Blade Runner", "Alien", "Total Recall", "The Thing"), "Blade Runner"),
                // 5
                new QuizQuestion("In the Marvel Cinematic Universe, what is the name of Thor's axe introduced in 'Avengers: Infinity War'?",
                        List.of("Gungnir", "Stormbreaker", "Jarnbjorn", "Thunderstrike"), "Stormbreaker"),
                // 6
                new QuizQuestion("Which TV series is set in the fictional continents of Westeros and Essos?",
                        List.of("The Witcher", "Game of Thrones", "Shadow and Bone", "The Wheel of Time"), "Game of Thrones"),
                // 7
                new QuizQuestion("In 'The Matrix', what is the name of the last human city?",
                        List.of("Zion", "Babylon", "Arcadia", "Eden"), "Zion"),
                // 8
                new QuizQuestion("Which Swedish actor plays the scientist Erik Selvig in several Marvel films?",
                        List.of("Stellan Skarsgård", "Peter Stormare", "Alexander Skarsgård", "Joel Kinnaman"), "Stellan Skarsgård"),
                // 9
                new QuizQuestion("In 'Star Wars', what is the name of the order of warriors who follow the dark side of the Force?",
                        List.of("Jedi", "Sith", "Mandalorians", "Clone Troopers"), "Sith"),
                // 10
                new QuizQuestion("Which animated film features the quote 'To infinity and beyond!'?",
                        List.of("Toy Story", "Finding Nemo", "The Incredibles", "Monsters, Inc."), "Toy Story"),
                // 11
                new QuizQuestion("In the TV series 'Friends', what is the name of Ross and Monica's childhood dog?",
                        List.of("Chi-Chi", "Marcel", "Clunkers", "LaPooh"), "Chi-Chi"),
                // 12
                new QuizQuestion("Which Swedish comedy-drama TV series features the characters Fredde and Mickan living in a wealthy suburb?",
                        List.of("Solsidan", "Bonusfamiljen", "Kvarteret Skatan", "Ack Värmland"), "Solsidan"),
                // 13
                new QuizQuestion("Which director is known as the 'Master of Suspense' and directed films like 'Psycho' and 'Vertigo'?",
                        List.of("Alfred Hitchcock", "Stanley Kubrick", "Billy Wilder", "Francis Ford Coppola"), "Alfred Hitchcock"),
                // 14
                new QuizQuestion("In 'Harry Potter', what is the name of the prison guarded by Dementors?",
                        List.of("Azkaban", "Nurmengard", "Gringotts", "Durmstrang"), "Azkaban"),
                // 15
                new QuizQuestion("Which 1994 film starring Tim Robbins and Morgan Freeman is based on a Stephen King novella about a prison escape?",
                        List.of("The Shawshank Redemption", "The Green Mile", "Misery", "Stand by Me"), "The Shawshank Redemption"),
                // 16
                new QuizQuestion("In the film 'Titanic', what is the name of the ship that eventually rescues the survivors?",
                        List.of("Carpathia", "Britannic", "Olympic", "Lusitania"), "Carpathia"),
                // 17
                new QuizQuestion("Which TV series follows a high school chemistry teacher who turns to manufacturing drugs?",
                        List.of("Breaking Bad", "Ozark", "Narcos", "The Wire"), "Breaking Bad"),
                // 18
                new QuizQuestion("In 'Star Wars', what is the name of Han Solo's ship?",
                        List.of("Millennium Falcon", "X-Wing", "Star Destroyer", "Slave I"), "Millennium Falcon"),
                // 19
                new QuizQuestion("Which animated film tells the story of a lion cub named Simba?",
                        List.of("The Lion King", "Madagascar", "Kung Fu Panda", "Ice Age"), "The Lion King"),
                // 20
                new QuizQuestion("In which film series does the character Jack Sparrow appear?",
                        List.of("Pirates of the Caribbean", "Indiana Jones", "The Mummy", "National Treasure"), "Pirates of the Caribbean"),
                // 21
                new QuizQuestion("Which TV series is set in a fictional paper company called Dunder Mifflin?",
                        List.of("The Office (US)", "Parks and Recreation", "Brooklyn Nine-Nine", "Scrubs"), "The Office (US)"),
                // 22
                new QuizQuestion("In 'Stranger Things', what is the name of the girl with telekinetic powers?",
                        List.of("Nancy", "Max", "Eleven", "Robin"), "Eleven"),
                // 23
                new QuizQuestion("Which film franchise features a secret spy organisation called MI6 and a character with the code number 007?",
                        List.of("Mission: Impossible", "Jason Bourne", "James Bond", "Kingsman"), "James Bond"),
                // 24
                new QuizQuestion("Which Swedish crime series is based on novels by Henning Mankell and features a detective in Ystad?",
                        List.of("Beck", "Wallander", "Johan Falk", "Bron/Broen"), "Wallander"),
                // 25
                new QuizQuestion("Which animated film features a clownfish searching for his son Nemo?",
                        List.of("Finding Dory", "Finding Nemo", "Shark Tale", "The Little Mermaid"), "Finding Nemo"),
                // 26
                new QuizQuestion("In 'Game of Thrones', what is the family motto of House Stark?",
                        List.of("'Winter is Coming'", "'Hear Me Roar!'", "'Fire and Blood'", "'We Do Not Sow'"), "Winter is Coming"),
                // 27
                new QuizQuestion("Which director is responsible for the 'Star Wars' original trilogy and created the character Luke Skywalker?",
                        List.of("George Lucas", "Steven Spielberg", "J.J. Abrams", "Ron Howard"), "George Lucas"),
                // 28
                new QuizQuestion("In 'The Simpsons', what is the name of the town where the family lives?",
                        List.of("Quahog", "South Park", "Springfield", "Arlen"), "Springfield"),
                // 29
                new QuizQuestion("Which film contains the quote 'May the Force be with you'?",
                        List.of("Star Wars", "Star Trek", "Avatar", "Blade Runner"), "Star Wars"),
                // 30
                new QuizQuestion("Which TV show follows a group of survivors in a world overrun by zombies, primarily focusing on Rick Grimes?",
                        List.of("The Walking Dead", "Z Nation", "Fear the Walking Dead", "iZombie"), "The Walking Dead")
        )));


// Geography – 30 questions, English
        categoryQuestions.put("Geografi", new ArrayList<>(List.of(
                new QuizQuestion("What is the largest country in the world by land area?",
                        List.of("Canada", "China", "Russia", "United States"), "Russia"),
                new QuizQuestion("Which river is the longest in the world by most modern measurements?",
                        List.of("Nile", "Amazon", "Yangtze", "Mississippi"), "Nile"),
                new QuizQuestion("What is the capital city of Sweden?",
                        List.of("Gothenburg", "Malmö", "Stockholm", "Uppsala"), "Stockholm"),
                new QuizQuestion("On which continent is the country of Brazil located?",
                        List.of("North America", "Europe", "Asia", "South America"), "South America"),
                new QuizQuestion("Which sea lies between northern Africa and southern Europe?",
                        List.of("Atlantic Ocean", "Indian Ocean", "Mediterranean Sea", "Red Sea"), "Mediterranean Sea"),
                new QuizQuestion("What is the highest mountain in the world above sea level?",
                        List.of("K2", "Kilimanjaro", "Mount Everest", "Mont Blanc"), "Mount Everest"),
                new QuizQuestion("Which country has cities such as Sydney and Melbourne?",
                        List.of("New Zealand", "Australia", "Canada", "South Africa"), "Australia"),
                new QuizQuestion("What is the capital city of Norway?",
                        List.of("Oslo", "Bergen", "Trondheim", "Stavanger"), "Oslo"),
                new QuizQuestion("Which European country is shaped roughly like a boot?",
                        List.of("Spain", "Portugal", "Italy", "Greece"), "Italy"),
                new QuizQuestion("Which river flows through Egypt and is often called 'the lifeline of Egypt'?",
                        List.of("Amazon", "Mississippi", "Nile", "Danube"), "Nile"),
                new QuizQuestion("Which of these countries is NOT part of the Nordic region?",
                        List.of("Denmark", "Finland", "Germany", "Iceland"), "Germany"),
                new QuizQuestion("What is the largest continent by land area?",
                        List.of("Europe", "Asia", "Africa", "North America"), "Asia"),
                new QuizQuestion("Which country is known as 'The Land of the Rising Sun'?",
                        List.of("China", "Japan", "Thailand", "Vietnam"), "Japan"),
                new QuizQuestion("What is the capital of Finland?",
                        List.of("Helsinki", "Turku", "Tampere", "Vaasa"), "Helsinki"),
                new QuizQuestion("Which large island belongs to Denmark?",
                        List.of("Iceland", "Greenland", "Gotland", "Sicily"), "Greenland"),
                new QuizQuestion("Which country does NOT share a land border with Sweden?",
                        List.of("Norway", "Finland", "Denmark", "Russia"), "Denmark"),
                new QuizQuestion("Which sea lies to the east of Sweden?",
                        List.of("North Sea", "Mediterranean Sea", "Baltic Sea", "Black Sea"), "Baltic Sea"),
                new QuizQuestion("Which continent is entirely south of the equator?",
                        List.of("Africa", "Antarctica", "Australia", "South America"), "Antarctica"),
                new QuizQuestion("What is the capital city of Spain?",
                        List.of("Barcelona", "Madrid", "Valencia", "Seville"), "Madrid"),
                new QuizQuestion("Which country currently has the largest population in the world?",
                        List.of("United States", "India", "China", "Indonesia"), "India"),
                new QuizQuestion("What is the name of the large desert covering much of northern Africa?",
                        List.of("Gobi Desert", "Sahara Desert", "Kalahari Desert", "Atacama Desert"), "Sahara Desert"),
                new QuizQuestion("Which city is known as 'The Big Apple'?",
                        List.of("Los Angeles", "Chicago", "New York City", "San Francisco"), "New York City"),
                new QuizQuestion("Which capital city is geographically closest to Stockholm?",
                        List.of("Oslo", "Copenhagen", "Helsinki", "Reykjavik"), "Helsinki"),
                new QuizQuestion("Which country is famous for fjords and oil in the North Sea?",
                        List.of("Denmark", "Norway", "Netherlands", "Ireland"), "Norway"),
                new QuizQuestion("Which large body of water lies between Sweden and Finland?",
                        List.of("Lake Vänern", "Lake Vättern", "Gulf of Bothnia", "North Sea"), "Gulf of Bothnia"),
                new QuizQuestion("What is the capital city of Germany?",
                        List.of("Hamburg", "Munich", "Berlin", "Frankfurt"), "Berlin"),
                new QuizQuestion("Which country lies on both the European and Asian continents?",
                        List.of("Spain", "Turkey", "Poland", "Greece"), "Turkey"),
                new QuizQuestion("Which part of the United Kingdom contains the cities Edinburgh and Glasgow?",
                        List.of("England", "Wales", "Scotland", "Northern Ireland"), "Scotland"),
                new QuizQuestion("What is the largest ocean on Earth?",
                        List.of("Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"), "Pacific Ocean")
        )));


// Music – 30 questions, English
        categoryQuestions.put("Musik", new ArrayList<>(List.of(
                new QuizQuestion("Which Swedish band won the Eurovision Song Contest in 1974 with the song 'Waterloo'?",
                        List.of("ABBA", "Roxette", "Ace of Base", "The Ark"), "ABBA"),
                new QuizQuestion("Which instrument typically has 88 keys?",
                        List.of("Piano", "Guitar", "Organ", "Saxophone"), "Piano"),
                new QuizQuestion("Who is known as 'The King of Pop'?",
                        List.of("Elvis Presley", "Michael Jackson", "Prince", "David Bowie"), "Michael Jackson"),
                new QuizQuestion("Which Swedish band is known for the song 'Dancing Queen'?",
                        List.of("ABBA", "Gyllene Tider", "Kent", "Europe"), "ABBA"),
                new QuizQuestion("Which instrument uses strings and is often played with a plectrum?",
                        List.of("Flute", "Drums", "Guitar", "Trumpet"), "Guitar"),
                new QuizQuestion("Which music genre is associated with bands like Metallica and Iron Maiden?",
                        List.of("Pop", "Jazz", "Heavy metal", "Country"), "Heavy metal"),
                new QuizQuestion("Which Swedish singer performed the song 'Fångad av en stormvind'?",
                        List.of("Carola", "Lena Philipsson", "Sanna Nielsen", "Charlotte Perrelli"), "Carola"),
                new QuizQuestion("Which genre is most associated with the artist Eminem?",
                        List.of("Rock", "Rap", "Country", "Classical"), "Rap"),
                new QuizQuestion("Which country did the band The Beatles come from?",
                        List.of("United States", "Australia", "United Kingdom", "Canada"), "United Kingdom"),
                new QuizQuestion("What do you call the person who leads an orchestra?",
                        List.of("Conductor", "Soloist", "Producer", "Composer"), "Conductor"),
                new QuizQuestion("Which instrument is strongly associated with jazz?",
                        List.of("Harp", "Saxophone", "Banjo", "Tuba"), "Saxophone"),
                new QuizQuestion("Which Swedish DJ produced the hit 'Wake Me Up'?",
                        List.of("Avicii", "Swedish House Mafia", "Basshunter", "Alesso"), "Avicii"),
                new QuizQuestion("Which musical period is composer Ludwig van Beethoven most associated with?",
                        List.of("Baroque", "Classical/Romantic", "Modern", "Renaissance"), "Classical/Romantic"),
                new QuizQuestion("What is the name of Sweden's annual music competition that sends a winner to Eurovision?",
                        List.of("Idol", "Melodifestivalen", "Talang", "Så ska det låta"), "Melodifestivalen"),
                new QuizQuestion("Which of these is a drum instrument?",
                        List.of("Bass drum", "Violin", "Flute", "Cello"), "Bass drum"),
                new QuizQuestion("Who sings the hit song 'Shape of You'?",
                        List.of("Ed Sheeran", "Justin Bieber", "Shawn Mendes", "Sam Smith"), "Ed Sheeran"),
                new QuizQuestion("Which of these is a famous Swedish hard rock band?",
                        List.of("Kent", "Europe", "First Aid Kit", "The Cardigans"), "Europe"),
                new QuizQuestion("Which instrument is commonly paired with piano in classical duos?",
                        List.of("Drums", "Violin", "Trumpet", "Clarinet"), "Violin"),
                new QuizQuestion("Which music style originally developed in Jamaica?",
                        List.of("Reggae", "Blues", "Country", "Funk"), "Reggae"),
                new QuizQuestion("Which Swedish duo released the hit 'The Look'?",
                        List.of("ABBA", "Roxette", "Ace of Base", "A-Teens"), "Roxette"),
                new QuizQuestion("What is the highest standard female vocal range called?",
                        List.of("Alto", "Mezzo-soprano", "Soprano", "Baritone"), "Soprano"),
                new QuizQuestion("Which of these is a percussion instrument?",
                        List.of("Cello", "Oboe", "Piano", "Drum kit"), "Drum kit"),
                new QuizQuestion("Who sings 'Rolling in the Deep'?",
                        List.of("Adele", "Taylor Swift", "Rihanna", "Beyoncé"), "Adele"),
                new QuizQuestion("Which musical is set in revolutionary France and features the character Jean Valjean?",
                        List.of("Les Misérables", "Cats", "The Phantom of the Opera", "Mamma Mia!"), "Les Misérables"),
                new QuizQuestion("Which band released the album 'Nevermind' in 1991?",
                        List.of("Nirvana", "Pearl Jam", "Green Day", "Red Hot Chili Peppers"), "Nirvana"),
                new QuizQuestion("Which genre is legendary singer Dolly Parton associated with?",
                        List.of("Country", "Hip hop", "House", "Metal"), "Country"),
                new QuizQuestion("Which former Swedish festival in Norrköping was once one of the biggest rock festivals?",
                        List.of("Sweden Rock", "Bråvalla", "Way Out West", "Hultsfred"), "Bråvalla"),
                new QuizQuestion("Which bowed string instrument is common in folk music?",
                        List.of("Violin", "Clarinet", "Saxophone", "Trombone"), "Violin"),
                new QuizQuestion("Which Swedish singer had a big hit with the song 'Glorious'?",
                        List.of("Army of Lovers", "Style", "Andreas Johnson", "Måns Zelmerlöw"), "Andreas Johnson")
        )));


// Science – 30 questions, English
        categoryQuestions.put("Vetenskap", new ArrayList<>(List.of(
                new QuizQuestion("Which chemical element has the symbol O?",
                        List.of("Gold", "Silver", "Oxygen", "Hydrogen"), "Oxygen"),
                new QuizQuestion("How many planets are currently recognised in our solar system?",
                        List.of("7", "8", "9", "10"), "8"),
                new QuizQuestion("Which scientist developed the theory of general relativity?",
                        List.of("Isaac Newton", "Albert Einstein", "Nikola Tesla", "Stephen Hawking"), "Albert Einstein"),
                new QuizQuestion("Which planet is known as 'the Red Planet'?",
                        List.of("Venus", "Mars", "Jupiter", "Mercury"), "Mars"),
                new QuizQuestion("Which organ pumps blood around the human body?",
                        List.of("Lungs", "Stomach", "Heart", "Liver"), "Heart"),
                new QuizQuestion("Which unit is used to measure electric current?",
                        List.of("Volt", "Ampere", "Watt", "Ohm"), "Ampere"),
                new QuizQuestion("Which organs are primarily responsible for gas exchange in the human body?",
                        List.of("Kidneys", "Lungs", "Liver", "Pancreas"), "Lungs"),
                new QuizQuestion("What is the force that pulls objects towards the centre of the Earth called?",
                        List.of("Pressure", "Magnetism", "Gravity", "Friction"), "Gravity"),
                new QuizQuestion("What is the smallest unit of a chemical element that retains its properties?",
                        List.of("Molecule", "Atom", "Cell", "Nucleus"), "Atom"),
                new QuizQuestion("What is the process called in which plants convert light energy into chemical energy?",
                        List.of("Fermentation", "Photosynthesis", "Glycolysis", "Respiration"), "Photosynthesis"),
                new QuizQuestion("Which metal is a key component of hemoglobin in red blood cells?",
                        List.of("Copper", "Zinc", "Iron", "Silver"), "Iron"),
                new QuizQuestion("Which is the largest planet in our solar system?",
                        List.of("Earth", "Saturn", "Jupiter", "Neptune"), "Jupiter"),
                new QuizQuestion("Which branch of science is the study of living organisms?",
                        List.of("Physics", "Chemistry", "Biology", "Geology"), "Biology"),
                new QuizQuestion("At what temperature in degrees Celsius does pure water freeze at standard pressure?",
                        List.of("-10", "0", "5", "10"), "0"),
                new QuizQuestion("Which part of the human eye controls how much light enters?",
                        List.of("Lens", "Retina", "Pupil", "Cornea"), "Pupil"),
                new QuizQuestion("What is the basic structural and functional unit of all living organisms?",
                        List.of("Organ", "Tissue", "Cell", "System"), "Cell"),
                new QuizQuestion("Which gas do plants primarily absorb from the atmosphere during photosynthesis?",
                        List.of("Oxygen", "Nitrogen", "Carbon dioxide", "Hydrogen"), "Carbon dioxide"),
                new QuizQuestion("Which group contains helium, neon and argon?",
                        List.of("Alkali metals", "Halogens", "Transition metals", "Noble gases"), "Noble gases"),
                new QuizQuestion("Which branch of science studies the Earth's physical structure and rocks?",
                        List.of("Astronomy", "Geology", "Ecology", "Meteorology"), "Geology"),
                new QuizQuestion("Which blood cells are mainly responsible for carrying oxygen?",
                        List.of("White blood cells", "Red blood cells", "Platelets", "Stem cells"), "Red blood cells"),
                new QuizQuestion("What is a mixture called where one substance is completely dissolved in another, like salt in water?",
                        List.of("Solution", "Suspension", "Emulsion", "Colloid"), "Solution"),
                new QuizQuestion("What is the name of our galaxy?",
                        List.of("Andromeda", "Milky Way", "Orion", "Sombrero"), "Milky Way"),
                new QuizQuestion("Which of the following is a fossil fuel?",
                        List.of("Solar energy", "Wind power", "Coal", "Hydropower"), "Coal"),
                new QuizQuestion("Which of these planets has the most prominent ring system visible from Earth with a small telescope?",
                        List.of("Mars", "Jupiter", "Saturn", "Uranus"), "Saturn"),
                new QuizQuestion("What is the process called when a liquid turns into gas at its surface below boiling point?",
                        List.of("Freezing", "Melting", "Evaporation", "Condensation"), "Evaporation"),
                new QuizQuestion("Which part of the cell contains the genetic material DNA?",
                        List.of("Cytoplasm", "Nucleus", "Cell membrane", "Ribosome"), "Nucleus"),
                new QuizQuestion("What is the chemical formula for water?",
                        List.of("CO2", "H2O", "O2", "H2"), "H2O"),
                new QuizQuestion("Which scale is commonly used to measure the magnitude of earthquakes?",
                        List.of("Beaufort scale", "Richter scale", "Decibel scale", "Mohs scale"), "Richter scale"),
                new QuizQuestion("What is the term for animals that maintain a constant body temperature, such as mammals and birds?",
                        List.of("Cold-blooded", "Warm-blooded", "Invertebrates", "Amphibians"), "Warm-blooded")
        )));


// Animals – 30 questions, English
        categoryQuestions.put("Djur", new ArrayList<>(List.of(
                new QuizQuestion("What is the largest animal on Earth?",
                        List.of("African elephant", "Blue whale", "Giraffe", "Hippopotamus"), "Blue whale"),
                new QuizQuestion("Which animal is often called 'the king of the jungle'?",
                        List.of("Tiger", "Lion", "Gorilla", "Leopard"), "Lion"),
                new QuizQuestion("Which mammal is capable of true flight?",
                        List.of("Flying squirrel", "Bat", "Chicken", "Ostrich"), "Bat"),
                new QuizQuestion("What is a young cow called?",
                        List.of("Calf", "Lamb", "Kid", "Foal"), "Calf"),
                new QuizQuestion("Which animal is famous for its black and white stripes?",
                        List.of("Giraffe", "Zebra", "Leopard", "Tiger"), "Zebra"),
                new QuizQuestion("Which animal hibernates during the winter in many northern regions?",
                        List.of("Horse", "Cow", "Bear", "Fox"), "Bear"),
                new QuizQuestion("Which mammal spends most of its life in water and has webbed feet and a flat tail, and builds dams?",
                        List.of("Otter", "Beaver", "Seal", "Walrus"), "Beaver"),
                new QuizQuestion("Which bird cannot fly but is very fast on land?",
                        List.of("Eagle", "Ostrich", "Swan", "Seagull"), "Ostrich"),
                new QuizQuestion("Which insect is responsible for producing honey?",
                        List.of("Wasp", "Bumblebee", "Honeybee", "Mosquito"), "Honeybee"),
                new QuizQuestion("Which animal has a long trunk and large ears?",
                        List.of("Rhinoceros", "Hippopotamus", "Elephant", "Giraffe"), "Elephant"),
                new QuizQuestion("Which predator lives primarily in the Arctic and is white to blend with the snow?",
                        List.of("Lion", "Snow leopard", "Polar bear", "Hyena"), "Polar bear"),
                new QuizQuestion("Which reptile can change colour to camouflage itself?",
                        List.of("Crocodile", "Snake", "Chameleon", "Turtle"), "Chameleon"),
                new QuizQuestion("What do we call a group of wolves?",
                        List.of("Herd", "Flock", "Pack", "Swarm"), "Pack"),
                new QuizQuestion("Which animal is known for hanging upside down in trees and moving very slowly?",
                        List.of("Monkey", "Sloth", "Panther", "Leopard"), "Sloth"),
                new QuizQuestion("Which bird of prey is known for its excellent eyesight and powerful talons?",
                        List.of("Goose", "Eagle", "Hen", "Duck"), "Eagle"),
                new QuizQuestion("Which animal carries its young in a pouch, typical of marsupials?",
                        List.of("Tiger", "Kangaroo", "Bear", "Dolphin"), "Kangaroo"),
                new QuizQuestion("Which marine mammal uses echolocation and is known for its intelligence and playful behaviour?",
                        List.of("Shark", "Dolphin", "Whale shark", "Seal"), "Dolphin"),
                new QuizQuestion("Which large cat species is generally considered the biggest in terms of body size?",
                        List.of("Leopard", "Cheetah", "Lion", "Tiger"), "Tiger"),
                new QuizQuestion("Which animal is a rodent?",
                        List.of("Cat", "Dog", "Rabbit", "Sheep"), "Rabbit"),
                new QuizQuestion("Which animal has scales and breathes through gills?",
                        List.of("Bird", "Fish", "Frog", "Snake"), "Fish"),
                new QuizQuestion("Which animal has eight legs and spins webs?",
                        List.of("Insect", "Spider", "Worm", "Crab"), "Spider"),
                new QuizQuestion("Which animal is known for its black and white fur and lives mainly in China, eating bamboo?",
                        List.of("Panda", "Koala", "Raccoon", "Skunk"), "Panda"),
                new QuizQuestion("Which marine creature has eight arms and can squirt ink?",
                        List.of("Squid", "Octopus", "Jellyfish", "Starfish"), "Octopus"),
                new QuizQuestion("Which bird is often associated with delivering babies in folklore?",
                        List.of("Stork", "Crow", "Pigeon", "Owl"), "Stork"),
                new QuizQuestion("Which animal is the fastest land animal?",
                        List.of("Cheetah", "Pronghorn", "Horse", "Greyhound"), "Cheetah"),
                new QuizQuestion("Which animal is known for living in colonies and building complex anthills?",
                        List.of("Bees", "Ants", "Termites", "Wasps"), "Ants"),
                new QuizQuestion("Which large marine animal is actually a mammal despite its name suggesting otherwise?",
                        List.of("Whale shark", "Great white shark", "Hammerhead shark", "Manta ray"), "Whale shark"),
                new QuizQuestion("Which animal has a hard shell and can retract its head and legs into it for protection?",
                        List.of("Turtle", "Crocodile", "Lizard", "Frog"), "Turtle"),
                new QuizQuestion("Which bird is known for its ability to mimic human speech?",
                        List.of("Canary", "Eagle", "Parrot", "Owl"), "Parrot")
        )));


// Food & Drink – 30 questions, English
        categoryQuestions.put("Mat & Dryck", new ArrayList<>(List.of(
                new QuizQuestion("From which country did pizza originally become popular in its modern form?",
                        List.of("United States", "Spain", "Italy", "Portugal"), "Italy"),
                new QuizQuestion("What is the main ingredient in guacamole?",
                        List.of("Tomato", "Avocado", "Corn", "Red pepper"), "Avocado"),
                new QuizQuestion("Which country is especially known for sushi?",
                        List.of("China", "Japan", "Thailand", "Vietnam"), "Japan"),
                new QuizQuestion("Which grain is traditionally used to make most pasta?",
                        List.of("Oats", "Wheat", "Rye", "Barley"), "Wheat"),
                new QuizQuestion("Which Swedish pickled fish is a traditional dish at Christmas and midsummer?",
                        List.of("Gravlax", "Herring", "Mackerel", "Salmon"), "Herring"),
                new QuizQuestion("Which drink is made from fermented grapes?",
                        List.of("Juice", "Wine", "Cider", "Syrup"), "Wine"),
                new QuizQuestion("Which spice gives paella its characteristic yellow colour?",
                        List.of("Saffron", "Paprika", "Curry", "Turmeric"), "Saffron"),
                new QuizQuestion("Which country is most associated with tacos?",
                        List.of("Mexico", "Spain", "Argentina", "Chile"), "Mexico"),
                new QuizQuestion("What is the Italian dessert made with coffee-soaked biscuits and mascarpone cheese called?",
                        List.of("Panna cotta", "Tiramisu", "Gelato", "Cannoli"), "Tiramisu"),
                new QuizQuestion("Which drink is traditionally made from apples and can be alcoholic or non-alcoholic?",
                        List.of("Cider", "Juice", "Soda", "Smoothie"), "Cider"),
                new QuizQuestion("Which ingredient is needed to make bread rise?",
                        List.of("Salt", "Yeast", "Sugar", "Milk"), "Yeast"),
                new QuizQuestion("Which dish is often considered a Swedish national dish?",
                        List.of("Lasagne", "Tacos", "Meatballs", "Paella"), "Meatballs"),
                new QuizQuestion("Which fruit is yellow and curved?",
                        List.of("Banana", "Pear", "Apple", "Lemon"), "Banana"),
                new QuizQuestion("Which vegetable is the main ingredient in a classic tomato soup?",
                        List.of("Carrot", "Tomato", "Leek", "Broccoli"), "Tomato"),
                new QuizQuestion("What do you call frozen water cubes commonly added to drinks?",
                        List.of("Snow", "Ice cubes", "Granules", "Frost"), "Ice cubes"),
                new QuizQuestion("Which type of meat comes from pigs?",
                        List.of("Beef", "Lamb", "Pork", "Venison"), "Pork"),
                new QuizQuestion("What is the traditional Japanese soup made from fermented soybean paste and often tofu called?",
                        List.of("Ramen", "Miso soup", "Pho", "Tom Yum"), "Miso soup"),
                new QuizQuestion("Which orange vegetable grows underground and is commonly eaten raw or cooked?",
                        List.of("Tomato", "Carrot", "Cucumber", "Pepper"), "Carrot"),
                new QuizQuestion("What is the main ingredient in hummus?",
                        List.of("Lentils", "Chickpeas", "Beans", "Potatoes"), "Chickpeas"),
                new QuizQuestion("Which country is famous for serving small dishes called 'tapas'?",
                        List.of("France", "Spain", "Italy", "Portugal"), "Spain"),
                new QuizQuestion("Which berry is commonly used to make jam served with Swedish pancakes?",
                        List.of("Strawberries", "Blueberries", "Lingonberries", "Raspberries"), "Lingonberries"),
                new QuizQuestion("Which drink is made from roasted and ground coffee beans?",
                        List.of("Tea", "Coffee", "Soda", "Juice"), "Coffee"),
                new QuizQuestion("Tofu is primarily made from which ingredient?",
                        List.of("Rice", "Soybeans", "Corn", "Wheat"), "Soybeans"),
                new QuizQuestion("Which traditional Swedish soup is made from peas and often served with pork?",
                        List.of("Tomato soup", "Pea soup", "Potato soup", "Mushroom soup"), "Pea soup"),
                new QuizQuestion("Which French dessert consists of baked custard topped with a layer of hard caramelised sugar?",
                        List.of("Crème brûlée", "Éclair", "Profiterole", "Tarte tatin"), "Crème brûlée"),
                new QuizQuestion("Which fruit is the main ingredient in a classic apple pie?",
                        List.of("Pear", "Apple", "Banana", "Orange"), "Apple"),
                new QuizQuestion("What is the classic Italian dish made of spaghetti with minced meat sauce commonly called?",
                        List.of("Lasagne", "Spaghetti Bolognese", "Carbonara", "Pesto pasta"), "Spaghetti Bolognese"),
                new QuizQuestion("Which green, elongated vegetable is commonly used in salads?",
                        List.of("Cucumber", "Beetroot", "Parsnip", "Corn"), "Cucumber"),
                new QuizQuestion("Which Italian dish is made by slowly cooking rice with stock until it becomes creamy?",
                        List.of("Risotto", "Gnocchi", "Polenta", "Focaccia"), "Risotto")
        )));

    }
}

