package biblioteca;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private GestioneBiblioteca controller;

    @Override
    public void start(Stage stage) throws IOException {

        controller = new GestioneBiblioteca();


        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/unisa/gruppo26/biblioteca/Home.fxml")
        );

        Parent root = loader.load();


        HomeController homeController = loader.getController();
        homeController.setController(controller);

        Scene scene = new Scene(root);
        stage.setTitle("Gestione Biblioteca Universitaria (Gruppo 26)");
        stage.setScene(scene);


        stage.setMinWidth(900);
        stage.setMinHeight(600);

        stage.setOnCloseRequest(e -> {

            System.out.println("Applicazione in chiusura. Salvataggio finale...");

        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}