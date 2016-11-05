package org.firstinspires.ftc.teamcode.hardware.driving;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TankDrive extends DriveTrain {
    private DcMotor left, right;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void driveControlled(Gamepad controller) {
        left.setPower(controller.left_stick_y);
        right.setPower(controller.right_stick_y);
    }

    @Override
    public void stop() {
        left.setPower(0);
        right.setPower(0);
    }

    @Override
    public void move(double millimeters) {
        //move that many millimeters
    }

    @Override
    public void move(double millimeters, double degrees) {
        //move that many millimeters at that angle in degrees
    }

    @Override
    public void turn(double degrees) {
        //turn to that angle in degrees
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Motor Power:", left.getPower());
        telemetry.addData("Right Motor Power:", right.getPower());
    }
}
