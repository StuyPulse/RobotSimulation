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
        super(new SwerveDrive(new Vector2D(5.0, 2.0)));
    }

    public String getAuthor() {
        return "Myles P";
    }

    public void execute() {
        // Get information about the drivetrain;
        Vector2D position = getPosition();
        Angle angle = getAngle();

        Vector2D target = Angle.fromRadians(System.currentTimeMillis() / 2500.).getVector().mul(7.5);

        // Makes the control field centric
        Vector2D direction = target.sub(position).normalize();
        direction = direction.rotate(Angle.kZero.sub(angle));

        getDrivetrain().swerveDrive(direction, 0.25);
    }

    public Color getColor() {
        return Color.RED;
    }

}