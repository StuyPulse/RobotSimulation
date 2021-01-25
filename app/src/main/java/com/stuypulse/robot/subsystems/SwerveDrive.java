package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.math.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.stuypulse.graphics.MeshInstance;
import com.stuypulse.graphics.RenderObject;
import com.stuypulse.physics.Force;
import com.stuypulse.robot.subsystems.components.SwerveModule;

import static com.stuypulse.Constants.SwerveDriveSettings.*;

/**
 * This is an implementation of SwerveDrive for this simulation.
 * It is very accurate, so it can be used to test swerve code
 * 
 * It has a function .swerveDrive() you can use to control it, 
 * with a direction and a turn value.
 */
public class SwerveDrive implements Drivetrain {

    /**
     * A list of wheels (this class can work with a general number of wheels)
     */
    private final SwerveModule[] modules;
    
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
        this(new Vector2D[]{
            size.mul(new Vector2D(+0.5, +0.5)),
            size.mul(new Vector2D(+0.5, -0.5)),
            size.mul(new Vector2D(-0.5, -0.5)),
            size.mul(new Vector2D(-0.5, +0.5))
        });
    }
    
    /**
     * Creates a swerve drive with the given list
     * 
     * @param wheels list of wheels, must be at least 2 wheels
     */
    public SwerveDrive(Vector2D[] wheels) {
        if (wheels.length <= 1)
            throw new IllegalArgumentException("Must have at least two wheels");
        
        this.modules = new SwerveModule[wheels.length];
        for(int i = 0; i < wheels.length; ++i) {
            this.modules[i] = new SwerveModule(wheels[i]);
        }
    }

    /**
     * This is how the SwerveDrive is controlled
     * @param direction a vector representing the direction that the robot needs to go
     * @param turn the amount it should be turning, high values can make the direction unusable
     */
    public void swerveDrive(Vector2D direction, double turn) {
        double max = 1.0;
        for (SwerveModule m : modules) {
            max = Math.max(max, m.point(direction, turn));
        }

        for (SwerveModule m : modules) { 
            m.normalize(max);
            m.drive();
        }
    }

    /**
     * Gets the force of each wheel and sums it up
     */
    public Force getNetForce() {
        return Force.getNetForce(
            Arrays.asList(modules)
                .stream()
                .map(module -> module.getForce())
                .collect(Collectors.toList())
                .toArray(new Force[0])
        );
    }

    /******************
     * RENDER DETAILS *
     ******************/

    protected static MeshInstance SWERVE_MESH = new MeshInstance(
        SWERVE_PATH, 
        Angle.fromDegrees(270.0),
        Angle.fromDegrees(90.0),
        Angle.kZero
    );

    public List<RenderObject> getRenderable() {

        List<RenderObject> out = new ArrayList<>();

        out.add(new RenderObject(
            SWERVE_MESH.get()
        ));

        return out;
        
    }

}
