package com.example.ap_game;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.Random;

public class generateCherry extends HelloController {
    public  generateCherry() {
        boolean generate = new Random().nextBoolean();

        if (true) {
            System.out.println("Cherry generated!");
            double xordinate = randomNumberGenerator(startRectangle.getWidth(), xCoordinate);

            if(xordinate > startRectangle.getWidth() && xordinate <  rectangle.getLayoutX() - rectangle.getWidth() && (rectangle.getLayoutX() - rectangle.getWidth() - startRectangle.getWidth()) > 30) {
                cherryImage = new ImageView("cherry.png");
                cherryImage.setFitWidth(30);
                cherryImage.setFitHeight(30);
                cherryImage.setX(xordinate);
                cherryImage.setY(500);

                Group rootGroup = (Group) scene.getRoot();
                rootGroup.getChildren().add(cherryImage);
            }
        }
        else cherryImage = null;
    }
}
