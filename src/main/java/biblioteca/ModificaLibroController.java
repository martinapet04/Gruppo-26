package biblioteca;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificaLibroController {

    @FXML private TextField txtTitolo;
    @FXML private TextField txtAutori;
    @FXML private TextField txtAnno;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtDisponibili;

    @FXML private Label lblErrore;

    private GestioneBiblioteca controller;
    private Book libroOriginale;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
    }

    public void setLibro(Book libro) {
        this.libroOriginale = libro;

        if (libro != null) {
            txtTitolo.setText(libro.getTitolo());
            txtAutori.setText(libro.getAutore());
            txtAnno.setText(String.valueOf(libro.getAnno()));
            txtIsbn.setText(libro.getIsbn());
            txtDisponibili.setText(String.valueOf(libro.getCopieDisponibili()));
        }
    }

    @FXML
    private void handleSalvaModifica() {
        if (controller == null || libroOriginale == null) {
            showError("Errore interno: libro o controller non impostati.");
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

       
        boolean isbnDuplicato = controller.getLibri().stream()
                .filter(b -> b != libroOriginale)
                .anyMatch(b -> b.getIsbn() != null && b.getIsbn().equalsIgnoreCase(isbn));

        if (isbnDuplicato) {
            showError("Esiste già un altro libro con questo ISBN.");
            return;
        }

        
        libroOriginale.setTitolo(titolo);
        libroOriginale.setAutore(autori);
        libroOriginale.setAnno(anno);
        libroOriginale.setIsbn(isbn);
        libroOriginale.setCopieDisponibili(disponibili);

        close();
    }

    @FXML
    private void handleAnnullaModifica() {
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
