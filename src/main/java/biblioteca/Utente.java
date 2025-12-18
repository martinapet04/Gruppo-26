package biblioteca;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utente {

    private final StringProperty nome;
    private final StringProperty cognome;
    private final StringProperty matricola;
    private final StringProperty email;

    public Utente(String nome, String cognome, String matricola, String email) {
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.matricola = new SimpleStringProperty(matricola);
        this.email = new SimpleStringProperty(email);
    }

    

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty cognomeProperty() {
        return cognome;
    }

    public StringProperty matricolaProperty() {
        return matricola;
    }

    public StringProperty emailProperty() {
        return email;
    }

    

    public String getNome() {
        return nome.get();
    }

    public String getCognome() {
        return cognome.get();
    }

    public String getMatricola() {
        return matricola.get();
    }

    public String getEmail() {
        return email.get();
    }

    

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    public void setMatricola(String matricola) {
        this.matricola.set(matricola);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}