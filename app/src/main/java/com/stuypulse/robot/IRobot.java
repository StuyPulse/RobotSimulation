package com.stuypulse.robot;

/*** VERY VERY IMPORTANT THAT YOU IMPORT THESE ***/
import java.awt.Color;
import com.stuypulse.stuylib.math.*;
import com.stuypulse.stuylib.control.*;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.*;
/*************************************************/

/**
 * An interface used to store a robot. This covers all the important 
 * functions besides the drivetrain, because that part can change.
 */
public interface IRobot {
     
    /**
     * Return the name of who made this robot
     * 
     * @return the name of the author
     */
    String getAuthor();

    /**
     * Get the color that you want the robot to be
     * 
     * @return the color you want the robot to be
     */
    Color getColor();

    /**
     * @return gets the current coordinates of the robot as a vector
     */
    Vector2D getPosition();

    /**
     * @return gets the current velocity of the robot as a vector
     */
    Vector2D getVelocity();

    /**
     * @return gets the current direction of the robot as an angle class (just use .toDegrees())
     */
    Angle getAngle();
    
    /**
     * This part of the code is executed every cycle,
     * it handles physics and the user code.
     */
    void periodic();

}
