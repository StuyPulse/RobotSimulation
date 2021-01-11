package com.stuypulse.graphics3d.math3d;

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

    public final Vector3D[] points;

    public Triangle(Vector3D first, Vector3D second, Vector3D third) {
        this.points = new Vector3D[] {
            first,
            second,
            third
        };
    }

    public Triangle(
        double a, double b, double c,
        double d, double e, double f,
        double g, double h, double i
    ) {
        this(
            new Vector3D(a, b, c),
            new Vector3D(d, e, f),
            new Vector3D(g, h, i)
        );
    }

}
