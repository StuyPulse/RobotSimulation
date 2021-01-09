package com.stuypulse.graphics3d;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import com.stuypulse.Constants;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

// import java.nio.*;
// import org.lwjgl.system.*;
// import static org.lwjgl.system.MemoryStack.*;

public final class Window {
    
    /**
     * SETUP AND CLEANUP FOR GLFW AND OPENGL
     */
    public static void initialize() {
        // often suggested to run this from the main thread
        System.out.println("LWJGL " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Could not initialize GLFW!");
        }

        glfwDefaultWindowHints(); // the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // don't become visible until done configuring
        //glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // resizable
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // resizable
    }

    public static void terminate() {
        System.out.println("Attempting to terminate LWJGL");

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /*
    Maybe: 
    have a static list of windows that are handled by Window.terminate()
    */

    /*
    // is this a good idea
    static {
        initialize();
    }
    */

    /**
     * REST OF WINDOW CLASS
     */

    // glfw window pointer
    private final long window;

    // window utils
    private KeyTracker keys;

    public Window(String title, int width, int height) {

        // create window
        this.window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // create key callback
        this.keys = new KeyTracker(this.window);
        glfwSetKeyCallback(this.window, this.keys);

        // center window 
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        // make window context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    }

    public void update() {
        if (keys.hasKey(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window, true);
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(window);

        glfwPollEvents();
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(window);
    }

    public void close() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

}
