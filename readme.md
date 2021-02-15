# Robot Simulation

## How to run

### 1. Clone the repository using git

Run: `$ git clone git@github.com:StuyPulse/RobotSimulation.git`

or if that doesnt work, Run: `$ git clone https://github.com/StuyPulse/RobotSimulation.git`

### 2. Run simulation using gradle

Run: `$ gradle run` while you are in the root of the project directory

If this doesn't work, you need to install gradle on your computer, this will not cover it.

### 3. Programming your own robot

Open the folder (`app/src/main/java/myrobots/`)[https://github.com/StuyPulse/RobotSimulation/tree/main/app/src/main/java/myrobots], and make a new java file here.

I advise that you copy (`Edwin.java`)[https://github.com/StuyPulse/RobotSimulation/blob/main/app/src/main/java/myrobots/Edwin.java] and make your own class. After that it's up to you to decide what you want it to do. It is structured similarly to regular robot programming, just with no commands.

You can also check out the examples in the (`app/src/main/java/myrobots/`)[https://github.com/StuyPulse/RobotSimulation/tree/main/app/src/main/java/myrobots] folder that algorithms often implemented in robot code, like control algorithms in (`app/src/main/java/myrobots/control`)[https://github.com/StuyPulse/RobotSimulation/tree/main/app/src/main/java/myrobots/control](`app/src/main/java/myrobots/control`).

After you program your own robot, head over to (`app/src/main/java/Main.java`)[https://github.com/StuyPulse/RobotSimulation/blob/main/app/src/main/java/Main.java] and add your robot to the list of robots at the top of the class. After that, your robot should appear in the simulation.

## Status of the program

### 1. Graphics

The robot simulation has a 3d graphical environment, but it is not complete. It needs --

* Better lighting
* A better floor (grid, preferrably)
* Smoother camera

### 2. Robot Code

The goal for a project like this is to provide a new way to practice coding a robot. However, this experience is not fully captured yet. Features like --

* Command system
* More extensive Robot base class
* More GUI features (like text) that will show debug info
