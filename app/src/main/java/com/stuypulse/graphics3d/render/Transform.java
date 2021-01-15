package com.stuypulse.graphics3d.render;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Matrix4f;
import org.joml.Vector3f;

// Contains all the valid transformations 
// that can be applied to a  mesh
public final class Transform {
    
    private Vector3f translation;
    private Angle pitch, yaw, roll; 

    private Vector3f scale; 

    private Matrix4f transform;

    private boolean centered;

    public Transform(
        Vector3f translation,
        Angle pitch, Angle yaw, Angle roll
    ) {
        this.translation =  translation;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;

        this.scale = new Vector3f(1f);

        this.transform = null;
        
        this.centered = false;
    }

    public Transform(
        Vector3f translation,
        Angle yaw
    ) {
        this(translation, Angle.kZero, yaw, Angle.kZero);
    }

    public Transform(Vector3f translation)  {
        this(translation, Angle.kZero);
    }

    public Transform() {
        this(new Vector3f(0));
    }

    private Matrix4f calculateTransform() {
        if (centered) {
            return new Matrix4f()
                .translate(translation)
                .scale(scale)
                .rotate((float) yaw.toRadians(), 1, 0, 0)
                .rotate((float) pitch.toRadians(), 0, 1, 0)
                .rotate((float) roll.toRadians(), 0, 0, 1);
        } else {
            return new Matrix4f()
                .rotate((float) yaw.toRadians(), 1, 0, 0)
                .rotate((float) pitch.toRadians(), 0, 1, 0)
                .rotate((float) roll.toRadians(), 0, 0, 1)
                .translate(translation)
                .scale(scale);
        }
    }

    protected Matrix4f getTransform() {
        return transform != null ?
            transform :
            (transform = calculateTransform());
    }

    // Setters
    public Transform setTranslation(Vector3f translation) {
        this.translation = translation;
        this.transform = null;
        return this;
    }
    
    public Transform setScale(Vector3f scale) {
        this.scale = scale;
        this.transform = null;
        return this;
    }

    public Transform setX(float x) {
        this.translation.x = x;
        this.transform = null;
        return this;
    }

    public Transform setY(float y) {
        this.translation.y = y;
        this.transform = null;
        return this;
    }

    public Transform setZ(float z) {
        this.translation.z = z;
        this.transform = null;
        return this;
    }

    public Transform setPitch(Angle pitch) {
        this.pitch = pitch;
        this.transform = null;
        return this;
    }

    public Transform setYaw(Angle yaw) {
        this.yaw = yaw;
        this.transform = null;
        return this;
    }

    public Transform setRoll(Angle roll) {
        this.roll = roll;
        this.transform = null;
        return this;
    }

    public Transform setCentered(boolean isCentered) {
        this.centered = isCentered;
        this.transform = null;
        return this;
    }

    // Getters
    public Vector3f getTranslation() {
        return translation;
    }

    public float getX() {
        return translation.x;
    }

    public float getY() {
        return translation.y;
    }

    public float getZ() {
        return translation.z;
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

    public Vector3f getScale() {
        return this.scale;
    }

    public float getScaleX() {
        return this.scale.x;
    }

    public float getScaleY() {
        return this.scale.y;
    }

    public float getScaleZ() {
        return this.scale.z;
    }

    public boolean isCentered() {
        return centered;
    }

}
