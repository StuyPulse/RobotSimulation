package com.stuypulse.graphics;

import static com.stuypulse.Constants.WindowSettings.*;
import static com.stuypulse.Constants.CameraSettings.*;

import com.stuypulse.graphics3d.Main;
import com.stuypulse.graphics3d.math3d.Triangle;
import com.stuypulse.graphics3d.*;
import com.stuypulse.graphics3d.render.*;

import com.stuypulse.robot.Robot;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.util.StopWatch;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.LinkedList;

public final class Graphics {

    public final static Triangle[] CUBE_TRIANGLES =
        Main.CUBE_TRIANGLES; // eventually move the cube triangles into here

    private final static void setupCamera(Camera camera) {
        camera.setFov(FOV);
        camera.setPosition(POSITION);
        camera.setFarPlane(FAR_PLANE);
        camera.setNearPlane(NEAR_PLANE);
        camera.setPitch(PITCH);
        camera.setYaw(YAW);
        camera.setRoll(ROLL);
    }

    private final List<Robot<?>> robots;
    
    // Graphics Library
    private Window window;
    private StopWatch timer;

    private Mesh mesh;

    public Graphics() {
        this.robots = new LinkedList<>();

        // Graphics stuff
        this.timer = new StopWatch();

        this.window = new Window(
            TITLE,
            WIDTH,
            HEIGHT
        );

        this.mesh = MeshLoader.getMeshFromObj(MESH);

        this.window.setShader(Shader.fromFiles(SHADER));
        this.window.getMouse().setVisible(!HIDE_MOUSE);

        setupCamera(this.window.getCamera());
    }

    public Graphics addRobot(Robot<?>... rs) {
        for(Robot<?> r : rs) {
            robots.add(r);
        }
        return this;
    }

    public void drawRobot(Robot<?> r) {
        final Vector2D pos = r.getPosition();
        final Angle angle = r.getAngle();

        window.draw(
            this.mesh,
            // r.getDrivetrain().getMesh(), 
            // do this to avoid creating a joml.Vector3f
            new Transform()
                .setCentered(r.getDrivetrain().isCentered())
                .setPitch(angle)
                .setX((float) pos.x)
                .setZ((float) pos.y),
                
            r.getDrivetrain().getColor()

        );

    }

    public boolean isOpen() {
        return window.isOpen();
    }

    public void periodic() {
        // PERIODIC LOOP
        window.pollErrors();
        window.clear();

        for(Robot<?> r : robots) {
            drawRobot(r);
        }

        window.swapBuffers();
        window.pollEvents();

        // KEY INPUT
        final double dt = timer.reset();

        final var keys = window.getKeys();
        final var camera = window.getCamera();
        final var mouse = window.getMouse();

        if (keys.hasKey(EXIT)) {
            window.close();
        }

        // CAMERA MOVEMENT
        final float speed = keys.hasKey(SPEED_UP) ? FAST_SPEED : SLOW_SPEED;

        float yDir = speed * (float)(dt) *
            ((keys.hasKey(UP) ? 1 : 0) - (keys.hasKey(DOWN) ? 1 : 0));
        float zDir = speed * (float)(dt) * 
            ((keys.hasKey(BACK) ? 1 : 0) - (keys.hasKey(FORWARD) ? 1 : 0));
        float xDir = speed * (float)(dt) *
            ((keys.hasKey(RIGHT) ? 1 : 0) - (keys.hasKey(LEFT) ? 1 : 0));

        Vector3f dir = new Vector3f(xDir, yDir, zDir);
        float yAngle = (float) (camera.getPitch().toRadians());

        camera.setPosition(camera.getPosition().add(dir.rotateY(yAngle)));

        // CAMERA ROTATION
        final var delta = mouse.getDelta();
    
        final double MAX_ANGLE = Math.PI / 2.0;

        float pitch = -SENSITIVITY_X * (float)(dt) *
            (delta.x / (WIDTH / 2f));

        float yaw = -SENSITIVITY_Y * (float)(dt) *
            (delta.y / (HEIGHT / 2f));

        camera.setPitch(Angle.fromRadians(camera.getPitch().toRadians() + pitch));
        camera.setYaw(Angle.fromRadians(SLMath.limit(
            camera.getYaw().toRadians() + yaw, 
            -MAX_ANGLE, 
            MAX_ANGLE
        )));

        mouse.setPosition(WIDTH / 2, HEIGHT / 2);
    }

}