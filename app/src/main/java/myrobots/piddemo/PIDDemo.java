package myrobots.piddemo;

import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.TankDrive;

import org.joml.Vector3f;

public class PIDDemo extends Robot<TankDrive> {

    public PIDDemo() {
        super(new TankDrive());
    }
    
    /**
     * This code is run repeatedly
     */
    
    private double previousError = 0;
    private double dt = 0.02;

    @Override
    protected void execute() {
        final double target = 5;
        final double x = getPosition().x;
        
        final TankDrive drivetrain = getDrivetrain();

        double error = target - x;
        double kP = 0.5;//0.1;

        double dError = (error - previousError) / dt;
        double kD = 0.2;
        
        // if (error > 0)
        // // NOTE: driving like this increases the x-position positively
        //     drivetrain.arcadeDrive(0.5, 0);
        // else if (error < 0)
        //     drivetrain.arcadeDrive(-0.5, 0);
        // else
        //     drivetrain.arcadeDrive(0.0, 0);  
        

        // pid algorithm implemented here
        drivetrain.arcadeDrive(
            error * kP +
            dError * kD,
            0
        );

        previousError = error;
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























