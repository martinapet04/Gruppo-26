package biblioteca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class GestioneBiblioteca {

   
    private final ObservableList<Book> libri = FXCollections.observableArrayList();

    public ObservableList<Book> getLibri() {
        return libri;
    }

    public boolean aggiungiLibro(Book libro) {
        for (Book b : libri) {
            if (b.getIsbn().equalsIgnoreCase(libro.getIsbn())) {
                return false;
            }
        }
        libri.add(libro);
        return true;
    }

    public void eliminaLibro(Book libro) {
        libri.remove(libro);
    }

    private final ObservableList<Utente> utenti = FXCollections.observableArrayList();

    public ObservableList<Utente> getUtenti() {
        return utenti;
    }

    public boolean aggiungiUtente(Utente utente) {
        for (Utente u : utenti) {
            if (u.getMatricola().equalsIgnoreCase(utente.getMatricola())
                    || u.getEmail().equalsIgnoreCase(utente.getEmail())) {
                return false;
            }
        }
        utenti.add(utente);
        return true;
    }

    public boolean aggiungiUtente(String nome, String cognome, String matricola, String email) {
        Utente u = new Utente(nome, cognome, matricola, email);
        return aggiungiUtente(u);
    }

    
    public boolean eliminaUtente(Utente utente) {
        String nomeCompleto = utente.getNome() + " " + utente.getCognome();

        boolean haPrestitiAttivi = prestiti.stream()
                .anyMatch(p -> p.getUtente().equalsIgnoreCase(nomeCompleto)
                        && !p.isConcluso());

        if (haPrestitiAttivi) {
            return false; 
        }

        utenti.remove(utente);
        return true;
    }

    
    private final ObservableList<Prestito> prestiti = FXCollections.observableArrayList();

    public ObservableList<Prestito> getPrestiti() {
        return prestiti;
    }

    
    public long contaPrestitiAttivi(String nomeUtente) {
        return prestiti.stream()
                .filter(p -> p.getUtente().equalsIgnoreCase(nomeUtente))
                .filter(p -> !p.isConcluso())
                .count();
    }

    /**
     * Registra un nuovo prestito se:
     * - l'utente non ha già 3 prestiti attivi
     * - il libro ha almeno 1 copia disponibile
     *
     */
    public boolean registraPrestito(String nomeUtente, String titoloLibro, LocalDate dataScadenza) {

        // 1) Controllo massimo 3 prestiti per utente
        long attivi = contaPrestitiAttivi(nomeUtente);
        if (attivi >= 3) {
            return false; // limite superato
        }

        // 2) Trova il libro e controlla copie disponibili
        Book libro = null;
        for (Book b : libri) {
            if (b.getTitolo().equalsIgnoreCase(titoloLibro)) {
                libro = b;
                break;
            }
        }
        if (libro == null) {
           
            return false;
        }

        if (libro.getCopieDisponibili() <= 0) {
            
            return false;
        }

        // 3) Decrementa le copie disponibili
        libro.setCopieDisponibili(libro.getCopieDisponibili() - 1);

        // 4) Crea e aggiunge il prestito
        Prestito p = new Prestito(nomeUtente, titoloLibro, dataScadenza);
        prestiti.add(p);

        return true;
    }

    public void eliminaPrestito(Prestito p) {
        prestiti.remove(p);
    }

    public void registraRestituzione(Prestito p) {
        if (p == null) return;
        p.registraRestituzione(); 

        
        for (Book b : libri) {
            if (b.getTitolo().equalsIgnoreCase(p.getLibro())) {
                b.setCopieDisponibili(b.getCopieDisponibili() + 1);
                break;
            }
        }
    }

    
    public GestioneBiblioteca() {

        
        libri.add(new Book(
                "Essentials of Software Engineering (Fifth Edition)",
                "Frank Tsui, Orlando Karam, Barbara Bernal",
                2023,
                "978-1-284-22899-1",
                3
        ));
        libri.add(new Book(
                "Lezioni di Automatica - Volume I",
                "Francesco Basile, Pasquale Chiacchio",
                2007,
                "978-88-95028-06-4",
                2
        ));
        libri.add(new Book(
                "Lezioni di Automatica - Volume II",
                "Francesco Basile, Pasquale Chiacchio",
                2007,
                "978-88-95028-07-1",
                2
        ));
        libri.add(new Book(
                "Manuale di Java 8",
                "Claudio De Sio Cesari",
                2014,
                "978-88-203-6400-7",
                4
        ));
        libri.add(new Book(
                "The Definitive Guide to HTML5",
                "Adam Freeman",
                2011,
                "978-1-4302-3960-4",
                3
        ));

        
        utenti.add(new Utente("Martina", "Petrini",
                "0612710198", "m.petrini@studenti.unisa.it"));
        utenti.add(new Utente("Andrea", "Murano",
                "0612710133", "a.murano15@studenti.unisa.it"));
        utenti.add(new Utente("Valeria", "Lanzara",
                "0612708102", "V.Lanzara10@studenti.unisa.it"));
        utenti.add(new Utente("Luca", "Migliaccio",
                "0612709713", "l.migliaccio4@studenti.unisa.it"));

        
        registraPrestito(
                "Andrea Murano",
                "Essentials of Software Engineering (Fifth Edition)",
                LocalDate.now().minusDays(5)
        );

        registraPrestito(
                "Martina Petrini",
                "Manuale di Java 8",
                LocalDate.now().plusDays(10)
        );
    }
}