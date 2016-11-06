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

    public HolonomicDriveTrain(HardwareSpecifications specifications) {
        this.specifications = specifications;
    }

    private Vec2 degreesToPoint(double degrees, double power) {
        double radius = sqrt(pow(power, 2) + pow(power, 2));

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
            frontLeft.setPower(powerScale.scalePower(- y - x + rotation));
            frontRight.setPower(powerScale.scalePower(+ y - x + rotation));
            backLeft.setPower(powerScale.scalePower(- y + x + rotation));
            backRight.setPower(powerScale.scalePower(+ y + x + rotation));
        }
    }

    private int millimetersToEncoderTicks(double millimeters) {
        double rotations = millimeters / (specifications.getMmWheelDiameter() * Math.PI);
        return (int) ((rotations * specifications.getEncoderTicksPerRotation()) / Math.sqrt(Math.PI)); //Spook
    }

    private int degreesToEncoderTicks(double degrees) {
        double ratio = degrees / 360;
        double mmRobotCircumference = Math.PI * specifications.getMmRobotDiameter();

        double mmTurnCurve = ratio * mmRobotCircumference;

        double rotations = mmTurnCurve / (specifications.getMmWheelDiameter() * Math.PI);
        return (int) ((rotations * specifications.getEncoderTicksPerRotation()) / Math.sqrt(Math.PI) * 1.25);
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
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        holonomicMove(controller.left_stick_x, controller.left_stick_y, controller.right_stick_x);
    }

    private final double AUTONOMOUS_SPEED = 0.1;

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
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void move(double millimeters, double degrees) throws InterruptedException {
        Vec2 point = degreesToPoint(degrees, AUTONOMOUS_SPEED);

        double frontLeftPower = holonomicMath(point.x, point.y, 0.0, Motor.FRONT_LEFT);
        double frontRightPower = holonomicMath(point.x, point.y, 0.0, Motor.FRONT_RIGHT);
        double backLeftPower = holonomicMath(point.x, point.y, 0.0, Motor.BACK_LEFT);
        double backRightPower = holonomicMath(point.x, point.y, 0.0, Motor.BACK_RIGHT);
    }

    @Override
    public void turn(double degrees) throws InterruptedException {
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int position = degreesToEncoderTicks(degrees);

        frontLeft.setTargetPosition(position);
        frontRight.setTargetPosition(position);
        backLeft.setTargetPosition(position);
        backRight.setTargetPosition(position);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(AUTONOMOUS_SPEED);
        frontRight.setPower(AUTONOMOUS_SPEED);
        backLeft.setPower(AUTONOMOUS_SPEED);
        backRight.setPower(AUTONOMOUS_SPEED);

        while(motorsBusy()) {
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
