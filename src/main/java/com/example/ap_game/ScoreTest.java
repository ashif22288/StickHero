package com.example.ap_game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ScoreTest extends HelloController {

    @Test
    public void test() {
        assertEquals(0, HelloController.Score);
    }
}
