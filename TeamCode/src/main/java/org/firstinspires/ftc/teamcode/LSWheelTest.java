package org.firstinspires.ftc.teamcode;

/**
 * Created by Nathaniel S. Glover on 1/30/2016, at 11:39 AM.
 * FTC Robotics - Loose Screws 4970
 */
public class LSWheelTest extends LSAutonomous {
    @Override
    public void runOpMode() throws InterruptedException {
        initRobot();

        final int millis = 4000;

        bLeft.setPower(0.5);
        sleep(millis);
        bLeft.setPower(0);
        sleep(millis);
        bRight.setPower(0.5);
        sleep(millis);
        bRight.setPower(0);
        sleep(millis);
        fRight.setPower(0.5);
        sleep(millis);
        fRight.setPower(0);
        sleep(millis);
        fLeft.setPower(0.5);
        sleep(millis);
        fLeft.setPower(0);

        stopMotors();
    }
}
