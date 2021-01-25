package com.stuypulse.robot.subsystems;

import com.stuypulse.graphics.RenderObject;
import com.stuypulse.physics.Force;

import java.util.List;

/**
 * The basic outline for a drivetrain is something that results in a netforce
 * 
 * TankDrive and SwerveDrive both implement this.
 * 
 * This interface does not cover driving controls, and is just for the robot.
 */
public interface Drivetrain {

    /***********
     * PHYSICS *
     ***********/

    Force getNetForce();

    /*************
     * RENDERING *
     *************/

    /**
     * Returns a list describing how to render a drivetrain. Having
     * a list allows for creating multipart drivetrains
     * 
     * @return list of meshes and transforms.
     */
    List<RenderObject> getRenderable();

    /**
     * Describes how the drivetrain should be rotated
     * 
     * if true, the robot mesh is rotated around itself
     * if false, the robot mesh is rotated around the origin 
     * 
     * 
     * @return true or false value
     */
    default boolean isCentered() {
        return true;
    }

}