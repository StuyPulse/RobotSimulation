package com.stuypulse.physics;

/**
 * This class basically combines the position and the velocity classes into one.
 * 
 * This way, it makes it really easy to apply force to a state and track an object.
 */
public final class State {

    private Position pos;
    private Velocity vel;

    public State(Position pos, Velocity vel) {
        this.pos = pos;
        this.vel = vel;
    }
    
    public State(Position pos) {
        this(pos, new Velocity());
    }

    public State() {
        this(new Position());
    }
    
    public Position getPosition() {
        return pos;
    }

    public Velocity getVelocity() {
        return vel;
    }
    
    public void update(Force force, double dt) {
        this.vel = this.vel.update(force.rotate(pos.angle), dt);
        this.pos = this.pos.update(this.vel, dt);
    }

	@Override
	public String toString() {
		return "State [pos=" + pos + ", vel=" + vel + "]";
	}

}