package com.stuypulse.graphics3d.render;

import java.util.LinkedList;
import java.util.Queue;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

public final class Renderer {
    
    private Camera camera;

    private Shader shader;

    private final Queue<RenderObject> objects;

    public Renderer() {
        this.camera = new Camera(
            new Vector3f(0,0,3),
            Angle.kZero, Angle.kZero, Angle.kZero,
            640, 640, 
            Angle.k90deg, 
            0.01f, 1000.0f
        );

        this.shader = null;

        this.objects = new LinkedList<>();
    }

    public Camera getCamera() {
        return this.camera;
    }

    public Renderer setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    public Renderer load(RenderObject... newObjects) {
        
        for (RenderObject m : newObjects) {
            this.objects.add(m);
        }
        return this;
        
    }

    public void unload() {

        if (shader != null) {
            shader.use();
            shader.useCamera(this.camera);
        }

        RenderObject head = null;
        while ((head = this.objects.poll()) != null) {
            shader.useTransform(head.getTransform());
            head.getMesh().draw();
        }
        
    }

}
