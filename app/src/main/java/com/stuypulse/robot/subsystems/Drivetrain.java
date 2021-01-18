package com.stuypulse.robot.subsystems;

import com.stuypulse.graphics.MeshLoader;
import com.stuypulse.graphics3d.render.Mesh;
import com.stuypulse.physics.Force;

import org.joml.Vector4f;

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

    default Vector4f getColor() {
        return new Vector4f(0, 0.23f, 0, 1);
    }

    default boolean isCentered() {
        return true;
    }

}