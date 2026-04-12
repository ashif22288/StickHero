package com.example.ap_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class checkCollisionWithHills extends HelloController {
    public checkCollisionWithHills(ImageView imageView, Rectangle rectangle, Stage stage) {
        hillTimeLine = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            Bounds imageViewBounds = imageView.getBoundsInParent();
            Bounds rectangeBound = rectangle.getBoundsInParent();
            if (imageViewBounds.intersects(rectangeBound)) {
                Controller3 controller3 = new Controller3(stage);
                hillTimeLine.stop();
            }
        }));

        hillTimeLine.setCycleCount(Timeline.INDEFINITE);
        hillTimeLine.play();
    }
}
