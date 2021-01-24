package com.stuypulse.robot;

/*** VERY VERY IMPORTANT THAT YOU IMPORT THESE ***/
import java.awt.Color;
import com.stuypulse.stuylib.math.*;
import com.stuypulse.stuylib.control.*;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.*;
/*************************************************/

import com.stuypulse.physics.State;
import com.stuypulse.physics.Position;
import com.stuypulse.stuylib.util.StopWatch;

import org.joml.Vector3f;

/**
 * This is the robot class that you will be extending when making your
 * own robot. It is relatively simple, and supports different types of drivetrain.
 */
public abstract class Robot<DT extends Drivetrain> {

    /***************************************/
    /*** ABSTRACT FUNCTIONS TO IMPLEMENT ***/
    /***************************************/
 
    /**
     * Return the name of who made this robot
     * 
     * @return the name of the author
     */
    public abstract String getAuthor();

    /**
     * This function gets called every cycle of the robot,
     * any algorithms that you create for the robot will
     * be defined in here.
     */
    protected abstract void execute(); 

    /**
     * Get the color that you want the robot to be
     * 
     * @return the color you want the robot to be
     */
    public Vector3f getColor() {
        return new Vector3f(1,1,1);
    }

    /**********************************/
    /*** Important functions to use ***/
    /**********************************/
    
    /**
     * @return gets the current coordinates of the robot as a vector
     */
    public final Vector2D getPosition() {
        return state.getPosition().position;
    }

    /**
     * @return gets the current velocity of the robot as a vector
     */
    public final Vector2D getVelocity() {
        return state.getVelocity().positional;
    }

    /**
     * @return gets the current direction of the robot as an angle class (just use .toDegrees())
     */
    public final Angle getAngle() {
        return state.getPosition().angle;
    }

    /**
     * @return the current drivetrain that the robot is using
     */
    public final DT getDrivetrain() {
        return drivetrain;
    }

    /*************************/
    /*** Rest of the class ***/
    /*************************/

    private StopWatch timer; // Used to time updates (physics)
    private DT drivetrain; // Used to drive around the robot
    private State state; // Used to calculate the physics of the robot

    /**
     * Initializes a robot with a drivetrain
     * 
     * @param drivetrain an instance of the drivetrain you are using
     */
    public Robot(DT drivetrain) {
        this.drivetrain = drivetrain;
        this.timer = new StopWatch();

        // TODO: fix this, make it more automatic
        Vector2D pos = new Vector2D(0, 0);
        this.state = new State(new Position(pos));
    }

    /**
     * This part of the code is executed every cycle,
     * it handles physics and the user code.
     */
    public final void periodic() {
        final double dt = timer.reset();
        state.update(drivetrain.getNetForce(), dt);
        execute();
    }

    /**
     * @return The authors name and the name of the robot
     */
    public final String toString() {
        return this.getAuthor() + "'s " + this.getClass().getSimpleName();
    }
}