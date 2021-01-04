package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.math.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import com.stuypulse.graphics.Line;
import com.stuypulse.physics.Force;
import com.stuypulse.robot.subsystems.components.Wheel;

/**
 * This is an implementation of SwerveDrive for this simulation.
 * It is very accurate, so it can be used to test swerve code
 * 
 * It has a function .swerveDrive() you can use to control it, 
 * with a direction and a turn value.
 */
public class SwerveDrive implements Drivetrain {

    /**
     * This class handles the setting the speed and direction of each wheel
     * It also has to handle normalization since thats an important part of SwerveDrive
     */
    private static class Module extends Wheel {
        private double speed;
        private Angle angle;

        public Module(Vector2D position) {
            super(position);
        }

        /**
         * Sets up the module to drive with certain instructions
         */
        public double point(Vector2D direction, double turn) {
            // Make sure the controls are in bounds
            direction = direction.div(Math.max(1.0, direction.magnitude()));
            turn = SLMath.limit(turn);

            // Calculate the vector the wheel should follow
            Vector2D perpendicular = getPosition().normalize().rotate(Angle.k90deg);
            Vector2D wheelVector = direction.add(perpendicular.mul(turn));

            // Angle and Speed the wheel should go at to follow the vector
            angle = wheelVector.getAngle();
            speed = wheelVector.magnitude() * angle.sub(getAngle()).cos();

            // Return the speed that it will go, used for normalization later
            return speed;
        }

        /**
         * Modifies the drive instructions to be normalized
         * This prevents distortion
         */
        public void normalize(double maxSpeed) {
            speed /= maxSpeed;
        }

        /**
         * Send the drive instructions to the wheel
         */
        public void drive() {
            set(speed);
            setAngle(angle);
        }
    }

    /**
     * A list of wheels (this class can work with a general number of wheels)
     */
    private final Module[] modules;
    
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
        
        this.modules = new Module[wheels.length];
        for(int i = 0; i < wheels.length; ++i) {
            this.modules[i] = new Module(wheels[i]);
        }
    }

    /**
     * This is how the SwerveDrive is controlled
     * @param direction a vector representing the direction that the robot needs to go
     * @param turn
     */
    public void swerveDrive(Vector2D direction, double turn) {
        double max = 1.0;
        for (Module m : modules) {
            max = Math.max(max, m.point(direction, turn));
        }

        for (Module m : modules) { 
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
                .toArray(new Force[modules.length])
        );
    }

    /**
     * This is some messy code for drawing the SwerveDrive
     * The reason its messy is because it has to draw each wheel
     */
    public Line[] getMesh() {
        double wheelSize = 0.4;

        LinkedList<Line> lines = new LinkedList<>();
        
        for(int i = 0; i < modules.length; ++i) {
            lines.push(new Line(
                modules[(i + 0) % modules.length].getPosition(), 
                modules[(i + 1) % modules.length].getPosition()
            ));
        }
        
        for(Wheel w : modules) {
            lines.push(new Line(
                w.getPosition().add(w.getAngle().getVector().mul(wheelSize)), 
                w.getPosition().sub(w.getAngle().getVector().mul(wheelSize))
            ));
        }
        
        return lines.toArray(new Line[0]);
    }

}