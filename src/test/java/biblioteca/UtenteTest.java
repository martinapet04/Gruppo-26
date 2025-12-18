package biblioteca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {

    private GestioneBiblioteca gestione;

    @BeforeEach
    void setUp() {
        gestione = new GestioneBiblioteca();
        gestione.getUtenti().clear();
    }

    @Test
    void aggiungiUtente_ok() {
        boolean ok = gestione.aggiungiUtente(
                new Utente("Martina", "Petrini", "M1234", "utente.test@example.com")
        );

        assertTrue(ok);
        assertEquals(1, gestione.getUtenti().size());
        Utente u = (Utente) gestione.getUtenti().get(0);
        assertEquals("M1234", u.getMatricola());
        assertEquals("utente.test@example.com", u.getEmail());
    }

    @Test
    void aggiungiUtente_matricolaDuplicata() {
        gestione.aggiungiUtente(new Utente("A", "B", "M1234", "a@example.com"));

        boolean ok = gestione.aggiungiUtente(
                new Utente("C", "D", "M1234", "c@example.com")
        );

        assertFalse(ok);
        assertEquals(1, gestione.getUtenti().size());
    }

    @Test
    void aggiungiUtente_emailDuplicata() {
        gestione.aggiungiUtente(new Utente("A", "B", "M1111", "dup@example.com"));

        boolean ok = gestione.aggiungiUtente(
                new Utente("C", "D", "M2222", "dup@example.com")
        );

        assertFalse(ok);
        assertEquals(1, gestione.getUtenti().size());
    }
}