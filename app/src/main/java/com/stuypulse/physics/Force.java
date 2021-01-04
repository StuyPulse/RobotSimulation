package com.stuypulse.physics;

import com.stuypulse.stuylib.math.*;

/**
 * This class represents a force. It is basically a vector and torque being 
 * applied to an object. It uses factories as there are a couple different ways
 * to create a force object, and they do very different things.
 */
public class Force {

    public static Force getForce(Vector2D force, double torque) {
        return new Force(force, torque);
    }

    public static Force getForce(Vector2D force) {
        return new Force(force, 0.0);
    }

    public static Force getPointForce(Vector2D offset, Vector2D force) {
        Angle diff = force.getAngle().sub(offset.getAngle());
        return new Force(
            force, 
            offset.magnitude() * force.magnitude() * diff.sin()
        );
    }

    public static Force getNetForce(Force... forces) {
        Vector2D force = new Vector2D(0,0);
        double torque = 0.0;
        
        for(Force f : forces) {
            force = force.add(f.getForce());
            torque += f.getTorque();
        }

        return new Force(force, torque);
    }
    
    private final Vector2D force;
    private final double torque;

    private Force(Vector2D force, double torque) {
        this.force = force;
        this.torque = torque;
    }
    
    public Vector2D getForce() {
        return force;
    }

    public double getTorque() {
        return torque;
    }

    public Force rotate(Angle ang) {
        return new Force(this.force.rotate(ang), this.torque);
    }

	@Override
	public String toString() {
		return "Force [force=" + force + ", torque=" + torque + "]";
	}
    
}