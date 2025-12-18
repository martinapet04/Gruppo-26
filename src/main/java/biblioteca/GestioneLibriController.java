package biblioteca;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class GestioneLibriController {

    private GestioneBiblioteca controller;

    @FXML private TableView<Book> tblLibri;
    @FXML private TableColumn<Book, String> colTitolo;
    @FXML private TableColumn<Book, String> colAutori;
    @FXML private TableColumn<Book, Number> colAnno;
    @FXML private TableColumn<Book, String> colIsbn;
    @FXML private TableColumn<Book, Number> colDisp;

    @FXML private ComboBox<String> cmbCercaPer;
    @FXML private TextField txtFiltro;

    private final ObservableList<Book> libri = FXCollections.observableArrayList();
    private FilteredList<Book> libriFiltrati;

    public void setController(GestioneBiblioteca controller) {
        this.controller = controller;
        if (controller != null) {
            libri.setAll(controller.getLibri());
            if (libriFiltrati != null) {
                libriFiltrati.setPredicate(null);
                tblLibri.setItems(libriFiltrati);
            }
            System.out.println("📚 Caricati " + libri.size() + " libri");
        }
    }

    @FXML
    private void initialize() {

        colTitolo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
                return param.getValue().titoloProperty();
            }
        });

        colAutori.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
                return param.getValue().autoriProperty();
            }
        });

        colIsbn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
                return param.getValue().isbnProperty();
            }
        });

        colAnno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Book, Number> param) {
                return param.getValue().annoProperty();
            }
        });

        colDisp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Book, Number> param) {
                return param.getValue().disponibiliProperty();
            }
        });

        libriFiltrati = new FilteredList<>(libri, b -> true);
        tblLibri.setItems(libriFiltrati);

        if (cmbCercaPer != null) {
            cmbCercaPer.setItems(FXCollections.observableArrayList("Titolo", "Autore", "ISBN"));
            cmbCercaPer.getSelectionModel().selectFirst();
        }

        if (txtFiltro != null) {
            txtFiltro.textProperty().addListener((obs, o, n) -> applicaFiltro());
        }
        if (cmbCercaPer != null) {
            cmbCercaPer.getSelectionModel().selectedItemProperty()
                    .addListener((obs, o, n) -> applicaFiltro());
        }
    }

    @FXML
    private void handleAggiungiLibro() {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Controller principale non inizializzato.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/biblioteca/AggiungiLibro.fxml"));
            Parent root = loader.load();

            AggiungiLibroController alc = loader.getController();
            alc.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Aggiungi Libro");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            libri.setAll(controller.getLibri());
            applicaFiltro();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile aprire AggiungiLibro.fxml:\n" + e.getMessage());
        }
    }

    @FXML
    private void handleModificaLibro() {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Controller principale non inizializzato.");
            return;
        }

        Book selected = tblLibri.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attenzione", "Seleziona un libro da modificare.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/biblioteca/ModificaLibro.fxml"));
            Parent root = loader.load();

            ModificaLibroController mlc = loader.getController();
            mlc.setController(controller);
            mlc.setLibro(selected);

            Stage stage = new Stage();
            stage.setTitle("Modifica Libro");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            
            libri.setAll(controller.getLibri());
            applicaFiltro();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile aprire ModificaLibro.fxml:\n" + e.getMessage());
        }
    }

    @FXML
    private void handleEliminaLibro() {
        if (controller == null) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Controller principale non inizializzato.");
            return;
        }

        Book selected = tblLibri.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attenzione", "Seleziona un libro da eliminare.");
            return;
        }

        
        boolean haPrestitiAttivi = controller.getPrestiti().stream()
                .filter(p -> !p.isConcluso())
                .anyMatch(p -> p.getLibro().equalsIgnoreCase(selected.getTitolo()));

        if (haPrestitiAttivi) {
            showAlert(Alert.AlertType.WARNING, "Eliminazione non consentita",
                    "Il libro selezionato non può essere eliminato perché è associato a prestiti attivi.");
            return;
        }

        controller.eliminaLibro(selected);
        libri.setAll(controller.getLibri());
        applicaFiltro();
        System.out.println("🗑️ Libro eliminato");
    }

    private void applicaFiltro() {
        if (libriFiltrati == null) return;

        String criterio = (cmbCercaPer != null && cmbCercaPer.getValue() != null)
                ? cmbCercaPer.getValue()
                : "Titolo";

        String testo = (txtFiltro != null && txtFiltro.getText() != null)
                ? txtFiltro.getText().toLowerCase().trim()
                : "";

        libriFiltrati.setPredicate(libro -> {
            if (testo.isEmpty()) return true;

            String t = safeLower(libro.getTitolo());
            String a = safeLower(libro.getAutori());
            String i = safeLower(libro.getIsbn());

            return switch (criterio) {
                case "Titolo" -> t.contains(testo);
                case "Autore" -> a.contains(testo);
                case "ISBN" -> i.contains(testo);
                default -> true;
            };
        });
    }

    private String safeLower(String s) {
        return s == null ? "" : s.toLowerCase();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
