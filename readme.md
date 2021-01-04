# Robot Simulation

## How to run

#### 1. Clone the repository using git 
Run: `$ git clone git@github.com:StuyPulse/RobotSimulation.git`

or if that doesnt work, Run: `$ git clone https://github.com/StuyPulse/RobotSimulation.git`

#### 2. Run simulation using gradle

Run: `$ gradle run` while you are in the root of the project directory

If this doesn't work, you need to install gradle on your computer, this will not cover it.

#### 3. Programming your own robot 

Open the folder `app/src/main/java/myrobots/`, and make a new java file here.

I advise that you copy `Edwin.java` and make your own class. After that it's up to you to decide what you want it to do. It is structured similarly to regular robot programming, just with no commands.

After you program your own robot, head over to `app/src/main/java/Main.java` and add your robot to the list of robots at the top of the class. After that, your robot should appear in the simulation.

## Status of the program

This is still in alpha stage, mainly because of the graphics. The graphics tell you all you need to know, but leave a lot to be desired.
