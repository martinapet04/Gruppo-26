package biblioteca;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private GestioneBiblioteca controller;

    @FXML private BorderPane root;
    @FXML private ImageView imgSfondo;

    @FXML private Button btnLogin;
    @FXML private Button btnCatalogoLibri;
    @FXML private Button btnElencoPrestiti;
    @FXML private Button btnElencoUtenti;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
    }

    @FXML
    private void initialize() {
        
        if (imgSfondo != null && root != null) {
            imgSfondo.fitWidthProperty().bind(root.widthProperty().subtract(220));   
            imgSfondo.fitHeightProperty().bind(root.heightProperty().subtract(140)); 
        }
    }

    @FXML
    private void handleApriCatalogoLibri() {
        apriFinestra("/biblioteca/Gestione-Libri.fxml", "Catalogo Libri");
    }

    @FXML
    private void handleApriPrestiti() {
        apriFinestra("/biblioteca/Gestione-Prestiti.fxml", "Elenco Prestiti");
    }

    @FXML
    private void handleApriUtenti() {
        apriFinestra("/biblioteca/Gestione-Utenti.fxml", "Gestione Utenti");
    }

    @FXML
    private void handleApriLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/biblioteca/Pagina-Login.fxml"));
            Parent rootLogin = loader.load();

            
            Object c = loader.getController();
            if (c instanceof LoginController lc) {
                lc.setController(controller);
            }

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(rootLogin));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile aprire Pagina-Login.fxml");
        }
    }

    private void apriFinestra(String fxmlPath, String titolo) {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore interno",
                    "GestioneBiblioteca (controller principale) è NULL.\n" +
                    "Devi chiamare homeController.setController(...) quando carichi la Home.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent rootFiglio = loader.load();

            Object childController = loader.getController();

            if (childController instanceof GestioneLibriController glc) {
                glc.setController(controller);
            } else if (childController instanceof GestionePrestitiController gpc) {
                gpc.setController(controller);
            } else if (childController instanceof GestioneUtentiController guc) {
                guc.setController(controller);
            } 

            Stage stage = new Stage();
            stage.setTitle(titolo);
            stage.setScene(new Scene(rootFiglio, 900, 600));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile aprire: " + fxmlPath);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
