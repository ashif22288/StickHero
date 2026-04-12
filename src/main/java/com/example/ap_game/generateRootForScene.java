package com.example.ap_game;

public class generateRootForScene {
    private static generateRootForScene gen = null;

    public static generateRootForScene getInstance() {
        if (gen == null) {
            gen = new generateRootForScene();
        }
        return gen;
    }

    private generateRootForScene() {
    }
}
