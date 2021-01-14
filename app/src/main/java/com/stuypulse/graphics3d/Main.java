package com.stuypulse.graphics3d;

import static com.stuypulse.Constants.WindowSettings.*;
import static org.lwjgl.glfw.GLFW.*;

import com.stuypulse.graphics3d.globject.Mesh;
import com.stuypulse.graphics3d.globject.Shader;
import com.stuypulse.graphics3d.math3d.Triangle;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.StopWatch;

import org.joml.Vector3f;


// import static org.lwjgl.opengl.GL11.*;
// import static org.lwjgl.opengl.GL15.*;
// import static org.lwjgl.opengl.GL20.*;
// import static org.lwjgl.opengl.GL30.*;


public class Main {
    
    public static void main(String... args) throws InterruptedException {
        Window.initialize();

        Window window = new Window(
            TITLE,
            WIDTH,
            HEIGHT
        );

        StopWatch timer = new StopWatch();

        // TODO: replace with vertex buffer and using index pools
        
        Mesh mesh = new Mesh(
            new Triangle(-0.5f, -0.5f, -0.5f, /**/ -0.5f, 0.5f, -0.5f, /**/ 0.5f, 0.5f, -0.5f),
            new Triangle(-0.5f, -0.5f, -0.5f, /**/ 0.5f, 0.5f, -0.5f, /**/ 0.5f, -0.5f, -0.5f),

            new Triangle(0.5f, -0.5f, -0.5f, /**/ 0.5f, 0.5f, -0.5f, /**/ 0.5f, 0.5f, 0.5f),
            new Triangle(0.5f, -0.5f, -0.5f, /**/ 0.5f, 0.5f, 0.5f, /**/ 0.5f, -0.5f, 0.5f),

            new Triangle(0.5f, -0.5f, 0.5f, /**/ 0.5f, 0.5f, 0.5f, /**/ -0.5f, 0.5f, 0.5f),
            new Triangle(0.5f, -0.5f, 0.5f, /**/ -0.5f, 0.5f, 0.5f, /**/ -0.5f, -0.5f, 0.5f),

            new Triangle(-0.5f, -0.5f, 0.5f, /**/ -0.5f, 0.5f, 0.5f, /**/ -0.5f, 0.5f, -0.5f),
            new Triangle(-0.5f, -0.5f, 0.5f, /**/ -0.5f, 0.5f, -0.5f, /**/ -0.5f, -0.5f, -0.5f),
        
            new Triangle(-0.5f, 0.5f, -0.5f, /**/ -0.5f, 0.5f, 0.5f, /**/ 0.5f, 0.5f, 0.5f),
            new Triangle(-0.5f, 0.5f, -0.5f, /**/ 0.5f, 0.5f, 0.5f, /**/ 0.5f, 0.5f, -0.5f),

            new Triangle(0.5f, -0.5f, 0.5f, /**/ -0.5f, -0.5f, 0.5f, /**/ -0.5f, -0.5f, -0.5f),
            new Triangle(0.5f, -0.5f, 0.5f, /**/ -0.5f, -0.5f, -0.5f, /**/ 0.5f, -0.5f, -0.5f)
        );

        Shader shader = Shader.fromFiles(
            "./app/src/main/resources/basic.vs",
            "./app/src/main/resources/basic.fs"
        );

        window.setShader(shader);

        System.out.println("Starting window...");
        while (window.isOpen()) {
            
            final double dt = timer.reset();

            // DRAW TO THE SCREEN
            window.draw(mesh);
            window.update();

            // KEY INPUT
            final var keys = window.getKeys();
            final var camera = window.getCamera();

            if (keys.hasKey(GLFW_KEY_ESCAPE)) {
                window.close();
            }

            // camera movement
            float zDir = 10 * (float)(dt) * 
                ((keys.hasKey(GLFW_KEY_S) ? 1 : 0) - (keys.hasKey(GLFW_KEY_W) ? 1 : 0));
            camera.setPosition(camera.getPosition().add(new Vector3f(0,0,zDir).rotateY((float) camera.getPitch().toRadians())));

            float pitch = 2 * (float)(dt) *
                ((keys.hasKey(GLFW_KEY_A) ? 1 : 0) - (keys.hasKey(GLFW_KEY_D) ? 1 : 0));
            camera.setPitch(Angle.fromRadians(camera.getPitch().toRadians() + pitch));

            Thread.sleep(20);
        }
        System.out.println("...Closing window");

        Window.terminate();
    }

}
