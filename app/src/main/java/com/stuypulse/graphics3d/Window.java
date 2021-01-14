package com.stuypulse.graphics3d;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.*;

import com.stuypulse.graphics3d.globject.*;

public class Window implements GlObject{
    
    /******************************
     * MEMORY AND GLFW MANAGEMENT *
     ******************************/

    private static List<GlObject> MANAGER = new LinkedList<>();

    public static void initialize() {
        System.out.println("LWJGL " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Could not initialize GLFW!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    }

    public static void addObject(GlObject object) {
        MANAGER.add(object);
    }
    
    public static void terminate() {
        System.out.println("Attempting to free GlObjects...");

        Collections.sort(MANAGER);
        for (GlObject obj : MANAGER) {
            obj.destroy();
        }

        System.out.println("Attempting to terminate LWJGL...");

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /****************************
     * REST OF THE WINDOW CLASS *
     ****************************/

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

        glEnable(GL_DEPTH_TEST);

        // create renderer
        this.renderer = new Renderer();
    }

    public KeyTracker getKeys() {
        return this.keys;
    }

    public Camera getCamera() {
        return this.renderer.getCamera();
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
