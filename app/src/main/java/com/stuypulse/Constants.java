package com.stuypulse;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import java.io.File;

/**
 * Just a file to store differnt constants
 */
public interface Constants {

    /**********************
     * Graphics Constants *
     **********************/

    static String getPath(String... parts) {
        return getPath(true, parts);
    }

    static String getPath(boolean isFile, String... parts) {
        StringBuilder out = new StringBuilder();

        String last = parts.length > 1 ? parts[parts.length - 1] : null;

        for (String part : parts) {
            out.append(part);
            if (part != last || !isFile)
                out.append(File.separator);
        }
        
        System.out.println(out.toString());
        return out.toString();
    }

    interface WindowSettings {
        int WIDTH = 1920;
        int HEIGHT = 1080;
        String TITLE = "Robot Simulation";

        boolean HIDE_MOUSE = true;
    
        String SHADER = getPath("app", "src", "main", "resources", "shaders", "uni_lighting"); 
            //"./app/src/main/resources/shaders/uni_lighting";
    
        String GRID_PATH = getPath("app", "src", "main", "resources", "meshes", "Grid.obj");
            // "./app/src/main/resources/meshes/Grid.obj";

        String SKYBOX_PATH = getPath("app", "src", "main", "resources", "shaders", "skybox"); 
            // "./app/src/main/resources/shaders/skybox";

        String CUBEMAP_BASE = getPath(false, "app", "src", "main", "resources", "skybox", "skybox");
        String[] CUBEMAP_PATH = { 
            CUBEMAP_BASE + "right.jpg",
            CUBEMAP_BASE + "left.jpg",
            CUBEMAP_BASE + "top.jpg",
            CUBEMAP_BASE + "bottom.jpg",
            CUBEMAP_BASE + "front.jpg",
            CUBEMAP_BASE + "back.jpg"
        };
    }

    interface CameraSettings {
        // Settings
        Angle FOV = Angle.k90deg;
        float NEAR_PLANE = 0.01f;
        float FAR_PLANE = 1_000f;
        
        Vector3f POSITION = new Vector3f(2.5f, 0.5f, 0);
        Angle YAW = Angle.fromDegrees(-45.0);
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
        float SLOW_SPEED = 0.5f;
        float FAST_SPEED = 5f;

        int TOGGLE_SIMULATION = GLFW_KEY_P;
    }

    interface SwerveDriveSettings {
        String SWERVE_PATH = getPath("app", "src", "main", "resources", "meshes", "Drivetrain_Assembly.obj");
            // "./app/src/main/resources/meshes/Drivetrain_Assembly.obj";
    }

    interface TankDriveSettings {
        String TANK_PATH = getPath("app", "src", "main", "resources", "meshes", "Drivetrain_Assembly.obj");
            // "./app/src/main/resources/meshes/Drivetrain_Assembly.obj";
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