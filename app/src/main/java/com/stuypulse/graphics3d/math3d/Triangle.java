package com.stuypulse.graphics3d.math3d;

public final class Triangle {

    public final Vertex[] vertices;

    public Triangle(Vertex first, Vertex second, Vertex third) {
        this.vertices = new Vertex[] {
            first,
            second,
            third
        };
    }

}
