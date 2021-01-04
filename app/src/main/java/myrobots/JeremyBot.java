package myrobots;

/*** VERY VERY IMPORTANT THAT YOU IMPORT THESE ***/
import java.awt.Color;
import com.stuypulse.stuylib.math.*;
import com.stuypulse.stuylib.control.*;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.subsystems.components.Wheel;
import com.stuypulse.robot.*;
/*************************************************/

/**
 * This robot is here to show you what an example implementation
 * of a robot would look like. Be careful not to miss the imports,
 * and also checkout the robot class to see some functions you can use
 */
public class JeremyBot extends Robot<SwerveDrive> {

    public JeremyBot() {
        // Make sure to pass in an instance of your drivetrain to 
        // the parent class so it can use it in the simulation
        super(new SwerveDrive(new Vector2D(2.0, 2.0)));
    }

    public String getAuthor() {
        return "Myles P";
    }

    public void execute() {
        // Get information about the drivetrain;
        Vector2D position = getPosition();
        Angle angle = getAngle();

        Vector2D direction = new Vector2D(0.0, 0.0);

        if(position.y < 5) {
            direction = new Vector2D(0.0, 1.0);    
        } else {
            if(position.x < 5) {
                direction = new Vector2D(1.0, 0.0);
            } else {
                direction = new Vector2D(1.0, 1.0);
            }
        }
        
        
        // Makes the control field centric
        direction = direction.rotate(Angle.kZero.sub(angle));

        getDrivetrain().swerveDrive(direction, 0.0);
    }

    public Color getColor() {
        return Color.RED;
    }

}