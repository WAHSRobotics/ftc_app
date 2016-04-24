package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Nathaniel S. Glover on 12/9/2015, at 10:14 PM.
 * FTC Robotics - Loose Screws 4970
 */
public class LSTeleOp extends OpMode {

    DcMotor fLeft, fRight, bLeft, bRight;

    final float SLOW_MOD_VAL = 0.4f;
    final float SPEED = 1.0f;
    final float FAST_MOD_VAL = 130f;

    public float leftModVal = SPEED;
    public float rightModVal = SPEED;

    @Override
    public void init() {
        bRight = hardwareMap.dcMotor.get("bLeft");
        bLeft = hardwareMap.dcMotor.get("bRight");
        fRight = hardwareMap.dcMotor.get("fLeft");
        fLeft = hardwareMap.dcMotor.get("fRight");

        telemetry.addData("Init","Motors Acquired.");

        bRight.setDirection(DcMotor.Direction.FORWARD);
        bLeft.setDirection(DcMotor.Direction.REVERSE);
        fRight.setDirection(DcMotor.Direction.FORWARD);
        fLeft.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Init", "Motor Reversals Complete.");

        telemetry.addData("Init", "Initiation Complete, ready for activation.");
    }

    @Override
    public void loop() {
        if(telemetry.hasData()) {
            telemetry.clearData();
        }

        if (gamepad1.left_bumper) {
            leftModVal = SLOW_MOD_VAL;
        } else if(gamepad1.left_trigger > 0.50f) {
            leftModVal = FAST_MOD_VAL;
        } else {
            leftModVal = SPEED;
        }

        if(gamepad1.right_bumper) {
            rightModVal = SLOW_MOD_VAL;
        } else if(gamepad1.right_trigger > 0.50f){
            rightModVal = FAST_MOD_VAL;
        } else {
            rightModVal = SPEED;
        }

//        if(gamepad2.left_trigger > 0.05) {
//            arm.setPower(-0.15f);
//        } else if(gamepad2.left_bumper) {
//            arm.setPower(0.15f);
//        }
//
//        if(gamepad2.right_trigger > 0.05) {
//            claw.setPower(-0.08);
//        } else if(gamepad2.right_bumper) {
//            claw.setPower(0.08);
//        }

        fLeft.setPower(scaleMotorPower(gamepad1.left_stick_y * leftModVal));
        bLeft.setPower(scaleMotorPower(gamepad1.left_stick_y * leftModVal));
        fRight.setPower(scaleMotorPower(gamepad1.right_stick_y * rightModVal));
        bRight.setPower(scaleMotorPower(gamepad1.right_stick_y * rightModVal));

        if(gamepad1.dpad_down) {
        fLeft.setPower(1);
        bLeft.setPower(1);
        fRight.setPower(1);
        bRight.setPower(1);
        } else if(gamepad1.dpad_up) {
        fLeft.setPower(-1);
        bLeft.setPower(-1);
        fRight.setPower(-1);
        bRight.setPower(-1);
        }

//        try {
//            switchToReadOnly();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        telemetry.addData("Motor F-L Power", fLeft.getPower());
//        telemetry.addData("Motor F-R Power", fRight.getPower());
//        telemetry.addData("Motor B-L Power", bLeft.getPower());
//        telemetry.addData("Motor B-R Power", bRight.getPower());
//
//        try {
//            switchToWriteOnly();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        telemetry.addData("Left Mod Val", leftModVal);
        telemetry.addData("Right Mod Val", rightModVal);
        }

        private float scaleMotorPower(float power) {

        float scaledPower = 0.0f;

        power = Range.clip(power, -1, 1);
        float[] possiblePowerValues = {
        0.00f, 0.05f, 0.09f, 0.10f, 0.12f,
        0.15f, 0.18f, 0.24f, 0.30f, 0.36f,
        0.43f, 0.50f, 0.60f, 0.72f, 0.85f,
        1.00f, 1.00f
        };

        int powerIndex = (int)(power * 16.0);

        if (powerIndex < 0) {
        powerIndex = -powerIndex;
        } else if (powerIndex > 16) {
        powerIndex = 16;
        }

        if (power < 0) {
        scaledPower = -possiblePowerValues[powerIndex];
        } else {
        scaledPower = possiblePowerValues[powerIndex];
        }

        return scaledPower;
        }

        protected synchronized void switchToWriteOnly() throws InterruptedException {
            bLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            bRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            fLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            fRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);

            wait(10);
        }

        protected synchronized void switchToReadOnly() throws InterruptedException {
            bLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            bRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            fLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            fRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);

        wait(10);
    }
}
