package biblioteca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class GestioneBibliotecaTest {

    private GestioneBiblioteca gestione;

    @BeforeEach
    void setUp() {
        gestione = new GestioneBiblioteca();
        gestione.getLibri().clear();
        gestione.getUtenti().clear();
        gestione.getPrestiti().clear();
    }

    @Test
    void registraPrestito_ok() {
        Utente u = new Utente("Mario", "Rossi", "M001", "mario.rossi@example.com");
        Book b = new Book("Libro", "Autore", 2024, "ISBN-1", 3);
        gestione.getUtenti().add(u);
        gestione.getLibri().add(b);

        boolean ok = gestione.registraPrestito(
                u.getNome() + " " + u.getCognome(),
                b.getTitolo(),
                LocalDate.now().plusDays(14)
        );

        assertTrue(ok);
        assertEquals(1, gestione.getPrestiti().size());
    }

    @Test
    void registraPrestito_troppiPrestitiUtente() {
        Utente u = new Utente("Mario", "Rossi", "M001", "mario.rossi@example.com");
        Book b = new Book("Libro", "Autore", 2024, "ISBN-1", 10);
        gestione.getUtenti().add(u);
        gestione.getLibri().add(b);

        for (int i = 0; i < 3; i++) {
            assertTrue(gestione.registraPrestito(
                    u.getNome() + " " + u.getCognome(),
                    b.getTitolo(),
                    LocalDate.now().plusDays(7 + i)
            ));
        }

        boolean ok = gestione.registraPrestito(
                u.getNome() + " " + u.getCognome(),
                b.getTitolo(),
                LocalDate.now().plusDays(30)
        );

        assertFalse(ok);
        assertEquals(3, gestione.getPrestiti().stream()
                .filter(p -> p.getUtente().equalsIgnoreCase(u.getNome() + " " + u.getCognome())
                        && !p.isConcluso())
                .count());
    }

    @Test
    void eliminaUtente_senzaPrestiti_ok() {
        Utente u = new Utente("Mario", "Rossi", "M001", "mario.rossi@example.com");
        gestione.getUtenti().add(u);

        boolean ok = gestione.eliminaUtente(u);

        assertTrue(ok);
        assertEquals(0, gestione.getUtenti().size());
    }

    @Test
    void eliminaUtente_conPrestiti_attivi_fallisce() {
        Utente u = new Utente("Mario", "Rossi", "M001", "mario.rossi@example.com");
        Book b = new Book("Libro", "Autore", 2024, "ISBN-1", 3);
        gestione.getUtenti().add(u);
        gestione.getLibri().add(b);

        gestione.registraPrestito(
                u.getNome() + " " + u.getCognome(),
                b.getTitolo(),
                LocalDate.now().plusDays(7)
        );

        boolean ok = gestione.eliminaUtente(u);

        assertFalse(ok);
        assertEquals(1, gestione.getUtenti().size());
    }
}