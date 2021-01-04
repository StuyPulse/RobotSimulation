package com.stuypulse.physics;

import com.stuypulse.stuylib.math.*;

import com.stuypulse.Constants.Physics;

/**
 * This class represents the velocity of an object at any point in time.
 * It stores the velocity vector and the angular velocity, and can be used
 * to update the position class. This class can also be updated by a force object.
 */
public final class Velocity {

    public final Vector2D positional;
    public final double angular;

    public Velocity(Vector2D pos, double ang) {
        positional = pos;
        angular = ang;
    }

    public Velocity(Vector2D pos) {
        this(pos, 0.0);
    }

    public Velocity() {
        this(new Vector2D(0,0));
    }

    public Velocity update(Force force, double dt) {
        Vector2D newPositional = this.positional.add(force.getForce().mul(dt));
        double newAngular = this.angular + force.getTorque() * dt;
   
        double a = Physics.DRAG_RC / (dt + Physics.DRAG_RC);

        return new Velocity(
            newPositional.mul(a) , 
            newAngular * a
        );
    }

	@Override
	public String toString() {
		return "Velocity [angular=" + angular + ", positional=" + positional + "]";
	}

}