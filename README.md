# Biblioteca Maven – Gruppo 26

Progetto per il corso di **Ingegneria del Software** (A.A. 2024/2025).  
L’obiettivo è sviluppare un sistema software per la gestione dei prestiti di una biblioteca universitaria, con interfaccia grafica JavaFX e build automatizzata tramite Maven.

---

## 👨‍👩‍👧‍👦 Membri del gruppo 26

| Matricola  | Cognome   | Nome    | E‑mail istituzionale                    | Ruolo principale        |
|-----------:|-----------|---------|-----------------------------------------|-------------------------|
| 0612710198  | Petrini   | Martina | m.petrini9@studenti.unisa.it           | Portavoce / Developer  |
| 0612708102  | Lanzara   | Valeria | v.lanza10@studenti.unisa.it            | Developer              |
| 0612710133  | Murano    | Andrea  | a.murano15@studenti.unisa.it           | Developer              |
| 0612709613  | Migliaccio| Luca    | l.migliaccio4@studenti.unisa.it        | Developer              |

---

## 📚 Descrizione del progetto

L’applicazione gestisce:

- Il **catalogo dei libri** (titolo, autori, anno, ISBN, copie disponibili).
- Le **schede degli utenti** (nome, cognome, matricola, e‑mail).
- I **prestiti attivi** con data prevista di restituzione e segnalazione di eventuali ritardi.

Tutte le operazioni vengono eseguite tramite una **GUI JavaFX**, pensata per l’operatore di biblioteca, e i dati sono salvati su file locali per garantire persistenza tra un’esecuzione e l’altra.

Funzionalità principali:

- Gestione libri: inserimento, modifica, cancellazione, ricerca e visualizzazione ordinata.
- Gestione utenti: registrazione, modifica, cancellazione, ricerca e visualizzazione ordinata.
- Gestione prestiti: apertura/chiusura prestiti, limite di 3 prestiti per utente, controllo copie disponibili, elenco prestiti e ritardi evidenziati.
- Salvataggio e caricamento automatico dell’intero archivio (libri, utenti, prestiti).

---

## 🛠 Requisiti software

- **Java 8+** (Zulu o OpenJDK)
- **Maven 3.x**
- **JUnit 5** (per i test automatizzati)

---

## 🚀 Come avviare il progetto

1. Clonare il repository:
   https://github.com/martinapet04/Gruppo-26.git

2. Compilare il progetto ed eseguire i test:
3. Avviare l’applicazione (profilo JavaFX standard, se configurato nel `pom.xml`):
 

   
   
