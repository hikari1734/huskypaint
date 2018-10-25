package com.huskypaint.app;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private Robot robot;
    private Window window;

    @Before
    public void setUp() {
        try {
            robot = new Robot();
            window = new Window();

        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }
    }

    @Test
    public void alwaysTrue() {
        assert(true);
    }

    @Test
    public void testColorPicker() {
        ColorPicker cp = new ColorPicker(Color.red);
        assert(cp.getCurrentColor().equals(Color.red));
    }

}
