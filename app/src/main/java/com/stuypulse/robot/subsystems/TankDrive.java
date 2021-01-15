package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.math.*;
import com.stuypulse.physics.Force;
import com.stuypulse.robot.subsystems.components.Wheel;

/**
 * This is an implementation of TankDrive or DifferentialDrive for this simulation.
 * 
 * It has the classic .tankDrive(), .arcadeDrive(), and .curvatureDrive() that you
 * would come to expect.
 */
public class TankDrive implements Drivetrain {

    private final Wheel leftWheel;
    private final Wheel rightWheel;
    
    public TankDrive() {
        leftWheel = new Wheel(new Vector2D(0.0, 1.0));
        rightWheel = new Wheel(new Vector2D(0.0, -1.0));
    }

    /**
     * Control the left and right side of the robot
     * @param left the speed of the left wheel [-1...+1]
     * @param right the speed of the right wheel [-1...+1]
     */
    public void tankDrive(double left, double right) {
        leftWheel.set(left);
        rightWheel.set(right);
    }

    /**
     * Control the robot using a speed and a turn amount
     * @param speed speed that the robot will go [-1...+1]
     * @param angle amount it should turn [-1...+1]
     */
    public void arcadeDrive(double speed, double angle) {
        tankDrive(speed + angle, speed - angle);
    }

    /**
     * Unrelated function for physics calculation
     */
    public Force getNetForce() {
        return Force.getNetForce(
            leftWheel.getForce(),
            rightWheel.getForce()
        );
    }

    /**
     * Relates to how the mesh should rotate around the origin or
     * itself
     */
    public boolean isCentered() {
        return false;
    }

}