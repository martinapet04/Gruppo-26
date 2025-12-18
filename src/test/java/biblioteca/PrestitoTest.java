package biblioteca;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitoTest {

    @Test
    void statoPrestito_attivo_e_concluso() {
        LocalDate scadenza = LocalDate.now().plusDays(7);
        Prestito p = new Prestito("Mario Rossi", "Libro", scadenza);

        assertFalse(p.isConcluso());
        assertEquals("ATTIVO", p.getStatoVista());

        p.registraRestituzione();

        assertTrue(p.isConcluso());
        assertEquals("CONCLUSO", p.getStatoVista());
    }

    @Test
    void statoPrestito_inRitardo() {
        LocalDate scadPassata = LocalDate.now().minusDays(3);
        Prestito p = new Prestito("Mario Rossi", "Libro", scadPassata);

        assertFalse(p.isConcluso());
        assertEquals("IN RITARDO", p.getStatoVista());
    }
}