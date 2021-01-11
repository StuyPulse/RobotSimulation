package com.stuypulse.graphics3d.math3d;

public final class Vector3D {

    public final double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3D sub(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3D mul(Vector3D other) {
        return new Vector3D(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vector3D div(Vector3D other) {
        return new Vector3D(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public Vector3D add(double other) {
        return new Vector3D(this.x + other, this.y + other, this.z + other);
    }

    public Vector3D sub(double other) {
        return new Vector3D(this.x - other, this.y - other, this.z - other);
    }

    public Vector3D mul(double other) {
        return new Vector3D(this.x * other, this.y * other, this.z * other);
    }

    public Vector3D div(double other) {
        return new Vector3D(this.x / other, this.y / other, this.z / other);
    }

    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y + this.z * z;
    }

    public Vector3D normalize() {
        return this.div(this.magnitude());
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

}