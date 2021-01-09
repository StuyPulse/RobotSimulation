package com.stuypulse.graphics3d;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Set;
import java.util.HashSet;

public final class KeyTracker implements GLFWKeyCallbackI {

    private final long window;

    private final Set<Integer> keys;

    public KeyTracker(long window) {
        this.window = window;
        this.keys = new HashSet<>();
    }

    public boolean hasKey(int key) {
        return this.keys.contains(key);
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (window != this.window)
            return;
        
        if (action == GLFW_RELEASE) {
            this.keys.remove(key);
        } else {
            // here action is GLFW_PRESS OR _REPEAT,
            // these are not differentiated in the code because _REPEAT
            // is not a good indication of held (which is more useful to have)
            this.keys.add(key);
        }
    }
    
}
