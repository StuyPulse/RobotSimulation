package myrobots.control;

import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.TankDrive;

import org.joml.Vector3f;

import com.stuypulse.stuylib.control.*;

public class DRBot extends Robot<TankDrive> {

    private double target;

    public DRBot() {
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

        if (distance < target) {
            drivetrain.arcadeDrive(1.0, 0.0);
        } else {
            drivetrain.arcadeDrive(0.0, 0.0);
        }

    }
    
    /**
     * Robot information
     */

    public String getAuthor() {
        return "";
    }

}
