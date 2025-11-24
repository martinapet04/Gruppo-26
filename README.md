Biblioteca Universitaria - Gruppo-26

L’applicazione sviluppata da Gruppo-26 ha come obiettivo la gestione di una biblioteca universitaria, permettendo la registrazione e la consultazione dei dati relativi a libri, utenti e prestiti tramite una interfaccia grafica intuitiva. Il sistema facilita le operazioni di inserimento, modifica, ricerca, prestito e restituzione dei volumi e include strumenti per il monitoraggio delle scadenze e dei ritardi.​

Funzionalità principali
Registrazione, modifica e cancellazione dei dati dei libri (titolo, autori, anno, ISBN/codice, copie disponibili).​

Visualizzazione della lista dei libri ordinata per titolo e ricerca per autore, titolo o codice identificativo.​

Gestione dei dati degli utenti (nome, cognome, matricola, e-mail istituzionale, libri in prestito, data restituzione).​

Visualizzazione e ricerca della lista utenti per cognome/nome o matricola.​

Gestione completa dei prestiti e delle restituzioni, con vincoli specifici (max 3 libri in prestito per utente, prestito solo se disponibili, evidenziazione ritardi).​

Esportazione e salvataggio archivio completo su file.​

Installazione
Il progetto è configurato tramite Maven e può essere compilato e eseguito da terminale:

text
git clone https://github.com/martinapet04/Gruppo-26.git
cd Gruppo-26
mvn compile
mvn package
Per avviare il programma:

text
java -jar target/<nome-file>.jar
Struttura del repository
La struttura segue gli standard Maven:

text
src/
  main/
    java/
      com/
        mycompany/
          mavenproject/
  test/
    java/
      ...
Il file pom.xml contiene le dipendenze e i plugin necessari per compilazione, testing e packaging.​

Testing
I test automatici sono sviluppati con JUnit.

Lanciare i test tramite:

text
mvn test
Documentazione
Sono inclusi requisiti, casi d’uso, diagrammi delle classi e di sequenza, e descrizioni delle scelte progettuali all’interno delle cartelle dedicate nel repository.

La documentazione delle interfacce pubbliche delle classi viene generata tramite Doxygen.
