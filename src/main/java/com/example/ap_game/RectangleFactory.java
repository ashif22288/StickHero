package com.example.ap_game;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory{
    @Override
    public Shape createShape() {
        return new Rectangle();
    }
}
