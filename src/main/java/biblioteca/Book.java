package biblioteca;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Book implements Serializable {

    private final StringProperty titolo = new SimpleStringProperty();
    private final StringProperty autore = new SimpleStringProperty();
    private final IntegerProperty anno = new SimpleIntegerProperty();
    private final StringProperty isbn = new SimpleStringProperty();
    private final IntegerProperty copieDisponibili = new SimpleIntegerProperty();

    public Book(String titolo, String autore, int anno, String isbn, int copieDisponibili) {
        this.titolo.set(titolo);
        this.autore.set(autore);
        this.anno.set(anno);
        this.isbn.set(isbn);
        this.copieDisponibili.set(copieDisponibili);
    }

  
    public String getTitolo() {
        return titolo.get();
    }

    public void setTitolo(String titolo) {
        this.titolo.set(titolo);
    }

    public StringProperty titoloProperty() {
        return titolo;
    }

    public String getAutore() {
        return autore.get();
    }

    public void setAutore(String autore) {
        this.autore.set(autore);
    }

    public StringProperty autoreProperty() {
        return autore;
    }

    public int getAnno() {
        return anno.get();
    }

    public void setAnno(int anno) {
        this.anno.set(anno);
    }

    public IntegerProperty annoProperty() {
        return anno;
    }

    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public int getCopieDisponibili() {
        return copieDisponibili.get();
    }

    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili.set(copieDisponibili);
    }

    public IntegerProperty copieDisponibiliProperty() {
        return copieDisponibili;
    }


    public String getAutori() {
        return getAutore();
    }

    public void setAutori(String autori) {
        setAutore(autori);
    }

    public StringProperty autoriProperty() {
        return autoreProperty();
    }

    public int getDisponibili() {
        return getCopieDisponibili();
    }

    public void setDisponibili(int disponibili) {
        setCopieDisponibili(disponibili);
    }

    public IntegerProperty disponibiliProperty() {
        return copieDisponibiliProperty();
    }
}
