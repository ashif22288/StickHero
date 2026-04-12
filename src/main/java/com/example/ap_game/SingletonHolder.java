package com.example.ap_game;

public class SingletonHolder {
    private static HelloApplication instance;

    private SingletonHolder() {
        // private constructor to prevent instantiation
    }

    public static synchronized HelloApplication getInstance() {
        if (instance == null) {
            instance = new HelloApplication();
        }
        return instance;
    }
}
