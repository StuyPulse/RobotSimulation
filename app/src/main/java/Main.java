

import com.stuypulse.graphics.Graphics;
import com.stuypulse.robot.*;
import com.stuypulse.robot.subsystems.*;

import java.util.List;
import myrobots.*;

/**
 * This is just a really simple bootstrap into a robot simulation.
 */
public class Main {

    public static Robot<?>[] robots = new Robot<?>[]{
        //new Edwin(),
        new JeremyBot(),
    };

    public static void main(String[] args) throws Exception {
        Graphics g = new Graphics().addRobot(robots);

        while(true) {
            for(Robot<?> r : robots) {
                r.periodic();
            }
            g.periodic();

            Thread.sleep(10);
        }

    }
}
