package org.firstinspires.ftc.teamcode.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.HmeBot;
import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name = "HME Autonomous", group = "HME")
public class HmeAutonomous extends LinearOpMode {
    private Robot robot = new HmeBot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();

        waitForStart();

        robot.runAutonomous();

        robot.stop();
    }
}
