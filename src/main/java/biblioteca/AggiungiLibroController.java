package biblioteca;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AggiungiLibroController {

    @FXML private TextField txtTitolo;
    @FXML private TextField txtAutori;
    @FXML private TextField txtAnno;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtDisponibili;

    @FXML private Label lblErrore;

    private GestioneBiblioteca controller;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
    }

    @FXML
    private void handleSalvaLibro() {
        if (controller == null) {
            showError("Errore interno: controller nullo.");
            return;
        }

        String titolo = safe(txtTitolo.getText());
        String autori = safe(txtAutori.getText());
        String isbn   = safe(txtIsbn.getText());

        if (titolo.isEmpty() || autori.isEmpty() || isbn.isEmpty()) {
            showError("Titolo, autori e ISBN sono obbligatori.");
            return;
        }

        int anno;
        int disponibili;

        try {
            anno = Integer.parseInt(safe(txtAnno.getText()));
        } catch (Exception e) {
            showError("Anno non valido (es. 2023).");
            return;
        }

        try {
            disponibili = Integer.parseInt(safe(txtDisponibili.getText()));
        } catch (Exception e) {
            showError("Disponibili non valido (es. 5).");
            return;
        }

        if (disponibili < 0) {
            showError("Disponibili non può essere negativo.");
            return;
        }

      
        boolean isbnGiaPresente = controller.getLibri().stream()
                .anyMatch(b -> b.getIsbn() != null && b.getIsbn().equalsIgnoreCase(isbn));
        if (isbnGiaPresente) {
            showError("Esiste già un libro con questo ISBN.");
            return;
        }

        Book nuovo = new Book(titolo, autori, anno, isbn, disponibili);
        controller.getLibri().add(nuovo);

        close();
    }

    @FXML
    private void handleAnnullaLibro() {
        close();
    }

    private void close() {
        Stage stage = (Stage) txtTitolo.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        if (lblErrore != null) {
            lblErrore.setText(msg);
            lblErrore.setVisible(true);
        }
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
