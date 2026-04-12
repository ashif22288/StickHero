package com.example.ap_game;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KeyPressTest extends HelloController {
    @Test
    public void test() {
        // Create an instance of HelloController
        HelloController helloController = new HelloController();

        // Set up your test environment if needed

        // Call the method you want to test
//        helloController.handleKeyPress(KeyCode.SPACE);
        helloController.music();

        // Assert the expected results
        assertEquals(true, helloController.isMusicPlaying);
        // Add more assertions if needed
    }

}
