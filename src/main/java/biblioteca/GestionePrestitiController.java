package biblioteca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class GestionePrestitiController {

    private GestioneBiblioteca controller;

    @FXML private TableView<Prestito> tblPrestiti;
    @FXML private TableColumn<Prestito, String> colLibro;
    @FXML private TableColumn<Prestito, String> colUtente;
    @FXML private TableColumn<Prestito, String> colDataInizio; // scadenza
    @FXML private TableColumn<Prestito, String> colDataFine;   // stato
    @FXML private TextField txtFiltroPrestiti;

    private final ObservableList<Prestito> prestiti = FXCollections.observableArrayList();
    private FilteredList<Prestito> prestitiFiltrati;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
        refreshDaModel();
    }

    @FXML
    private void initialize() {
        // colonne
        colLibro.setCellValueFactory(d -> d.getValue().libroProperty());
        colUtente.setCellValueFactory(d -> d.getValue().utenteProperty());
        colDataInizio.setCellValueFactory(d -> d.getValue().scadenzaProperty());
        colDataFine.setCellValueFactory(d -> d.getValue().statoVistaProperty());

       
        tblPrestiti.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Prestito item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                } else if ("IN RITARDO".equalsIgnoreCase(item.getStatoVista())) {
                    setStyle("-fx-background-color: #ffcccc;");
                } else {
                    setStyle("");
                }
            }
        });

        
        prestitiFiltrati = new FilteredList<>(prestiti, p -> true);
        tblPrestiti.setItems(prestitiFiltrati);

        
        if (txtFiltroPrestiti != null) {
            txtFiltroPrestiti.textProperty().addListener((obs, oldV, newV) -> applicaFiltroPrestiti());
        }
    }

    @FXML
    private void handleNuovoPrestito() {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore interno", "Controller principale non impostato (setController non chiamato).");
            return;
        }

        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/biblioteca/Nuovo-Prestito.fxml"));
            Parent root = loader.load();

            NuovoPrestitoController npc = loader.getController();
            npc.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Nuovo Prestito");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            refreshDaModel();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile aprire /biblioteca/Nuovo-Prestito.fxml");
        }
    }

    @FXML
    private void handleRestituzione() {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore interno", "Controller principale non impostato.");
            return;
        }

        Prestito selected = tblPrestiti.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selezione mancante", "Seleziona un prestito dalla tabella.");
            return;
        }

        controller.registraRestituzione(selected);
        refreshDaModel();
    }

    @FXML
    private void handleEliminaPrestito() {
        
        handleRestituzione();
    }

    @FXML
    private void handleCercaPrestiti() {
        applicaFiltroPrestiti();
    }

    @FXML
    private void handleCambioFiltro() {
        applicaFiltroPrestiti();
    }

    private void refreshDaModel() {
        if (controller == null) return;

        prestiti.setAll(controller.getPrestiti());
        ordinaPrestiti();
        applicaFiltroPrestiti();
    }

    private void applicaFiltroPrestiti() {
        if (prestitiFiltrati == null) return;

        String testo = (txtFiltroPrestiti == null || txtFiltroPrestiti.getText() == null)
                ? ""
                : txtFiltroPrestiti.getText().trim().toLowerCase();

        if (testo.isEmpty()) {
            prestitiFiltrati.setPredicate(p -> true);
            return;
        }

        prestitiFiltrati.setPredicate(p ->
                (p.getLibro() != null && p.getLibro().toLowerCase().contains(testo)) ||
                (p.getUtente() != null && p.getUtente().toLowerCase().contains(testo))
        );
    }

    
    private void ordinaPrestiti() {
        FXCollections.sort(prestiti, (p1, p2) -> {
            boolean concl1 = p1.isConcluso();
            boolean concl2 = p2.isConcluso();

            if (concl1 && !concl2) return 1;
            if (!concl1 && concl2) return -1;

            LocalDate d1 = p1.getScadenzaDate();
            LocalDate d2 = p2.getScadenzaDate();

            
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return 1;
            if (d2 == null) return -1;

            boolean scad1 = d1.isBefore(LocalDate.now());
            boolean scad2 = d2.isBefore(LocalDate.now());

            if (!concl1) {
                if (scad1 && !scad2) return -1;
                if (!scad1 && scad2) return 1;
            }
            return d1.compareTo(d2);
        });
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
