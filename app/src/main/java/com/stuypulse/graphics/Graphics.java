package com.stuypulse.graphics;

import static com.stuypulse.Constants.WindowSettings.*;
import static com.stuypulse.Constants.CameraSettings.*;

import com.stuypulse.graphics3d.*;
import com.stuypulse.graphics3d.render.*;

import com.stuypulse.robot.Robot;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.util.StopWatch;

import org.joml.Vector3f;

import java.util.List;
import java.util.LinkedList;

public final class Graphics {

    private final static void setupCamera(Camera camera) {
        camera.setFov(FOV);
        camera.setPosition(POSITION);
        camera.setFarPlane(FAR_PLANE);
        camera.setNearPlane(NEAR_PLANE);
        camera.setPitch(PITCH);
        camera.setYaw(YAW);
        camera.setRoll(ROLL);
    }

    private static final MeshInstance GRID_MESH = new MeshInstance(GRID_PATH);

    private final List<Robot<?>> robots;
    
    // Graphics Library
    private Window window;
    private StopWatch timer;

    private Shader meshShader;
    private Shader floorShader;

    private Skybox skybox;
    private Shader skyboxShader;

    public Graphics() {
        this.robots = new LinkedList<>();

        // Graphics stuff
        this.timer = new StopWatch();

        this.window = new Window(
            TITLE,
            WIDTH,
            HEIGHT
        );

        this.meshShader = Shader.fromFiles(MESH_SHADER);

        this.floorShader = Shader.fromFiles(GRID_SHADER);

        this.window.getMouse().setVisible(!HIDE_MOUSE);

        this.skybox = new Skybox(CUBEMAP_PATH);
        this.skyboxShader = Shader.fromFiles(SKYBOX_PATH);

        centerMouse();

        setupCamera(this.window.getCamera());
    }

    private void centerMouse() {
        this.window.getMouse().setPosition(WIDTH / 2, HEIGHT / 2);
    }

    public Graphics addRobot(Robot<?>... rs) {
        for(Robot<?> r : rs) {
            robots.add(r);
        }
        return this;
    }

    public void drawRobot(Robot<?> r) {

        window.setShader(meshShader);

        Vector2D pos = r.getPosition();
        Angle angle = r.getAngle();

        List<RenderObject> renderables = r.getDrivetrain().getRenderable();

        Transform transform = new Transform()
            .setPitch(angle)
            .setX((float) pos.x)
            .setZ((float) pos.y);

        for (RenderObject obj : renderables) {
            window.draw(
                obj.getMesh(),

                new Transform(obj.getTransform())
                    .transform(transform),
                
                r.getColor()
            );
        }

    }

    public boolean isOpen() {
        return window.isOpen();
    }

    private boolean simulating = false;
    private boolean lastToggle = false;

    public boolean isSimulating() {
        return simulating;
    }

    private void drawSkybox() {
        window.setShader(skyboxShader);
        window.draw(skybox);
    }

    private void drawFloor() {
        // super crappy way to get infinite floor
        final float floorScale = 50;
        final var cameraPos = window.getCamera().getPosition();

        window.setShader(floorShader);
        window.draw(
            GRID_MESH.get(), 
            new Transform()
                .setScale(new Vector3f(
                    floorScale + floorScale * Math.abs(cameraPos.y),
                    1.0f, 
                    floorScale + floorScale * Math.abs(cameraPos.y)
                ))
                .setX(cameraPos.x)
                .setZ(cameraPos.z)
                .setY(-0.075f), 
            new Vector3f(0, 0.8f, 0)
        );
    }

    private void updateCamera(double dt) {
        final var keys = window.getKeys();
        final var camera = window.getCamera();
        final var mouse = window.getMouse();

        // UPDATE POSITION
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

        // UPDATE ROTATION
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
    }

    public void periodic() {

        // PERIODIC LOOP
        window.pollErrors();
        window.clear();

        // DRAWING
        drawSkybox();
        drawFloor();
        for(Robot<?> r : robots)
            drawRobot(r);

        window.swapBuffers();
        window.pollEvents();

        // KEY INPUT
        final double dt = timer.reset();

        final var keys = window.getKeys();

        if (keys.hasKey(EXIT)) {
            window.close();
        }

        final boolean toggle = keys.hasKey(TOGGLE_SIMULATION);
        simulating ^= (toggle && !lastToggle);

        updateCamera(dt);
        centerMouse();

        lastToggle = toggle;

        if (isSimulating()) {
            for (Robot<?> r : robots) 
                r.periodic(dt);
        }

    }

}