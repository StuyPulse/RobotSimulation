package com.stuypulse.physics;

import com.stuypulse.stuylib.math.*;

/**
 * This class represents the position and the angle of an object.
 * You can apply a velocity over time to this object to get a new position.
 */
public final class Position {

    public final Vector2D position;
    public final Angle angle;

    public Position(Vector2D pos, Angle ang) {
        position = pos;
        angle = ang;
    }

    public Position(Vector2D pos) {
        this(pos, Angle.kZero);
    }

    public Position() {
        this(new Vector2D(0,0));
    }

    public Position update(Velocity vel, double dt) {
        Vector2D newPosition = this.position.add(vel.positional.mul(dt));
        Angle newAngle = this.angle.add(Angle.fromRadians(vel.angular * dt));
        return new Position(
            newPosition, 
            newAngle     
        );
    }

	@Override
	public String toString() {
		return "Position [angle=" + angle + ", position=" + position + "]";
	}

}