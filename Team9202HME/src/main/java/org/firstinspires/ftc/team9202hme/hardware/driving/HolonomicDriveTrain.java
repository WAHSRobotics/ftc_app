package org.firstinspires.ftc.team9202hme.hardware.driving;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.math.PowerScale;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class HolonomicDriveTrain extends DriveTrain {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private GyroSensor gyroSensor;

    private PowerScale powerScale = new PowerScale(0.02, 0.80, 6);

    private final double mmWheelDiameter;
    private final int encoderTicksPerRotation;

    public HolonomicDriveTrain(double mmWheelDiameter, int encoderTicksPerRotation) {
        this.mmWheelDiameter = mmWheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
    }

    private enum Motor {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT
    }

    private double holonomicMath(double x, double y, double rotation, Motor motor) {
        double power = 0.0;

        switch(motor) {
            case FRONT_LEFT: power = - y - x + rotation;
                break;
            case FRONT_RIGHT: power = + y - x + rotation;
                break;
            case BACK_LEFT: power = - y + x + rotation;
                break;
            case BACK_RIGHT: power = + y + x + rotation;
                break;
        }

        return power;
    }

    private void holonomicMove(double x, double y, double rotation) {
        if(x == 0.0 && y == 0.0 && rotation == 0.0) {
            stop();
        } else {
            frontLeft.setPower(powerScale.scalePower(holonomicMath(x, y, rotation, Motor.FRONT_LEFT)));
            frontRight.setPower(powerScale.scalePower(holonomicMath(x, y, rotation, Motor.FRONT_RIGHT)));
            backLeft.setPower(powerScale.scalePower(holonomicMath(x, y, rotation, Motor.BACK_LEFT)));
            backRight.setPower(powerScale.scalePower(holonomicMath(x, y, rotation, Motor.BACK_RIGHT)));
        }
    }

    private int millimetersToEncoderTicks(double millimeters) {
        double rotations = millimeters / (mmWheelDiameter * Math.PI);
        return (int) (rotations * encoderTicksPerRotation);
    }

    private boolean motorsBusy() {
        return frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy();
    }

    private boolean encodersExceedTargetPosition() {
        return frontLeft.getCurrentPosition() >= frontLeft.getTargetPosition() &&
                frontRight.getCurrentPosition() >= frontRight.getTargetPosition() &&
                backLeft.getCurrentPosition() >= backLeft.getTargetPosition() &&
                backRight.getCurrentPosition() >= backRight.getTargetPosition();
    }

    private void setRunMode(DcMotor.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        gyroSensor = hardwareMap.gyroSensor.get("gyro");

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Front Left Motor", frontLeft.getPower());
        telemetry.addData("Front Right Motor", frontRight.getPower());
        telemetry.addData("Back Left Motor", backLeft.getPower());
        telemetry.addData("Back Right Motor", backRight.getPower());

        telemetry.addData("Gyro X", gyroSensor.rawX());
        telemetry.addData("Gyro Y", gyroSensor.rawY());
        telemetry.addData("Gyro Z", gyroSensor.rawZ());
        telemetry.addData("Gyro H", gyroSensor.getHeading());
    }

    @Override
    public void driveControlled(Gamepad controller) {
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        holonomicMove(controller.left_stick_x, controller.left_stick_y, controller.right_stick_x);
    }

    @Override
    public void stop() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

    @Override
    public void move(double power, double millimeters) throws InterruptedException {
        move(power, millimeters, 0.0);
    }

    @Override
    public void move(double power, double millimeters, double degrees) throws InterruptedException {
        double angle = toRadians(degrees + 90);

        double x = power * cos(angle);
        double y = power * sin(angle);

        double frontLeftPower = holonomicMath(x, y, 0.0, Motor.FRONT_LEFT);
        double frontRightPower = holonomicMath(x, y, 0.0, Motor.FRONT_RIGHT);
        double backLeftPower = holonomicMath(x, y, 0.0, Motor.BACK_LEFT);
        double backRightPower = holonomicMath(x, y, 0.0, Motor.BACK_RIGHT);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int frontLeftPos = (int) (millimetersToEncoderTicks(millimeters) * frontLeftPower);
        int frontRightPos = (int) (millimetersToEncoderTicks(millimeters) * frontRightPower);
        int backLeftPos = (int) (millimetersToEncoderTicks(millimeters) * backLeftPower);
        int backRightPos = (int) (millimetersToEncoderTicks(millimeters) * backRightPower);

        frontLeft.setTargetPosition(frontLeftPower >= 0 ? frontLeftPos : -frontLeftPos);
        frontRight.setTargetPosition(frontRightPower >= 0 ? frontRightPos : -frontRightPos);
        backLeft.setTargetPosition(backLeftPower >= 0 ? backLeftPos : -backLeftPos);
        backRight.setTargetPosition(backRightPower >= 0 ? backRightPos : -backRightPos);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        while(motorsBusy() && !encodersExceedTargetPosition()) {
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void turn(double power, double degrees) throws InterruptedException {
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        do {
            gyroSensor.calibrate();
            Thread.sleep(5);
        } while(!gyroSensor.isCalibrating());

        while(gyroSensor.isCalibrating()) {
            Thread.sleep(5);
        }

        int currentHeading;

        do {
            currentHeading = gyroSensor.getHeading();

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            Thread.sleep(1);
        } while(currentHeading < degrees);

        stop();
    }
}
