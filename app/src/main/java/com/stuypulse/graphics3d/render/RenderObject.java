package com.stuypulse.graphics3d.render;

public final class RenderObject {
    
    private Mesh mesh;
    private Transform transform;

    public RenderObject(Mesh mesh, Transform transform) {
        this.mesh = mesh;
        this.transform = transform; 
    }

    public RenderObject(Mesh mesh) {
        this(mesh, new Transform());
    }

    public Mesh getMesh() {
        return mesh;
    }

    public RenderObject setMesh(Mesh mesh) {
        this.mesh = mesh;
        return this;
    }

    public Transform getTransform() {
        return transform;
    }

    public RenderObject setTransform(Transform transform) {
        this.transform = transform;
        return this;
    }
    
}
