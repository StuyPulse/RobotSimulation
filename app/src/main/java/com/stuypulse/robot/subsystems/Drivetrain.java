package com.stuypulse.robot.subsystems;

import com.stuypulse.graphics.MeshLoader;
import com.stuypulse.graphics3d.render.Mesh;
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

    /*************
     * RENDERING *
     *************/

    default Mesh getMesh() {
        return MeshLoader.getDefaultMesh();
    }

    default boolean isCentered() {
        return true;
    }

}