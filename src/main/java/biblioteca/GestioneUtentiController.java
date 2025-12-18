package biblioteca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GestioneUtentiController {

    private GestioneBiblioteca controller;

    @FXML private TableView<Utente> tblUtenti;
    @FXML private TableColumn<Utente, String> colNome;
    @FXML private TableColumn<Utente, String> colCognome;
    @FXML private TableColumn<Utente, String> colMatricola;
    @FXML private TableColumn<Utente, String> colEmail;

    @FXML private ComboBox<String> cmbFiltroUtenti;
    @FXML private TextField txtFiltroUtenti;

    private final ObservableList<Utente> utenti = FXCollections.observableArrayList();
    private FilteredList<Utente> utentiFiltrati;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
        if (controller != null) {
            utenti.setAll(controller.getUtenti());
            if (utentiFiltrati != null) {
                utentiFiltrati.setPredicate(null);
                tblUtenti.setItems(utentiFiltrati);
            }
        }
    }

    @FXML
    private void initialize() {
       
        colNome.setCellValueFactory(cd -> cd.getValue().nomeProperty());
        colCognome.setCellValueFactory(cd -> cd.getValue().cognomeProperty());
        colMatricola.setCellValueFactory(cd -> cd.getValue().matricolaProperty());
        colEmail.setCellValueFactory(cd -> cd.getValue().emailProperty());

        
        if (cmbFiltroUtenti != null) {
            cmbFiltroUtenti.setItems(FXCollections.observableArrayList(
                    "Nome", "Cognome", "Matricola", "Email"
            ));
            cmbFiltroUtenti.getSelectionModel().select("Nome");
            cmbFiltroUtenti.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((obs, o, n) -> applicaFiltro());
        }

       
        utentiFiltrati = new FilteredList<>(utenti, u -> true);
        tblUtenti.setItems(utentiFiltrati);

        
        if (txtFiltroUtenti != null) {
            txtFiltroUtenti.textProperty()
                    .addListener((obs, o, n) -> applicaFiltro());
        }
    }

    @FXML
    private void handleNuovoUtente() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/biblioteca/Nuovo-Utente.fxml"));
            Parent root = loader.load();

            NuovoUtenteController nuc = loader.getController();
            nuc.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Nuovo Utente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            if (controller != null) {
                utenti.setAll(controller.getUtenti());
                applicaFiltro();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModificaUtente() {
        Utente selezionato = tblUtenti.getSelectionModel().getSelectedItem();
        if (selezionato == null || controller == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/biblioteca/Nuovo-Utente.fxml"));
            Parent root = loader.load();

            NuovoUtenteController nuc = loader.getController();
            nuc.setController(controller);
            nuc.setUtente(selezionato);

            Stage stage = new Stage();
            stage.setTitle("Modifica Utente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            utenti.setAll(controller.getUtenti());
            applicaFiltro();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEliminaUtente() {
        Utente selezionato = tblUtenti.getSelectionModel().getSelectedItem();
        if (selezionato != null && controller != null) {
            boolean eliminato = controller.eliminaUtente(selezionato);
            if (!eliminato) {
                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle("Impossibile eliminare utente");
                alert.setHeaderText(null);
                alert.setContentText("Questo utente ha prestiti attivi e non può essere eliminato.");
                alert.showAndWait();
                return;
            }
            utenti.setAll(controller.getUtenti());
            applicaFiltro();
        }
    }

    private void applicaFiltro() {
        if (utentiFiltrati == null) return;

        String testo = (txtFiltroUtenti != null)
                ? txtFiltroUtenti.getText().toLowerCase().trim()
                : "";

        String criterio = (cmbFiltroUtenti != null && cmbFiltroUtenti.getValue() != null)
                ? cmbFiltroUtenti.getValue()
                : "Nome";

        utentiFiltrati.setPredicate(u -> {
            if (testo.isEmpty()) return true;

            return switch (criterio) {
                case "Nome" -> u.getNome().toLowerCase().contains(testo);
                case "Cognome" -> u.getCognome().toLowerCase().contains(testo);
                case "Matricola" -> u.getMatricola().toLowerCase().contains(testo);
                case "Email" -> u.getEmail().toLowerCase().contains(testo);
                default -> true;
            };
        });
    }
}