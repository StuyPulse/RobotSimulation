package com.stuypulse.graphics3d;

import java.util.LinkedList;
import java.util.Queue;

public final class Renderer {
    
    /*
    TODO: incorporate a Shader into the renderer
    */

    // private Shader shader;

    private final Queue<Mesh> meshes;

    public Renderer() {
        this.meshes = new LinkedList<>();
    }

    // public Renderer useShader(Shader shader) {
    //     this.shader = shader;
    //     return this;
    // }

    public Renderer load(Mesh... newMeshes) {
        
        for (Mesh m : newMeshes) {
            this.meshes.add(m);
        }
        return this;
        
    }

    public void unload() {

        // if (shader != null)
            // shader.use();

        Mesh head = null;
        while ((head = this.meshes.poll()) != null) {
            head.draw();
        }
        
    }

}
