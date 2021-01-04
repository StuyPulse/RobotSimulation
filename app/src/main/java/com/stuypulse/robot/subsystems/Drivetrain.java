package com.stuypulse.robot.subsystems;

import com.stuypulse.physics.Force;

/**
 * The basic outline for a drivetrain is something that results in a netforce
 * 
 * TankDrive and SwerveDrive both implement this.
 * 
 * This interface does not cover driving controls, and is just for the robot.
 */
public interface Drivetrain {
    Force getNetForce();
}