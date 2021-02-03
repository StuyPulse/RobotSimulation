package myrobots.control;

import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.TankDrive;

import org.joml.Vector3f;

import com.stuypulse.stuylib.control.*;

public class BBBot extends Robot<TankDrive> {

    private double target;

    public BBBot() {
        super(new TankDrive());

        target = 5;
    }
    
    /**
     * This code is run repeatedly
     */
    
    @Override
    protected void execute() {
        final double distance = getPosition().x;
        
        final TankDrive drivetrain = getDrivetrain();

        double error = target - distance;

        double speed;

        if (error > 0) {
            speed = 0.50;
        } else if (error < 0) {
            speed = -0.50;
        } else {
            speed = 0;
        }

        drivetrain.arcadeDrive(speed, 0);

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
