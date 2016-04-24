package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Nathaniel S. Glover on 12/19/2015, at 1:10 AM.
 * FTC Robotics - Loose Screws 4970
 */
public class LSAutonomousRight extends LSAutonomous {
    @Override
    public void runOpMode() throws InterruptedException {
        initRobot();

        while(currentState != State.STOP) {
            switch(currentState) {
                case PARK:
                    move(12,MoveDirection.FORWARD);
                    turn(360, TurnDirection.CLOCKWISE);
                    nextState();break;
            }
        }

        stopMotors();
    }
}
