package org.firstinspires.ftc.teamcode.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.LsRobot;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class LsAutonomousBlue extends LinearOpMode {
    private Robot robot = new LsRobot(this, Robot.FieldSide.BLUE);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();

        waitForStart();

        robot.runAutonomous();

        robot.stop();
    }
}
