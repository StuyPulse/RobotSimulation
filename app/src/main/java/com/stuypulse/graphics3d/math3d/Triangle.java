package com.stuypulse.graphics3d.math3d;

import org.joml.Vector3f;

public final class Triangle {

    public final Vertex[] vertices;

    public Triangle(Vertex first, Vertex second, Vertex third) {
        this.vertices = new Vertex[] {
            first,
            second,
            third
        };
    }

    public Triangle(
        Vector3f first, Vector3f firstNormal,
        Vector3f second, Vector3f secondNormal,
        Vector3f third, Vector3f thirdNormal
    ) {
        this(
            new Vertex(first, firstNormal), 
            new Vertex(second, secondNormal), 
            new Vertex(third, thirdNormal)
        );
    }

}
