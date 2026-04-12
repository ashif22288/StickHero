package com.example.ap_game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
                Scene scene = new Scene(root);
                Platform.runLater(() -> {
                    stage.setScene(scene);
                    stage.setTitle("STICK HERO'S");
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    public static void main(String[] args) {
        launch();
    }
}
