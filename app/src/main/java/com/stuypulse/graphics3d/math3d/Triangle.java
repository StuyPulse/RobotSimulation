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

    // public Triangle(Vector3f first, Vector3f second, Vector3f third) {
    //     this(
    //         new Vertex(first), 
    //         new Vertex(second), 
    //         new Vertex(third)
    //     );
    // }

    // public Triangle(
    //     float a, float b, float c,
    //     float d, float e, float f,
    //     float g, float h, float i
    // ) {
    //     this(
    //         new Vertex(a, b, c),
    //         new Vertex(d, e, f),
    //         new Vertex(g, h, i)
    //     );
    // }

}
