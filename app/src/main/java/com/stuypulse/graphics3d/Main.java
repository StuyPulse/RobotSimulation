package com.stuypulse.graphics3d;

import static com.stuypulse.Constants.WindowSettings.*;
import static org.lwjgl.glfw.GLFW.*;

import com.stuypulse.graphics3d.globject.*;
import com.stuypulse.graphics3d.math3d.*;


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

        Mesh mesh = new Mesh(
            new Triangle(
                -1.0, -1.0, +0.0, 
                -1.0, +1.0, +0.0, 
                +1.0, +1.0, +0.0
            )
        );

        window.setShader(Shader.fromFiles(
            "./app/src/main/resources/basic.vs",
            "./app/src/main/resources/basic.fs"
        ));

        // Shader shader = Shader.fromFiles(
        //     "./app/src/main/resources/basic.vs",
        //     "./app/src/main/resources/basic.fs"
        // );

        System.out.println("Starting window...");

        while (window.isOpen()) {
            
            // DRAW TO THE SCREEN
            window.draw(mesh);
            window.update();

            // KEY INPUT
            final var keys = window.getKeys();

            if (keys.hasKey(GLFW_KEY_ESCAPE)) {
                window.close();
            }

            Thread.sleep(20);
        }
        
        System.out.println("...Closing window");

        Window.terminate();
    }

}
