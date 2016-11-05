package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.HardwareSpecifications;
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

    private PowerScale powerScale = new PowerScale();

    private HardwareSpecifications specifications;

    private Vec2 degreesToPoint(double degrees, double power) {
        double radius = sqrt(pow(power, 2) + pow(power, 2));

        double angle = toRadians(degrees);

        double x = radius * cos(angle);
        double y = radius * sin(angle);

        x = setSignificantPlaces(x, 2);
        y = setSignificantPlaces(y, 2);

        return new Vec2(x, y);
    }

    private void holonomicMove(double x, double y, double rotation) {
        if(x == 0.0 && y == 0.0 && rotation == 0.0) {
            stop();
        } else {
            frontLeft.setPower(powerScale.scalePower(- y - x + rotation));
            frontRight.setPower(powerScale.scalePower(+ y - x + rotation));
            backRight.setPower(powerScale.scalePower(+ y + x + rotation));
            backLeft.setPower(powerScale.scalePower(- y + x + rotation));
        }
    }

    private int millimetersToEncoderTicks(double millimeters) {
        double rotations = millimeters / (specifications.getMmWheelCircumference() * Math.PI);
        return (int) (rotations * specifications.getEncoderTicksPerRotation());
    }

    private int degreesToEncoderTicks(double degrees) {
        double ratio = degrees / 360;
        double mmRadius = sqrt(pow(specifications.getMmLength(), 2) + pow(specifications.getMmWidth(), 2));
        double mmRobotTurnCircumference = 2 * Math.PI * mmRadius;
        double mmTurnCurve = ratio * mmRobotTurnCircumference;

        double rotations = mmTurnCurve / (specifications.getMmWheelCircumference() * Math.PI);
        return (int) (rotations * specifications.getEncoderTicksPerRotation());
    }

    private boolean motorsBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy();
    }

    private void setRunMode(DcMotor.RunMode runMode) {
        if(frontLeft.getMode() != runMode && frontRight.getMode() != runMode && backLeft.getMode() != runMode && backRight.getMode() != runMode) {
            frontLeft.setMode(runMode);
            frontRight.setMode(runMode);
            backLeft.setMode(runMode);
            backRight.setMode(runMode);
        }
    }

    private void setTargetPosition(int encoderTicks) {
        frontLeft.setTargetPosition(encoderTicks);
        frontRight.setTargetPosition(encoderTicks);
        backLeft.setTargetPosition(encoderTicks);
        backRight.setTargetPosition(encoderTicks);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        if(!motorsBusy()) {
            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            holonomicMove(controller.left_stick_x, controller.left_stick_y, controller.right_stick_x);
        }
    }

    private final double AUTONOMOUS_SPEED = 0.5;

    @Override
    public void stop() {
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

    @Override
    public void move(double millimeters) {
        if(!motorsBusy()) {
            holonomicMove(0.0, AUTONOMOUS_SPEED, 0.0);

            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            setTargetPosition(millimetersToEncoderTicks(millimeters));
        }
    }

    @Override
    public void move(double millimeters, double degrees) {
        if(!motorsBusy()) {
            Vec2 stickPower = degreesToPoint(degrees, AUTONOMOUS_SPEED);

            holonomicMove(stickPower.x, stickPower.y, 0.0);

            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            setTargetPosition(millimetersToEncoderTicks(millimeters));
        }
    }

    @Override
    public void turn(double degrees) {
        if(!motorsBusy()) {
            double multiplier = degrees >= 0 ? 1.0 : -1.0;

            holonomicMove(0.0, 0.0, AUTONOMOUS_SPEED * multiplier);

            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            setTargetPosition(degreesToEncoderTicks(degrees));
        }
    }
}
