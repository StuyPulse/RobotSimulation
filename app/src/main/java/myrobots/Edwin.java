package myrobots;

/*** VERY VERY IMPORTANT THAT YOU IMPORT THESE ***/
import java.awt.Color;
import com.stuypulse.stuylib.math.*;
import com.stuypulse.stuylib.control.*;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.*;
/*************************************************/

/**
 * This robot is here to show you what an example implementation
 * of a robot would look like. Be careful not to miss the imports,
 * and also checkout the robot class to see some functions you can use
 */
public class Edwin extends Robot<TankDrive> {

    public Edwin() {
        // Make sure to pass in an instance of your drivetrain to 
        // the parent class so it can use it in the simulation
        super(new TankDrive());
    }

    public String getAuthor() {
        return "Sam B";
    }

    public void execute() {
        // Get information about the drivetrain;
        Vector2D position = getPosition();
        Angle angle = getAngle();

        if(position.y < 5) {
            getDrivetrain().arcadeDrive(0.2, 0);
        } else {
            getDrivetrain().arcadeDrive(-0.2, 0);
        }
    }

    public Color getColor() {
        return Color.RED;
    }

}