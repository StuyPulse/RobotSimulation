package com.stuypulse.robot.subsystems.components;

import com.stuypulse.Constants.WheelProps;
import com.stuypulse.physics.Force;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.filters.*;
import com.stuypulse.stuylib.math.*;

/**
 * This wheel class is basically used to simulate a wheel.
 * 
 * It stores an angle and a speed and gives you a force vector back.
 * 
 * Its driven like a motor on a robot, and it is used to simulate realistic movement.
 */
public final class Wheel {

    private final Vector2D position;

    public Angle angle;
    public IStream realAngle; 

    public double target;
    public IFilter filter;

    public Wheel(Vector2D pos) {
        position = pos;

        angle = Angle.kZero;
        realAngle = new FilteredIStream(
            new IStream() {

                double lastValue = 0.0; 

                public double get() {
                    lastValue = angle.toRadians(lastValue);
                    return lastValue;
                }

            },
            new LowPassFilter(WheelProps.ANGLE_RC),
            new LowPassFilter(WheelProps.ANGLE_RC),
            new LowPassFilter(WheelProps.ANGLE_RC)
        );

        target = 0;

        filter = new IFilterGroup(
            new LowPassFilter(WheelProps.RC)
        );
    }

    public Vector2D getPosition() {
        return position;
    }

    public Angle getAngle() {
        return Angle.fromRadians(realAngle.get());
    }

    public void setAngle(Angle angle) {
        this.angle = angle;
    }

    public void set(double newTarget) {
        target = SLMath.limit(newTarget);
    }

    public double getSpeed() {
        return WheelProps.SPEED * filter.get(target);
    }

    public Force getForce() {
        return Force.getPointForce(
            position, 
            getAngle().add(Angle.k90deg).getVector().mul(getSpeed())
        );
    } 
}