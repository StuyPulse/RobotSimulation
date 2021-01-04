package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.math.*;

import java.util.LinkedList;

import com.stuypulse.graphics.Line;
import com.stuypulse.physics.Force;
import com.stuypulse.robot.subsystems.components.Wheel;

public class SwerveDrive implements Drivetrain {

    /**
     * A list of wheels (this class can work with a general number of wheels)
     */
    private final Wheel[] modules;
    
    /**
     * Creates a swerve drive with four wheels. 
     * 
     * When given a robot size, assumes that it's a 
     * rectangular and places four wheels at each corner
     * of the robot.
     * 
     * @param size length, width of the robot
     */
    public SwerveDrive(Vector2D size) {
        // divide size by 2 so the center of the robot is at (0,0)
        var hSize = size.div(2.0);
        
        // there will be four modules
        this.modules = new Wheel[4];

        for (int i = 0; i < modules.length; ++i) {

            // use hSize to position the robot because hSize is also a vector
            // (hSize starts out at the top, left corner of the robot)
            this.modules[i] = new Wheel(hSize);
            
            // This code is used to gradually flip
            // hSize to each corner of the robot
            if ((i&1) == 0) { // if i is 0 or 2, flip the y coordinate
                hSize = new Vector2D(hSize.x, -hSize.y);
            } else { // if i is 1 or 3, flip the x coordinate
                hSize = new Vector2D(-hSize.x, hSize.y);
            }
        }
    }
    
    /**
     * Creates a swerve drive with the given list
     * 
     * @param wheels list of wheels, must be at least 2 wheels
     */
    public SwerveDrive(Wheel... wheels) {
        if (wheels.length <= 1)
            throw new IllegalArgumentException("Must have at least two wheels");
        this.modules = wheels;
    }

    public void swerveDrive(Vector2D direction, double angVel) {
        // Due to 
        
        angVel = SLMath.limit(angVel);

        double[] magnitudeOutputs = new double[modules.length];
        Angle[] angleOutputs = new Angle[modules.length];

        double maxSpeed = 1.0;

        for (int i = 0; i < modules.length; ++i) {

            Vector2D perp = modules[i].getPosition().rotate(Angle.k90deg);
            Vector2D modDirection = direction.add(perp.mul(angVel));

            magnitudeOutputs[i] = modules[i].getAngleError().cos() * modDirection.magnitude();
            angleOutputs[i] = modDirection.getAngle();

            maxSpeed = Math.max(magnitudeOutputs[i], maxSpeed);
        }
        
        for (int i = 0; i < modules.length; ++i) {
            magnitudeOutputs[i] /= maxSpeed;

            modules[i].set(magnitudeOutputs[i]);
            modules[i].setAngle(angleOutputs[i]);
        }
    }

    public Line[] getMesh() {
        double wheelSize = 0.4;

        LinkedList<Line> lines = new LinkedList<>();
        
        for(int i = 0; i < modules.length; ++i) {
            lines.push(new Line(modules[i].getPosition(), modules[(i + 1) % modules.length].getPosition()));
        }
        
        for(Wheel w : modules) {
            lines.push(new Line(w.getPosition().add(w.getAngle().getVector().mul(wheelSize)), w.getPosition().sub(w.getAngle().getVector().mul(wheelSize))));
        }
        
        return lines.toArray(new Line[0]);
    }

    public Force getNetForce() {
        Force[] forces = new Force[modules.length];

        for (int i = 0; i < modules.length; ++i) {
            forces[i] = modules[i].getForce();
        }

        return Force.getNetForce(
            forces
        );
    }

}