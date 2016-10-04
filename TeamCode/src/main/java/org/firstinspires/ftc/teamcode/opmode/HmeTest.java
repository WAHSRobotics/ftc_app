package org.firstinspires.ftc.teamcode.opmode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.firstinspires.ftc.teamcode.robot.HmeTestBot;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "HME Test", group = "HME/Test")
public class HmeTest extends LinearOpMode {
    DcMotor left;
    DcMotor right;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Robot", "Is Working");

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        left.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.left_stick_y >= 0.05) {
                left.setPower(gamepad1.left_stick_y);
            }

            if(gamepad1.right_stick_y >= 0.05) {
                right.setPower(gamepad1.right_stick_y);
            }

            telemetry.update();
            idle();
        }
    }
}
