package com.stuypulse;

/**
 * Just a file to store differnt constants
 */
public interface Constants {

    interface WindowSettings {
        int WIDTH = 640;
        int HEIGHT = 640;
        String TITLE = "Robot Simulation";

    }

    interface WheelProps {
        double SPEED = 20;
        double RC = 0.25;

        double ANGLE_RC = 0.1;
    }

    interface Physics {
        double DRAG_RC = 0.1;
    }
}