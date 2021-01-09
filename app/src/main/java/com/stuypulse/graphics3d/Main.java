package com.stuypulse.graphics3d;

import static com.stuypulse.Constants.WindowSettings.*;

public class Main {
    
    public static void main(String... args) {
        Window.initialize();

        Window window = new Window(
            "Robot Simulation",
            WIDTH,
            HEIGHT
        );

        System.out.println("Starting window...");

        while (window.isOpen()) {
            window.update();
        }
        window.close();

        System.out.println("...Closing window");

        Window.terminate();
    }

}
