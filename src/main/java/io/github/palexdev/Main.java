package io.github.palexdev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load beranda.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("beranda.fxml"));
            Parent root = loader.load();

            // Set scene
            Scene scene = new Scene(root);

            // Set title and scene to the primary stage
            primaryStage.setTitle("Aplikasi JavaFX");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Gagal memuat file FXML: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
