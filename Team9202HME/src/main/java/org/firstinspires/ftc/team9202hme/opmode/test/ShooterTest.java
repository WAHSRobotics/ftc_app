package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Shooter Test", group = "Tests")
//@Disabled
public class ShooterTest extends OpMode{
    private DcMotor leftShooter, rightShooter, lift, spinner;

    @Override
    public void init() {
        leftShooter = hardwareMap.dcMotor.get("left");
        rightShooter = hardwareMap.dcMotor.get("right");
        lift = hardwareMap.dcMotor.get("lift");
        spinner = hardwareMap.dcMotor.get("spinner");
    }

    @Override
    public void loop() {
        leftShooter.setPower(1);
        rightShooter.setPower(1);
        lift.setPower(1);
        spinner.setPower(1);
    }
}
