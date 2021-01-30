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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((normal == null) ? 0 : normal.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (normal == null) {
            if (other.normal != null)
                return false;
        } else if (!normal.equals(other.normal))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

}
