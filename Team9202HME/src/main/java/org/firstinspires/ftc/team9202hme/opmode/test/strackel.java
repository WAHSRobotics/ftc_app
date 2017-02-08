package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;

/**
 * Created by Sage on 2/7/2017.
 */

@TeleOp(name = "Strackel", group = "Tests")


public class strackel extends OpMode{

    HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);

    @Override
    public void init() {
        DcMotor frontLeft, backRight, frontRight, backLeft;

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
    }

    @Override
    public void loop() {
        driveTrain.moveAndTurn(0.5, 360, 0.5);
    }
}
