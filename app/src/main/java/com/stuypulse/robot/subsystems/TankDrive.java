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
        leftWheel = new Wheel(new Vector2D(-1.0, 0.0));
        rightWheel = new Wheel(new Vector2D(+1.0, 0.0));
    }

    public void tankDrive(double left, double right) {
        leftWheel.set(left);
        rightWheel.set(right);
    }

    public void arcadeDrive(double speed, double angle) {
        tankDrive(speed + angle, speed - angle);
    }

    public void curvatureDrive(double speed, double angle, boolean useQuickTurn) {
        if(useQuickTurn) {
            arcadeDrive(speed, angle);
        } else {
            arcadeDrive(speed, angle * speed);
        }
    }

    public Force getNetForce() {
        return Force.getNetForce(
            leftWheel.getForce(),
            rightWheel.getForce()
        );
    }

}