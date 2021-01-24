package com.stuypulse.graphics;

import com.stuypulse.graphics3d.render.Mesh;
import com.stuypulse.graphics3d.render.Transform;

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

    public Transform getTransform() {
        return transform;
    }

}
