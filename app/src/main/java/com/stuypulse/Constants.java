package com.stuypulse;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Just a file to store differnt constants
 */
public interface Constants {

    /**********************
     * Graphics Constants *
     **********************/

    interface WindowSettings {
        int WIDTH = 960 * 2;
        int HEIGHT = 540 * 2;
        String TITLE = "Robot Simulation";
    
        String SHADER = "./app/src/main/resources/basic";
    }

    interface CameraSettings {
        // Settings
        Angle FOV = Angle.k90deg;
        float NEAR_PLANE = 0.01f;
        float FAR_PLANE = 1_000f;
        
        Vector3f POSITION = new Vector3f(0,5,0);
        Angle YAW = Angle.kZero;
        Angle PITCH = Angle.k90deg;
        Angle ROLL = Angle.kZero;

        // Movement
        int EXIT = GLFW_KEY_ESCAPE;
        int UP = GLFW_KEY_SPACE;
        int DOWN = GLFW_KEY_LEFT_SHIFT;
        int LEFT = GLFW_KEY_A;
        int RIGHT = GLFW_KEY_D;
        int FORWARD = GLFW_KEY_W;
        int BACK = GLFW_KEY_S;
        int SPEED_UP = GLFW_KEY_LEFT_CONTROL;

        float SENSITIVITY_X = 30f;
        float SENSITIVITY_Y = 30f;
        float SLOW_SPEED = 10f;
        float FAST_SPEED = 30f;
    }

    /************************
     * Simulation Constants *
     ************************/

    interface WheelProps {
        double SPEED = 20;
        double RC = 0.25;

        double ANGLE_RC = 0.1;
    }

    interface Physics {
        double DRAG_RC = 0.1;
    }
}