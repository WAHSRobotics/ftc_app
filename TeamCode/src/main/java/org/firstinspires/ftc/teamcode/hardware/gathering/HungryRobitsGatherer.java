package org.firstinspires.ftc.teamcode.hardware.gathering;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HungryRobitsGatherer extends Gatherer {
    private CRServo left, right;
    private Servo armLeft, armRight;

    @Override
    public void gatherControlled(Gamepad gamepad) {
        if(gamepad.right_trigger >= 0.2) {
            left.setPower(gamepad.right_trigger);
            right.setPower(gamepad.right_trigger);
        } else if(gamepad.left_trigger >= 0.2) {
            left.setPower(-gamepad.left_trigger);
            right.setPower(-gamepad.left_trigger);
        }
    }

    @Override
    public void gather() throws InterruptedException {

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.crservo.get("gather_left");
        right = hardwareMap.crservo.get("gather_right");
        armLeft = hardwareMap.servo.get("gather_arm_left");
        armRight = hardwareMap.servo.get("gather_arm_right");

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        armLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
