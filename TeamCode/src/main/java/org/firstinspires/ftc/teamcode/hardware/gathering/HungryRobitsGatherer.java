package org.firstinspires.ftc.teamcode.hardware.gathering;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HungryRobitsGatherer extends Gatherer {
    private CRServo leftArm, rightArm;
    private Servo leftGate, rightGate;

    @Override
    public void gatherControlled(Gamepad gamepad) {
        if(gamepad.right_trigger >= 0.2) {
            leftArm.setPower(gamepad.right_trigger);
            rightArm.setPower(gamepad.right_trigger);
        } else if(gamepad.left_trigger >= 0.2) {
            leftArm.setPower(-gamepad.left_trigger);
            rightArm.setPower(-gamepad.left_trigger);
        }
    }

    @Override
    public void gather() throws InterruptedException {

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        leftArm = hardwareMap.crservo.get("left_arm");
        rightArm = hardwareMap.crservo.get("right_arm");
        leftGate = hardwareMap.servo.get("left_gate");
        rightGate = hardwareMap.servo.get("right_gate");

        leftArm.setDirection(DcMotorSimple.Direction.REVERSE);
        leftGate.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
