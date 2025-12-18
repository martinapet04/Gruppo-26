package biblioteca;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkRicordami;

    private GestioneBiblioteca controller;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
    }

    @FXML
    private void handleLogin() {
        String user = (txtUsername.getText() == null) ? "" : txtUsername.getText().trim();
        String pass = (txtPassword.getText() == null) ? "" : txtPassword.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Dati mancanti",
                    "Inserisci username e password.");
            return;
        }

        
        if ("admin".equals(user) && "admin".equals(pass)) {
            close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login fallito",
                    "Username o password non corretti.");
        }
    }

    @FXML
    private void handleAnnulla() {
        close();
    }

    private void close() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
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
