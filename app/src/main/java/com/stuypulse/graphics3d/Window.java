package com.stuypulse.graphics3d;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.stuypulse.graphics3d.globject.*;

// import java.nio.*;
// import org.lwjgl.system.*;
// import static org.lwjgl.system.MemoryStack.*;

public final class Window implements GlObject{
    
    /**
     * SETUP AND CLEANUP FOR GLFW AND OPENGL
     */
    private static GlObject.Manager MANAGER = new GlObject.Manager();

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

    public static void addObject(GlObject object) {
        MANAGER.addObject(object);
    }
    
    public static void terminate() {

        System.out.println("Attempting to clear GlObject manager...");

        MANAGER.destroy();

        System.out.println("Attempting to terminate LWJGL...");

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * REST OF WINDOW CLASS
     */

    // glfw window pointer
    private final long window;

    // window utils
    private KeyTracker keys;
    private Renderer renderer;

    public Window(String title, int width, int height) {
        Window.addObject(this);

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
        glfwSwapInterval(1); // vsync

        GL.createCapabilities(); // allow opengl functions

        // glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // white
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glfwShowWindow(window);

        // create renderer
        this.renderer = new Renderer();
    }

    public KeyTracker getKeys() {
        return this.keys;
    }

    public Window draw(Mesh... meshes) {
        this.renderer.load(meshes);
        return this;
    }

    public Window setShader(Shader shader) {
        this.renderer.setShader(shader);
        return this;
    }

    /*
    Order is important:
     - check for errors (idk if it even works)
     - glClear(...)
     - DRAW MESHES HERE
     - swap buffers
     - glfwPollEvents() (could technically go anywhere)
     - check for keyinput (could technically go anywhere)
    */
    public void update() {
        int error = -1;
        while ((error = glGetError()) != GL_NO_ERROR) {
            System.out.println("There is an OpenGL error: " + error);
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        this.renderer.unload();

        glfwSwapBuffers(window);

        glfwPollEvents();
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(window);
    }

    public void close() {
        glfwSetWindowShouldClose(window, true);
    }

    public void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }
    
    public int getOrder() {
        return 1_000; // just to be safe
    }

}
