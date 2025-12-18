package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    private Label lblTitolo;
    @FXML
    private TextArea txtMessaggio;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setErrore(String titolo, String messaggio) {
        lblTitolo.setText(titolo);
        txtMessaggio.setText(messaggio);
    }

    @FXML
    private void handleOk() {
        dialogStage.close();
    }
}