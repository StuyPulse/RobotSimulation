package com.stuypulse.robot.subsystems;

import com.stuypulse.graphics.Line;
import com.stuypulse.physics.Force;
import com.stuypulse.stuylib.math.Vector2D;

/**
 * The basic outline for a drivetrain is something that results in a netforce
 * 
 * TankDrive and SwerveDrive both implement this.
 * 
 * This interface does not cover driving controls, and is just for the robot.
 */
public interface Drivetrain {
    Force getNetForce();

    default Line[] getMesh() {
        return new Line[] {
            // Box
            new Line(new Vector2D(+1.0, +1.0), new Vector2D(-1.0, +1.0)),
            new Line(new Vector2D(-1.0, +1.0), new Vector2D(-1.0, -1.0)),
            new Line(new Vector2D(-1.0, -1.0), new Vector2D(+1.0, -1.0)),
            new Line(new Vector2D(+1.0, -1.0), new Vector2D(+1.0, +1.0)),
        };
    }

}