package com.stuypulse.graphics3d;

import static com.stuypulse.Constants.WindowSettings.*;
import static org.lwjgl.glfw.GLFW.*;

import com.stuypulse.graphics3d.math3d.Triangle;
import com.stuypulse.graphics3d.render.Mesh;
import com.stuypulse.graphics3d.render.Shader;
import com.stuypulse.graphics3d.render.Transform;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

import org.joml.Vector3f;


// import static org.lwjgl.opengl.GL11.*;
// import static org.lwjgl.opengl.GL15.*;
// import static org.lwjgl.opengl.GL20.*;
// import static org.lwjgl.opengl.GL30.*;


public class Main {
    
    // Create an array of triangles statically
    // because the mesh cannot be created statically
    // (a singleton function can be used to only initialize a mesh at runtime if it starts out as null)
    // TODO: replace with vertex buffer and using index pools
    public final static Triangle[] CUBE_TRIANGLES = {
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
    };

    public static void main(String... args) throws InterruptedException {
        Window.initialize();

        Window window = new Window(
            TITLE,
            WIDTH,
            HEIGHT
        );

        StopWatch timer = new StopWatch();
        double accumulatedTime = 0.0;

        Mesh CUBE_MESH = new Mesh(CUBE_TRIANGLES);

        Transform cube = new Transform();

        Shader shader = Shader.fromFiles(
            "./app/src/main/resources/basic"
        );

        window.setShader(shader);

        window.getMouse().setVisible(false);

        System.out.println("Starting window...");
        while (window.isOpen()) {
            
            final double dt = timer.reset();
            accumulatedTime += dt;

            // DRAWING LOOP
            window.clear();

            window.draw(CUBE_MESH, cube);

            window.swapBuffers();
            window.pollEvents();

            // KEY INPUT
            final var keys = window.getKeys();
            final var camera = window.getCamera();
            final var mouse = window.getMouse();

            if (keys.hasKey(GLFW_KEY_ESCAPE)) {
                window.close();
            }

            // camera movement
            float yDir = 10 * (float)(dt) *
                ((keys.hasKey(GLFW_KEY_SPACE) ? 1 : 0) - (keys.hasKey(GLFW_KEY_LEFT_SHIFT) ? 1 : 0));
            float zDir = 10 * (float)(dt) * 
                ((keys.hasKey(GLFW_KEY_S) ? 1 : 0) - (keys.hasKey(GLFW_KEY_W) ? 1 : 0));
            float xDir = 10 * (float)(dt) *
                ((keys.hasKey(GLFW_KEY_D) ? 1 : 0) - (keys.hasKey(GLFW_KEY_A) ? 1 : 0));

            Vector3f dir = new Vector3f(xDir, yDir, zDir);
            float yAngle = (float) (camera.getPitch().toRadians());

            camera.setPosition(camera.getPosition().add(dir.rotateY(yAngle)));

            final var delta = mouse.getDelta();
        
            final float sensitivity = 20f;
            final double MAX_ANGLE = Math.PI / 2.0;

            float pitch = -sensitivity * (float)(dt) *
                (delta.x / (WIDTH / 2f));

            float yaw = -sensitivity * (float)(dt) *
                (delta.y / (HEIGHT / 2f));

            camera.setPitch(Angle.fromRadians(camera.getPitch().toRadians() + pitch));
            camera.setYaw(Angle.fromRadians(SLMath.limit(
                camera.getYaw().toRadians() + yaw, 
                -MAX_ANGLE, 
                MAX_ANGLE
            )));

            // TEST OBJECT MOVEMENT
            cube.setX((float) (Math.sin(accumulatedTime) * 3));

            mouse.setPosition(WIDTH / 2, HEIGHT / 2);
            Thread.sleep(20);
        }
        System.out.println("...Closing window");

        Window.terminate();
    }

}
