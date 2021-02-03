
import com.stuypulse.graphics.Graphics;
import com.stuypulse.graphics3d.Window;
import com.stuypulse.robot.*;
import com.stuypulse.robot.subsystems.*;

import java.util.List;
import myrobots.*;
import myrobots.control.*;
import myrobots.piddemo.*;


/**
 * This is just a really simple bootstrap into a robot simulation.
 */
public class Main {

    public static Robot<?>[] robots = new Robot<?>[]{
        // new Edwin(),
        // new JeremyBot(),
        // new PIDDemo()
        new PIDBot(),
        new DRBot(),
        new BBBot()
    };

    public static void main(String[] args) throws Exception {
        Window.initialize();

        Graphics g = new Graphics()
            .addRobot(robots);

        while(g.isOpen()) {
            g.periodic();
            Thread.sleep(20);
        }

        Window.terminate();
    }
}
