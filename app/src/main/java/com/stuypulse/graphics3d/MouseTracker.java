package com.stuypulse.graphics3d;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;

public final class MouseTracker {

    private final long window;

    private double lx, ly;

    private final double[] xBuffer;
    private final double[] yBuffer;

    public MouseTracker(long window) {
        this.window = window;

        this.xBuffer = new double[1];
        this.yBuffer = new double[1];

        this.lx = 0.0;
        this.ly = 0.0;
    }

    // Setters
    public void setPosition(int x, int y) {
        glfwSetCursorPos(window, x, y);

        update();
    }

    public void setVisible(boolean isVisible) {
        glfwSetInputMode(window, GLFW_CURSOR, isVisible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
    }

    private void update() {
        this.lx = xBuffer[0];
        this.ly = yBuffer[0];

        glfwGetCursorPos(window, xBuffer, yBuffer);
    }

    // Getters

    public boolean isVisible() {
        return glfwGetInputMode(window, GLFW_CURSOR) == GLFW_CURSOR_NORMAL;
    }

    public Vector2f getPosition() {
        update();

        return new Vector2f(
            (float) xBuffer[0],
            (float) yBuffer[0]
        );
    }

    public Vector2f getDelta() {
        update();

        return new Vector2f(
            (float) (xBuffer[0] - lx), 
            (float) (yBuffer[0] - ly)
        );
    }

}