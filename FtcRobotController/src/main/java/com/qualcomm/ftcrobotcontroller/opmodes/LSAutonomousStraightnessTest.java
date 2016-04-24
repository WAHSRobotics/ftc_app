package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Nathaniel S. Glover on 1/9/2016, at 1:43 PM.
 * FTC Robotics - Loose Screws 4970
 */
public class LSAutonomousStraightnessTest extends LSAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {
        initRobot();

        timelyMove(4000, MoveDirection.FORWARD);

        stopMotors();
    }
}
