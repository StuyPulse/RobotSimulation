package com.stuypulse.graphics;

import com.stuypulse.physics.Position;
import com.stuypulse.robot.Robot;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import java.util.List;
import java.util.LinkedList;

/**
 * TODO: reprogram this graphics engine, it is not upto the standards that we want
 * 
 * This class is a super simple way to draw all the robots in the simulation
 */
public class Graphics {


    List<Robot<?>> robots;
    
    public Graphics() {
        robots = new LinkedList<>();

        StdDraw.setCanvasSize(720, 720);

        StdDraw.setXscale(-10, 10);
        StdDraw.setYscale(-10, 10);
    }

    public Graphics addRobot(Robot<?>... rs) {
        for(var r : rs) {
            robots.add(r);
        }
        return this;
    }

    public void line(Vector2D a, Vector2D b) {
        StdDraw.line(a.x,a.y,b.x,b.y);
    }


    public void drawRobot(Robot<?> r) {
        StdDraw.setPenColor(r.getColor());
        Vector2D pos = r.getPosition();
        Angle ang = r.getAngle();
        Line[] mesh = r.getDrivetrain().getMesh();

        for(Line l : mesh) {
            l.transform(pos, ang).draw();
        }
    }

    public void periodic() {
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();

        for(Robot<?> r : robots) {
            drawRobot(r);
        }

        StdDraw.show();
    }

}