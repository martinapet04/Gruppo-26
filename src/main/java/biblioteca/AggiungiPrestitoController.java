package biblioteca;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
/**
 * Controller per la finestra "Nuovo Prestito" 
 */
public class AggiungiPrestitoController {

    @FXML private TextField txtMatricola;
    @FXML private TextField txtNomeUtente;
    @FXML private TextField txtTitoloLibro;
    @FXML private TextField txtIsbnLibro;
    @FXML private TextField txtDataScadenza;

    @FXML private Label lblErrore; // se non esiste in FXML, rimuovi anche qui

    private GestioneBiblioteca controller;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
    }

    @FXML
    private void initialize() {
        if (lblErrore != null) {
            lblErrore.setText("");
        }
    }

    @FXML
    private void handleSalvaPrestito() {
        if (controller == null) {
            mostraErrore("Errore interno: controller nullo.");
            return;
        }

        String matricola = txtMatricola.getText().trim();
        String nomeUtente = txtNomeUtente.getText().trim();
        String titoloLibro = txtTitoloLibro.getText().trim();
        String isbn = txtIsbnLibro.getText().trim();
        String dataStr = txtDataScadenza.getText().trim();

        if (matricola.isEmpty() || nomeUtente.isEmpty()
                || titoloLibro.isEmpty() || isbn.isEmpty() || dataStr.isEmpty()) {
            mostraErrore("Tutti i campi sono obbligatori.");
            return;
        }

        LocalDate scadenza;
        try {
            scadenza = LocalDate.parse(dataStr); 
        } catch (DateTimeParseException e) {
            mostraErrore("Data scadenza non valida. Usa formato AAAA-MM-GG.");
            return;
        }

        long attiviPrima = controller.contaPrestitiAttivi(nomeUtente);
        boolean ok = controller.registraPrestito(nomeUtente, titoloLibro, scadenza);

        if (!ok) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Prestito non consentito");
            alert.setHeaderText(null);

            if (attiviPrima >= 3) {
                alert.setContentText("L'utente non può avere più di 3 prestiti attivi.");
            } else {
                alert.setContentText("Nessuna copia disponibile per questo libro.");
            }

            alert.showAndWait();
            return;
        }

        Stage stage = (Stage) txtMatricola.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAnnulla() {
        Stage stage = (Stage) txtMatricola.getScene().getWindow();
        stage.close();
    }

    private void mostraErrore(String msg) {
        if (lblErrore != null) {
            lblErrore.setText(msg);
        } else {
            System.out.println("Errore AggiungiPrestito: " + msg);
        }
    }
}