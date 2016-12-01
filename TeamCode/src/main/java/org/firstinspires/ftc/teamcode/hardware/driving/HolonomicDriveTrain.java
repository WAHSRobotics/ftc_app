package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.PowerScale;
import org.firstinspires.ftc.teamcode.util.Vec2;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static org.firstinspires.ftc.teamcode.util.MathUtil.setSignificantPlaces;

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

    private Vec2 degreesToPoint(double degrees, double power) {
        double radius = sqrt(2 * pow(power, 2));

        double angle = toRadians(degrees);

        double x = radius * cos(angle);
        double y = radius * sin(angle);

        x = setSignificantPlaces(x, 2);
        y = setSignificantPlaces(y, 2);

        return new Vec2(x, y);
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
        return (int) ((rotations * encoderTicksPerRotation) / Math.sqrt(Math.PI)); //Spook
    }

    private boolean motorsBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy();
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

        gyroSensor.calibrate();

        while(gyroSensor.isCalibrating()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

    private final double AUTONOMOUS_SPEED = 0.4;

    @Override
    public void stop() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

    @Override
    public void move(double millimeters) throws InterruptedException {
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int position = millimetersToEncoderTicks(millimeters);

        frontLeft.setTargetPosition(position);
        frontRight.setTargetPosition(-position);
        backLeft.setTargetPosition(position);
        backRight.setTargetPosition(-position);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(AUTONOMOUS_SPEED);
        frontRight.setPower(AUTONOMOUS_SPEED);
        backLeft.setPower(AUTONOMOUS_SPEED);
        backRight.setPower(AUTONOMOUS_SPEED);

        while(motorsBusy()) {
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void move(double millimeters, double degrees) throws InterruptedException {
        Vec2 point = degreesToPoint(degrees, AUTONOMOUS_SPEED);

        double frontLeftPower = holonomicMath(point.x, point.y, 0.0, Motor.FRONT_LEFT);
        double frontRightPower = holonomicMath(point.x, point.y, 0.0, Motor.FRONT_RIGHT);
        double backLeftPower = holonomicMath(point.x, point.y, 0.0, Motor.BACK_LEFT);
        double backRightPower = holonomicMath(point.x, point.y, 0.0, Motor.BACK_RIGHT);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int position = millimetersToEncoderTicks(millimeters);

        frontLeft.setTargetPosition(frontLeftPower >= 0 ? position : -position);
        frontRight.setTargetPosition(frontRightPower >= 0 ? position : -position);
        backLeft.setTargetPosition(backLeftPower >= 0 ? position : -position);
        backRight.setTargetPosition(backRightPower >= 0 ? position : -position);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        while(motorsBusy()) {
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void turn(int degrees) throws InterruptedException {
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyroSensor.calibrate();

        while(gyroSensor.isCalibrating()) {
            Thread.sleep(1);
        }

        int currentHeading;

        do {
            currentHeading = gyroSensor.getHeading();

            frontLeft.setPower(AUTONOMOUS_SPEED);
            frontRight.setPower(AUTONOMOUS_SPEED);
            backLeft.setPower(AUTONOMOUS_SPEED);
            backRight.setPower(AUTONOMOUS_SPEED);

            Thread.sleep(1);
        } while(currentHeading < degrees);

        stop();
    }
}
