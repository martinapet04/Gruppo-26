package biblioteca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestioneLibriTest {

    private GestioneBiblioteca gestione;

    @BeforeEach
    void setUp() {
        gestione = new GestioneBiblioteca();
        gestione.getLibri().clear();
    }

    @Test
    void aggiungiLibro_ok() {
        Book b = new Book("Titolo Test", "Autore Test", 2024, "978-0000000001", 10);

        boolean ok = gestione.aggiungiLibro(b);

        assertTrue(ok);
        assertEquals(1, gestione.getLibri().size());
        assertEquals("978-0000000001", gestione.getLibri().get(0).getIsbn());
    }

    @Test
    void aggiungiLibro_isbnDuplicato() {
        Book b1 = new Book("Libro 1", "Autore", 2024, "978-0000000002", 5);
        Book b2 = new Book("Libro 2", "Autore", 2024, "978-0000000002", 3);

        assertTrue(gestione.aggiungiLibro(b1));
        boolean ok = gestione.aggiungiLibro(b2);

        assertFalse(ok);
        assertEquals(1, gestione.getLibri().size());
    }
}