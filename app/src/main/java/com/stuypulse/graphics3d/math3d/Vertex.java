package com.stuypulse.graphics3d.math3d;

import org.joml.Vector3f;

public final class Vertex {

    private Vector3f position;
    private Vector3f normal;

    public Vertex(Vector3f position, Vector3f normal) {
        this.position = position;
        this.normal = normal;
    }

    public Vertex(float x, float y, float z, float ux, float uy, float uz) {
        this(new Vector3f(x, y, z), new Vector3f(ux, uy, uz));
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getNormal() {
        return this.normal;
    }

}
