
import com.stuypulse.graphics.Graphics;
import com.stuypulse.graphics3d.Window;
import com.stuypulse.robot.*;
import com.stuypulse.robot.subsystems.*;

import java.util.List;
import myrobots.*;
import myrobots.piddemo.PIDDemo;

/**
 * This is just a really simple bootstrap into a robot simulation.
 */
public class Main {

    public static Robot<?>[] robots = new Robot<?>[]{
        new Edwin(),
        new JeremyBot(),
        new PIDDemo()
    };

    public static void main(String[] args) throws Exception {
        Window.initialize();

        Graphics g = new Graphics().addRobot(robots);

        while(g.isOpen()) {

            if (g.isSimulating()) {
                for(Robot<?> r : robots) {
                    r.periodic();
                }
            }
            g.periodic();

            Thread.sleep(20);
        }

        Window.terminate();
    }
}
