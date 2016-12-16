package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.teleop.LSRobit;


@Autonomous (name = "WheelTest")
public class WheelDiag extends LinearOpMode {
    private LSRobit robot = new LSRobit();
    @Override
//memes
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.rightfront.setPower(1.0);
        sleep(3000);
        robot.rightfront.setPower(0);

        robot.rightback.setPower(1.0);
        sleep(3000);
        robot.rightback.setPower(0);

        robot.leftfront.setPower(1.0);
        sleep(3000);
        robot.leftfront.setPower(0D);

        robot.leftback.setPower(1.0);
        sleep(3000);
        robot.leftback.setPower(0);


    }









}

