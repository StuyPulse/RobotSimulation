package myrobots.control;

import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.TankDrive;

import org.joml.Vector3f;

import com.stuypulse.stuylib.control.*;

public class PIDBot extends Robot<TankDrive> {

    private PIDController controller;

    private static final double kDt = 0.02;
    
    private double target;
    private double lastError;

    private double kP, kD;

    public PIDBot() {
        super(new TankDrive());

        target = 5;

        kP = 0.5;
        kD = 0.2;
    }
    
    /**
     * This code is run repeatedly
     */
    
    @Override
    protected void execute() {
        final double distance = getPosition().x;
        
        final TankDrive drivetrain = getDrivetrain();

        double error = target - distance;
        double dError = (error - lastError) / kDt;

        drivetrain.arcadeDrive(
            error * kP +
            dError * kD,
            0
        );

        lastError = error;

    }
    
    /**
     * Robot information
     */

    @Override
    public String getAuthor() {
        return "Tahsin & Myles";
    }

    public Vector3f getColor() {
        return new Vector3f(150 / 255.0f, 0 / 255.0f, 3f / 255.0f);
    }

}
