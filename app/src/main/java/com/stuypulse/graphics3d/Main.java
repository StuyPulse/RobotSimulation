package com.stuypulse.graphics3d;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static com.stuypulse.Constants.WindowSettings.*;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    
    public static void main(String... args) {

        // TODO: make glfw window floating (for i3-wm)

        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        final long window = glfwCreateWindow(
            WIDTH, 
            HEIGHT, 
            TITLE, 
            0, 
            0
        );
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        if (window == 0) {
            throw new IllegalStateException("Failed to create window");
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2); 

        glfwShowWindow(window);

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

}
