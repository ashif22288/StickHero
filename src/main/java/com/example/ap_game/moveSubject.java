package com.example.ap_game;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public class moveSubject extends HelloController{
    public moveSubject(double baseEndOrdinate) {
        // Create a TranslateTransition
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imageView);
        translateTransition.setByX(baseEndOrdinate - prevLadderEndCoordinate);
        translateTransition.setDuration(Duration.seconds(2));

        prevLadderEndCoordinate = breadth;

        checkCollisionDuringTranslation();
        checkCollisionWithHills check = new checkCollisionWithHills(imageView, rectangle, stage);


        // Set an event handler to be triggered when the animation finishes
        translateTransition.setOnFinished(event -> {
            timeline.stop();
            if(isCollision) isCollision = false;
            else {
                System.out.println("in!");
                Group rootGroup = (Group) scene.getRoot();
                rootGroup.getChildren().remove(cherryImage);
            }
            runParallel();
        });

        // Start the animation
        translateTransition.play();
    }
}
