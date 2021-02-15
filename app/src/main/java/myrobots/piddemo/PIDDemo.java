package myrobots.piddemo;

import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.TankDrive;

import org.joml.Vector3f;

import com.stuypulse.stuylib.control.*;

public class PIDDemo extends Robot<TankDrive> {

    private PIDController controller;

    public PIDDemo() {
        super(new TankDrive());

        controller = new PIDController(0.5, 0, 0.2);
    }
    
    /**
     * This code is run repeatedly
     */
    
    @Override
    protected void execute() {
        final double target = 5;
        final double x = getPosition().x;
        
        final TankDrive drivetrain = getDrivetrain();

        // pid algorithm implemented here
        drivetrain.arcadeDrive(
            controller.update(target, x),
            0
        );
    }
    
    /**
     * Robot information
     */

    @Override
    public String getAuthor() {
        return "Tahsin & Myles";
    }

    public Vector3f getColor() {
        return new Vector3f(252f / 255.0f, 183f / 255.0f, 3f / 255.0f);
    }

}
