package com.stuypulse.graphics3d;

import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.*;

import com.stuypulse.graphics3d.render.*;
import com.stuypulse.stuylib.math.Angle;

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
    private MouseTracker mouse;
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

        // create mouse
        this.mouse = new MouseTracker(this.window);

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
        glEnable(GL_CULL_FACE);
        // glEnable(GL_BLEND);
        
        // glDepthFunc(GL_LESS); // <-- default

        // create renderer
        this.renderer = new Renderer(new Camera(
            new Vector3f(0),
            Angle.kZero, Angle.kZero, Angle.kZero,
            width, height,
            Angle.k90deg, 
            0.01f, 
            1000.0f
        ));
    }

    /***********
     * GETTERS *
     ***********/

    public KeyTracker getKeys() {
        return this.keys;
    }

    public MouseTracker getMouse() {
        return this.mouse;
    }

    public Camera getCamera() {
        return this.renderer.getCamera();
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(window);
    }

    /*********************
     * WINDOW OPERATIONS *
     *********************/

    public Window setShader(Shader shader) {
        this.renderer.setShader(shader);
        return this;
    }

    public void close() {
        glfwSetWindowShouldClose(window, true);
    }

    public void pollErrors() {
        int error = -1;
        while ((error = glGetError()) != GL_NO_ERROR) {
            throw new IllegalStateException("There is an OpenGL error: " + error);
        }
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void clear(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
        this.clear();
    }

    public void clear(float r, float g, float b) {
        this.clear(r, g, b, 1);
    }

    public Window draw(Mesh mesh, Transform transform, Vector3f color) {
        // I dont like the fact that it's necessary 
        // to update a camera uniform on every draw

        this.renderer.setColor(color.x, color.y, color.z);
        this.renderer.updateCamera();
        this.renderer.setTransform(transform);
        this.renderer.draw(mesh);
        return this;
    }

    public void swapBuffers() {
        glfwSwapBuffers(this.window);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    /*********************
     * MEMORY MANAGEMENT *
     *********************/

    public void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }
    
    public int getOrder() {
        return 1_000; // just to be safe
    }

}
