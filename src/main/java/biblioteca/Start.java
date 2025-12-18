package biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        
        GestioneBiblioteca controllerCentrale = new GestioneBiblioteca();

        
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/biblioteca/Gestione-Biblioteca-Universitaria-1.fxml")
        );
        Parent root = loader.load();

        
        HomeController homeController = loader.getController();
        homeController.setController(controllerCentrale);

        Scene scene = new Scene(root);
        stage.setTitle("Biblioteca Universitaria");
        stage.setScene(scene);

        stage.setMinWidth(900);
        stage.setMinHeight(600);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}