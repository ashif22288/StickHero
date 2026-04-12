package com.example.ap_game;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Controller3 extends HelloController {
    public Controller3(Stage stage) {
        terminateGamePlay(stage);
    }
    public void terminateGamePlay(Stage stage) {
        TranslateTransition uptransition = new TranslateTransition();
        uptransition.setNode(imageView);
        uptransition.setByY(-30);
        uptransition.setDuration(Duration.millis(100));

        uptransition.setOnFinished(actionEvent -> {
            TranslateTransition fallTransition = new TranslateTransition();
            fallTransition.setNode(imageView);
            fallTransition.setByY(500);
            fallTransition.setDuration(Duration.seconds(1));

            fallTransition.setOnFinished(event3 -> {
                // Instead of printing to the terminal, display the bonus on the scene
                try {
                    // Load the new FXML file (scene3.fxml)
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
                    Parent root = loader.load();


                    Controller3 h = loader.getController();
                    h.playerSet();
//
                    // Set the new scene in the stage
                    Scene newScene = new Scene(root);
                    stage.setScene(newScene);

//                    if (scoreLabel == null) {
//                        scoreLabel = new Label("SCORE : " + Score);
//                        scoreLabel.setLayoutX(0);
//                        scoreLabel.setLayoutY(0);
//                        scoreLabel.setStyle("-fx-font-size: 40; -fx-font-weight: bold; -fx-text-fill: blue;");
//
//                        Group rootGroup = (Group) scene.getRoot();
//                        rootGroup.getChildren().add(scoreLabel);
//                    } else {
//                        scoreLabel.setText("SCORE : " + Score);
//                    }

                    // Show the stage with the new scene
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fallTransition.play();
        });

        uptransition.play();
    }

    public void playerSet() {
        scoreLabelOnScene3.setText("ajshgfaslhdf;jhas;ldhf");
    }
}
