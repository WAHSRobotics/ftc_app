package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.identification.HardwareIdentifier;
import org.firstinspires.ftc.teamcode.hardware.identification.HardwarePosition;
import org.firstinspires.ftc.teamcode.hardware.identification.HardwarePurpose;
import org.firstinspires.ftc.teamcode.hardware.identification.HardwareSide;
import org.firstinspires.ftc.teamcode.util.PowerScale;

import java.io.PrintStream;

public class HolonomicDriveTrain extends DriveTrain {
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    private PowerScale powerScale = new PowerScale(0.05, 1.00);

    private void move(double x, double y, double rotation) {
        frontLeft.setPower(powerScale.scalePower(- y - x + rotation));

        frontRight.setPower(powerScale.scalePower(+ y - x + rotation));

        backRight.setPower(powerScale.scalePower(+ y + x + rotation));

        backLeft.setPower(powerScale.scalePower(- y + x + rotation));
    }

    @Override
    public void init(HardwareIdentifier identifier) {
        frontLeft = identifier.findDcMotor(HardwarePurpose.DRIVING, HardwareSide.BOTTOM, HardwarePosition.FRONT_LEFT);
        frontRight = identifier.findDcMotor(HardwarePurpose.DRIVING, HardwareSide.BOTTOM, HardwarePosition.FRONT_RIGHT);
        backLeft = identifier.findDcMotor(HardwarePurpose.DRIVING, HardwareSide.BOTTOM, HardwarePosition.BACK_LEFT);
        backRight = identifier.findDcMotor(HardwarePurpose.DRIVING, HardwareSide.BOTTOM, HardwarePosition.BACK_RIGHT);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Front Left Motor: ", frontLeft.getPower());
        telemetry.addData("Front Right Motor: ", frontRight.getPower());
        telemetry.addData("Back Left Motor: ", backLeft.getPower());
        telemetry.addData("Back Right Motor: ", backRight.getPower());
    }

    @Override
    public void driveControlled(Gamepad controller) {
        double x = controller.left_stick_x;
        double y = controller.left_stick_y;
        double rotation = controller.right_stick_x;

        move(x, y, rotation);
    }

    @Override
    public void move(double millimeters) {
        //TODO: Make millimeters to milliseconds function for this class
    }

    @Override
    public void move(double millimeters, double degrees) {

    }

    @Override
    public void turn(double degrees) {
        //TODO: Make degrees to milliseconds function for this class
    }
}
