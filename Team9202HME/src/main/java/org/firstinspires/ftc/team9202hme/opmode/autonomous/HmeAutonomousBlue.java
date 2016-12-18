package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.robot.HmeBot;
import org.firstinspires.ftc.team9202hme.robot.Robot;

@Autonomous(name = "HME Autonomous Blue", group = "HME")
public class HmeAutonomousBlue extends LinearOpMode {
    private Robot robot = new HmeBot(this, Robot.FieldSide.BLUE);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();

        waitForStart();

        robot.runAutonomous();

        robot.stop();
    }
}
