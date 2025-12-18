package biblioteca;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestito implements Serializable {

    private final StringProperty utente    = new SimpleStringProperty();
    private final StringProperty libro     = new SimpleStringProperty();
    private final StringProperty scadenza  = new SimpleStringProperty();
    private final StringProperty statoVista = new SimpleStringProperty();

    public Prestito(String nomeUtente, String titoloLibro, LocalDate dataScadenza) {
        this.utente.set(nomeUtente);
        this.libro.set(titoloLibro);
        this.scadenza.set(dataScadenza.toString());
        if (dataScadenza.isBefore(LocalDate.now())) {
            this.statoVista.set("IN RITARDO");
        } else {
            this.statoVista.set("ATTIVO");
        }
    }

    public boolean isInRitardo() {
        try {
            LocalDate oggi = LocalDate.now();
            LocalDate scad = LocalDate.parse(scadenza.get());
            return scad.isBefore(oggi) && !isConcluso();
        } catch (Exception e) {
            return false;
        }
    }

    public void registraRestituzione() {
        this.statoVista.set("CONCLUSO");
    }

    public boolean isConcluso() {
        return "CONCLUSO".equalsIgnoreCase(statoVista.get());
    }

    
    public LocalDate getScadenzaDate() {
        try {
            return LocalDate.parse(scadenza.get());
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    
    public StringProperty utenteProperty()   { return utente; }
    public StringProperty libroProperty()    { return libro; }
    public StringProperty scadenzaProperty() { return scadenza; }
    public StringProperty statoVistaProperty() { return statoVista; }

    public String getUtente()      { return utente.get(); }
    public String getLibro()       { return libro.get(); }
    public String getScadenza()    { return scadenza.get(); }
    public String getStatoVista()  { return statoVista.get(); }
}