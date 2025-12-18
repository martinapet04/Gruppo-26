package biblioteca;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class NuovoPrestitoController {

    @FXML private TableView<Utente> tblUtenti;
    @FXML private TableColumn<Utente, String> colMatricola;
    @FXML private TableColumn<Utente, String> colCognome;
    @FXML private TableColumn<Utente, String> colNome;

    @FXML private TableView<Book> tblLibri;
    @FXML private TableColumn<Book, String> colTitolo;
    @FXML private TableColumn<Book, String> colIsbn;
    @FXML private TableColumn<Book, Number> colDisp;

    @FXML private DatePicker dpScadenza;

    private GestioneBiblioteca controller;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
        if (controller != null) {
            tblUtenti.setItems(controller.getUtenti());
            tblLibri.setItems(controller.getLibri());
        }
    }

    @FXML
    private void initialize() {
        
        colMatricola.setCellValueFactory(data -> data.getValue().matricolaProperty());
        colCognome.setCellValueFactory(data -> data.getValue().cognomeProperty());
        colNome.setCellValueFactory(data -> data.getValue().nomeProperty());

        
        colTitolo.setCellValueFactory(data -> data.getValue().titoloProperty());
        colIsbn.setCellValueFactory(data -> data.getValue().isbnProperty());

        
        colDisp.setCellValueFactory(data -> data.getValue().copieDisponibiliProperty());

        tblUtenti.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblLibri.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        dpScadenza.setValue(LocalDate.now().plusDays(15));
    }

    @FXML
    private void handleConferma() {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore interno", "Controller nullo.");
            return;
        }

        Utente u = tblUtenti.getSelectionModel().getSelectedItem();
        Book b   = tblLibri.getSelectionModel().getSelectedItem();
        LocalDate scadenza = dpScadenza.getValue();

        if (u == null || b == null || scadenza == null) {
            showAlert(Alert.AlertType.WARNING, "Dati mancanti",
                    "Seleziona uno studente, un libro e una data di scadenza.");
            return;
        }

       
        if (b.getCopieDisponibili() <= 0) {
            showAlert(Alert.AlertType.WARNING, "Nessuna copia disponibile",
                    "Non ci sono copie disponibili per questo libro.");
            return;
        }

        
        String nomeCompleto = (u.getNome() + " " + u.getCognome()).trim();
        String titoloLibro = b.getTitolo().trim();

        
        boolean giaPreso = controller.getPrestiti().stream()
                .filter(p -> !p.isConcluso())
                .anyMatch(p ->
                        p.getUtente() != null &&
                        p.getLibro() != null &&
                        p.getUtente().trim().equalsIgnoreCase(nomeCompleto) &&
                        p.getLibro().trim().equalsIgnoreCase(titoloLibro)
                );

        if (giaPreso) {
            showAlert(Alert.AlertType.WARNING, "Libro già in prestito",
                    "Questo utente ha già un prestito attivo per questo libro.");
            return;
        }

        
        long attivi = controller.getPrestiti().stream()
                .filter(p -> !p.isConcluso())
                .filter(p -> p.getUtente() != null && p.getUtente().trim().equalsIgnoreCase(nomeCompleto))
                .count();

        if (attivi >= 3) {
            showAlert(Alert.AlertType.WARNING, "Limite prestiti",
                    "L'utente non può avere più di 3 prestiti attivi.");
            return;
        }

        
        boolean ok = controller.registraPrestito(nomeCompleto, titoloLibro, scadenza);

        if (!ok) {
            showAlert(Alert.AlertType.ERROR, "Errore",
                    "Impossibile registrare il prestito.");
            return;
        }

        close();
    }

    @FXML
    private void handleAnnulla() {
        close();
    }

    private void close() {
        Stage stage = (Stage) tblUtenti.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
