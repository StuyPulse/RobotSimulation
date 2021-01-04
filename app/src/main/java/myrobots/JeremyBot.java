package myrobots;

/*** VERY VERY IMPORTANT THAT YOU IMPORT THESE ***/
import java.awt.Color;
import com.stuypulse.stuylib.math.*;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.control.*;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.subsystems.components.Wheel;
import com.stuypulse.graphics.StdDraw;
import com.stuypulse.robot.*;
/*************************************************/

/**
 * This robot is an example of a robot using swerve drive.
 * 
 * This is useful when testing swerve.
 */
public class JeremyBot extends Robot<SwerveDrive> {

    public static final double kP = 1.0;
    public static final double kD = 0.2;

    public IFilter turnFilter = new LowPassFilter(0.25);

    public JeremyBot() {
        // Make sure to pass in an instance of your drivetrain to 
        // the parent class so it can use it in the simulation
        super(new SwerveDrive(new Vector2D(2.0, 2.0)));
    }

    public String getAuthor() {
        return "Sam B / Myles P";
    }

    public void execute() {
        // Get information about the drivetrain;
        Vector2D position = getPosition();
        Vector2D velocity = getVelocity();
        Angle angle = getAngle();

        // These values will be passed into the drivetrain
        Vector2D direction = new Vector2D(0.0, 0.0);
        double turn = 0;

        // This code just follows the mouse and spins when the moust is down
        Vector2D target = new Vector2D(StdDraw.mouseX(), StdDraw.mouseY());
        direction = target.sub(position.mul(kP).add(velocity.mul(kD)));
        if(direction.magnitude() < 0.05) {
            direction = direction.mul(0.0);
        }

        if(StdDraw.isMousePressed()) {
            turn = 0.75;
        }
        
        // Makes the control field centric
        direction = direction.rotate(Angle.kZero.sub(angle));

        // Drive the drivetrain
        getDrivetrain().swerveDrive(direction, turnFilter.get(turn));
    }

    public Color getColor() {
        return Color.RED;
    }

}