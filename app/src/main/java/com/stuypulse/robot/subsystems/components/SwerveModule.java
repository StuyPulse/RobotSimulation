package com.stuypulse.robot.subsystems.components;

import com.stuypulse.stuylib.math.*;

/**
 * This class handles the setting the speed and direction of each wheel
 * It also has to handle normalization since thats an important part of SwerveDrive
 */
public class SwerveModule extends Wheel {
    private double speed;
    private Angle angle;

    public SwerveModule(Vector2D position) {
        super(position);
    }

    /**
     * Sets up the module to drive with certain instructions
     */
    public double point(Vector2D direction, double turn) {
        // Make sure the controls are in bounds
        direction = direction.div(Math.max(1.0, direction.magnitude()));
        turn = SLMath.limit(turn);

        // Calculate the vector the wheel should follow
        Vector2D perpendicular = getPosition().normalize().rotate(Angle.k90deg);
        Vector2D wheelVector = direction.add(perpendicular.mul(turn));

        // Angle and Speed the wheel should go at to follow the vector
        angle = wheelVector.getAngle();
        speed = wheelVector.magnitude() * angle.sub(getAngle()).cos();

        // Return the speed that it will go, used for normalization later
        return speed;
    }

    /**
     * Modifies the drive instructions to be normalized
     * This prevents distortion
     */
    public void normalize(double maxSpeed) {
        speed /= maxSpeed;
    }

    /**
     * Send the drive instructions to the wheel
     */
    public void drive() {
        set(speed);
        setAngle(angle);
    }
}