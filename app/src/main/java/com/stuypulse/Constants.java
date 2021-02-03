package com.stuypulse;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Just a file to store differnt constants
 */
public interface Constants {

    /**********************
     * Graphics Constants *
     **********************/

    Path APP = Paths.get(System.getProperty("user.dir"));
    Path RESOURCES = APP.resolve("src").resolve("main").resolve("resources");
    Path SHADERS = RESOURCES.resolve("shaders");
    Path SKYBOX = RESOURCES.resolve("skybox");
    Path MESHES = RESOURCES.resolve("meshes");

    static String resolve(Path a, String b, String... c) {
        a = a.resolve(b);

        for (String part : c)
            a = a.resolve(part);

        return a.toString();
    }

    interface WindowSettings {
        int WIDTH = 1080;
        int HEIGHT = 540;
        String TITLE = "Robot Simulation";

        boolean HIDE_MOUSE = true;
    
        String MESH_SHADER = resolve(SHADERS, "uni_lighting"); 
    
        String GRID_PATH = resolve(MESHES, "Grid.obj");
            // "./app/src/main/resources/meshes/Grid.obj";

        String SKYBOX_PATH = resolve(SHADERS, "skybox"); 
            // "./app/src/main/resources/shaders/skybox";

        String GRID_SHADER = resolve(SHADERS, "floor");

        // This one works a bit differently but it should be cross platform 
        Path CUBEMAP_BASE = SKYBOX.resolve("skybox");
        String[] CUBEMAP_PATH = { 
            CUBEMAP_BASE.resolve("right.jpg").toString(),
            CUBEMAP_BASE.resolve("left.jpg").toString(),
            CUBEMAP_BASE.resolve("top.jpg").toString(),
            CUBEMAP_BASE.resolve("bottom.jpg").toString(),
            CUBEMAP_BASE.resolve("front.jpg").toString(),
            CUBEMAP_BASE.resolve("back.jpg").toString()
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
        String SWERVE_PATH = resolve(MESHES, "Drivetrain_Assembly.obj"); 
    }

    interface TankDriveSettings {
        String TANK_PATH = resolve(MESHES, "Drivetrain_Assembly.obj");
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