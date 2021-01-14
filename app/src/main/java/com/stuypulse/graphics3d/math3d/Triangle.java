package com.stuypulse.graphics3d.math3d;

import org.joml.Vector3f;

public final class Triangle {

    /*
     * TODO: Add color
     * 
     * add attrib point in mesh
     * 
     * public static final Vector3D getColor(float r, float g, float b) { return new
     * Vector3D( SLMath.limit(r, 0.0, 1.0), SLMath.limit(g, 0.0, 1.0),
     * SLMath.limit(b, 0.0, 1.0) ); }
     * 
     * public final Vector3D color;
     */

    public final Vector3f[] points;

    public Triangle(Vector3f first, Vector3f second, Vector3f third) {
        this.points = new Vector3f[] {
            first,
            second,
            third
        };
    }

    public Triangle(
        float a, float b, float c,
        float d, float e, float f,
        float g, float h, float i
    ) {
        this(
            new Vector3f(a, b, c),
            new Vector3f(d, e, f),
            new Vector3f(g, h, i)
        );
    }

}
