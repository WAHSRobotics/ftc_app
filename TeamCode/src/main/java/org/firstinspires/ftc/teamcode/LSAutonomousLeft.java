package org.firstinspires.ftc.teamcode;

/**
 * Created by Nathaniel S. Glover on 12/15/2015, at 4:42 PM.
 * FTC Robotics - Loose Screws 4970
 */
public class LSAutonomousLeft extends LSAutonomous {
    @Override
    public void runOpMode() throws InterruptedException {
        initRobot();

        while(currentState != State.STOP) {
            switch(currentState) {
                case PARK:
                    move(12, MoveDirection.FORWARD);
                    turn(360, TurnDirection.COUNTER_CLOCKWISE);
                    nextState();
            }
        }

        stopMotors();
    }
}
