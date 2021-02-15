package com.stuypulse.graphics3d.render;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class Camera {
    
    // View data
    private Matrix4f view; 
    
    // add this to constructor later
    private Vector3f position;
    private Angle pitch, yaw, roll; 

    // Projection data
    private Matrix4f projection;

    private int width, height;
    private float near, far;

    private Angle fov;

    // Full constructor
    public Camera(
        Vector3f position,
        Angle pitch, Angle yaw, Angle roll,
        int width,
        int height, 
        Angle fov, 
        float near, 
        float far
    ) {
        // Projection
        this.width = width;
        this.height = height;
        this.fov = fov;
        this.near = near; 
        this.far = far;

        this.projection = null;

        // View
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;

        this.view = null;

    }

    // View getters and setters
    protected Matrix4f getView() {
        
        return view != null ? 
            view :
            (view = new Matrix4f()
                .rotate((float) -yaw.toRadians(), 1, 0, 0)
                .rotate((float) -pitch.toRadians(), 0, 1, 0)
                .rotate((float) -roll.toRadians(), 0, 0, 1)
                .translate(new Vector3f(0).sub(position))
            );
        
    }

    public Camera setPosition(Vector3f in) {
        this.position = in;
        this.view = null;
        return this;
    }

    public Camera setYaw(Angle yaw) {
        this.yaw = yaw;
        this.view = null;
        return this;
    }

    public Camera setPitch(Angle pitch) {
        this.pitch = pitch;
        this.view = null;
        return this;
    }

    public Camera setRoll(Angle roll) {
        this.roll = roll;
        this.view = null;
        return this;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Angle getPitch() {
        return pitch;
    }

    public Angle getYaw() {
        return yaw;
    }

    public Angle getRoll() {
        return roll;
    }

    // Projection Getters & Setters    
    protected Matrix4f getProjection() {

        return projection != null ?
            projection :
            (projection = new Matrix4f().setPerspective(
                (float) fov.toDegrees(),
                (width / (float) height),
                near,
                far
            ));
    
    }

    public Camera setWidth(int width) {
        this.width = width;
        this.projection = null;
        return this;
    }

    public Camera setHeight(int height) {
        this.height = height;
        this.projection = null;
        return this;
    }

    public Camera setNearPlane(float near) {
        this.near = near;
        this.projection = null;
        return this;
    }

    public Camera setFarPlane(float far) {
        this.far = far;
        this.projection = null;
        return this;
    }

    public Camera setFov(Angle fov) {
        this.fov = fov;
        this.projection = null;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getNearPlane() {
        return near;
    }


    public double getFarPlane() {
        return far;
    }

    public Angle getFov() {
        return fov;
    }

}
