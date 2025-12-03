package org.example;

public enum MessageTypes {
    MATCH_STARTED,
    QUESTION,       // Server → Klient: en fråga
    ANSWER,         // Klient → Server: spelarens svar
    ROUND_RESULT,   // Server → Klient: poäng för rundan
    GAME_RESULT,    // Server → Klient: slutresultat
    CATEGORY_CHOICE,// Klient → Server: valt kategori
    GIVE_UP,        // Klient → Server: spelaren ger upp
    CHAT,            // Klient ↔ Server: chatmeddelande
    USERNAME,
    DEVELOPMENTMSG,
    CATEGORYTOPLAY, PLAYAGAIN, SERVERMSG// used during development
}
