package biblioteca;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivioDAO {

    private static final String FILE_LIBRI = "libri.dat";
    private static final String FILE_UTENTI = "utenti.dat";
    private static final String FILE_PRESTITI = "prestiti.dat";

    @SuppressWarnings("unchecked")
    private static List<Object> caricaArchivio(String nomeFile) {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(nomeFile))) {
            System.out.println("DAO: Caricamento dati da " + nomeFile);
            return (List<Object>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("DAO: File non trovato (" + nomeFile + "). Inizio con archivio vuoto.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("DAO ERROR: Errore durante il caricamento da " + nomeFile + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static boolean salvaArchivio(List<?> dati, String nomeFile) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            oos.writeObject(dati);
            System.out.println("DAO: Dati salvati con successo su " + nomeFile);
            return true;
        } catch (IOException e) {
            System.err.println("DAO ERROR: Errore durante il salvataggio su " + nomeFile + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

   

    public static List<Object> caricaLibri() {
        return caricaArchivio(FILE_LIBRI);
    }

    public static boolean salvaLibri(ObservableList<?> libri) {
        return salvaArchivio(new ArrayList<>(libri), FILE_LIBRI);
    }

    public static List<Object> caricaUtenti() {
        return caricaArchivio(FILE_UTENTI);
    }

    public static boolean salvaUtenti(ObservableList<?> utenti) {
        return salvaArchivio(new ArrayList<>(utenti), FILE_UTENTI);
    }

    public static List<Object> caricaPrestiti() {
        return caricaArchivio(FILE_PRESTITI);
    }

    public static boolean salvaPrestiti(ObservableList<?> prestiti) {
        return salvaArchivio(new ArrayList<>(prestiti), FILE_PRESTITI);
    }
}