package com.stuypulse.graphics3d.globject;

import java.util.LinkedList;
import java.util.Queue;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

public final class Renderer {
    
    private Camera camera;

    private Shader shader;

    private final Queue<Mesh> meshes;

    public Renderer() {
        this.camera = new Camera(
            new Vector3f(0,0,3),
            Angle.kZero, Angle.kZero, Angle.kZero,
            640, 640, 
            Angle.k90deg, 
            0.01f, 1000.0f
        );

        this.shader = null;

        this.meshes = new LinkedList<>();
    }

    public Camera getCamera() {
        return this.camera;
    }

    public Renderer setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    public Renderer load(Mesh... newMeshes) {
        
        for (Mesh m : newMeshes) {
            this.meshes.add(m);
        }
        return this;
        
    }

    public void unload() {

        if (shader != null) {
            shader.use();
            shader.useCamera(this.camera);
            shader.useTransform(/**/);
        }

        Mesh head = null;
        while ((head = this.meshes.poll()) != null) {
            head.draw();
        }
        
    }

}
