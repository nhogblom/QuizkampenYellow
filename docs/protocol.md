# QuizKampen – Kommunikationsprotokoll (MVP)


## 1. SERVER → CLIENT : QUESTION
{
  "type": "QUESTION",
  "id": 1,
  "text": "Vilket år startade andra världskriget?",
  "answers": ["1937", "1939", "1941", "1943"]
}

## 2. CLIENT → SERVER : ANSWER
{
  "type": "ANSWER",
  "id": 1,
  "selectedIndex": 1
}

## 3. SERVER → CLIENT : RESULT
{
  "type": "RESULT",
  "correct": true,
  "correctIndex": 1
}

## Kommentarer:
- Protokollet skickas som JSON-rader.
- Ett meddelande per rad.
- Byggt för att kunna utökas senare (ronders protokoll, kategorier, scoring etc).
- 
