package org.firstinspires.ftc.team9202hme.hardware.gathering;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Gatherer made for robots using the "Hungry Hungry Robits"
 * gathering mechanism, designed by James Krasner
 *
 * @author Sage Wibberley
 */
public class HungryRobitsGatherer extends Gatherer {
    private CRServo leftArm, rightArm;
    private Servo leftGate, rightGate;

    @Override
    public void gatherControlled(Gamepad controller) {
        if(controller.right_trigger >= 0.2) {
            leftArm.setPower(controller.right_trigger);
            rightArm.setPower(controller.right_trigger);
        } else if(controller.left_trigger >= 0.2) {
            leftArm.setPower(-controller.left_trigger);
            rightArm.setPower(-controller.left_trigger);
        } else {
            leftArm.setPower(0);
            rightArm.setPower(0);
        }

        if(controller.left_bumper) {
            leftGate.setPosition(1.0);
            rightGate.setPosition(1.0);
        } else if(controller.right_bumper) {
            leftGate.setPosition(0.0);
            rightGate.setPosition(0.0);
        } else {
            leftGate.setPosition(0.8);
            rightGate.setPosition(0.8);
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

        leftGate.setPosition(0.1);
        rightGate.setPosition(0.1);

        leftArm.setPower(0);
        rightArm.setPower(0);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
