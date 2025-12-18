package biblioteca;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NuovoUtenteController {

    private GestioneBiblioteca controller;
    private Utente utenteDaModificare; 

    @FXML private TextField txtNome;
    @FXML private TextField txtCognome;
    @FXML private TextField txtMatricola;
    @FXML private TextField txtEmail;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
    }

    
    public void setUtente(Utente u) {
        this.utenteDaModificare = u;
        if (u != null) {
            txtNome.setText(u.getNome());
            txtCognome.setText(u.getCognome());
            txtMatricola.setText(u.getMatricola());
            txtEmail.setText(u.getEmail());
        }
    }

    @FXML
    private void handleAnnulla() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSalva() {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String matricola = txtMatricola.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty() || cognome.isEmpty() || matricola.isEmpty() || email.isEmpty()) {
            mostraErrore("Tutti i campi sono obbligatori.");
            return;
        }

        if (controller == null) {
            mostraErrore("Controller non inizializzato.");
            return;
        }

        
        if (utenteDaModificare != null) {
            utenteDaModificare.setNome(nome);
            utenteDaModificare.setCognome(cognome);
            utenteDaModificare.setMatricola(matricola);
            utenteDaModificare.setEmail(email);

            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.close();
            return;
        }

        
        boolean ok = controller.aggiungiUtente(nome, cognome, matricola, email);
        if (!ok) {
            mostraErrore("Esiste già un utente con la stessa matricola o la stessa email.");
            return;
        }

        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }

    
    @FXML
    private void handleSalvaUtente() {
        handleSalva();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}