package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Nathaniel S. Glover on 12/19/2015, at 11:56 AM.
 * FTC Robotics - Loose Screws 4970
 */
public abstract class LSAutonomous extends LinearOpMode {

    protected DcMotor fLeft, fRight, bLeft, bRight;

    protected final float SPEED = 0.6f;
    protected final double INCHES_PER_ROTATION = Math.PI * 4;
    protected final double TURN_CIRCUMFERENCE = Math.PI * 20.5;
    protected final double ROTATIONS_PER_TURN = (TURN_CIRCUMFERENCE/ INCHES_PER_ROTATION) / 2;
    protected final double DEGREES_PER_ROTATION = 360/ROTATIONS_PER_TURN;
    protected final int NEVEREST_PPR = 280;

    protected State currentState;

    protected enum State{
        PARK, STOP;

        private static State[] values = values();

        private State nextState() {return values[(this.ordinal()+1)%values.length];}

        private State previousState() {return values[(this.ordinal()-1)%values.length];}
    }

    protected enum TurnDirection {CLOCKWISE, COUNTER_CLOCKWISE}
    protected enum MoveDirection {FORWARD, BACKWARD}

    protected void initRobot() throws InterruptedException {
        bRight = hardwareMap.dcMotor.get("bLeft");
        bLeft = hardwareMap.dcMotor.get("bRight");
        fRight = hardwareMap.dcMotor.get("fLeft");
        fLeft = hardwareMap.dcMotor.get("fRight");

        currentState = State.PARK;

        bRight.setDirection(DcMotor.Direction.FORWARD);
        bLeft.setDirection(DcMotor.Direction.REVERSE);
        fRight.setDirection(DcMotor.Direction.FORWARD);
        fLeft.setDirection(DcMotor.Direction.REVERSE);

        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        switchToWriteOnly();

        waitForStart();
    }

    protected void timelyMove(int milliseconds, MoveDirection moveDirection) throws InterruptedException {
        if(moveDirection == MoveDirection.FORWARD) {
            fLeft.setPower(SPEED);
            fRight.setPower(SPEED * 0.7);
            bLeft.setPower(SPEED);
            bRight.setPower(SPEED * 0.7);

            printEncoderValues();
        } else if(moveDirection == MoveDirection.BACKWARD) {
            fLeft.setPower(-SPEED);
            fRight.setPower(-SPEED * 0.7);
            bLeft.setPower(-SPEED);
            bRight.setPower(-SPEED * 0.7);

            printEncoderValues();
        }

        sleep(milliseconds);
    }

    protected void timelyTurn(int milliseconds, TurnDirection turnDirection) throws InterruptedException {
        if(turnDirection == TurnDirection.CLOCKWISE) {
            fLeft.setPower(SPEED);
            fRight.setPower(-SPEED);
            bLeft.setPower(SPEED);
            bRight.setPower(-SPEED);
        } else if(turnDirection == TurnDirection.COUNTER_CLOCKWISE) {
            fLeft.setPower(-SPEED);
            fRight.setPower(SPEED);
            bLeft.setPower(-SPEED);
            bRight.setPower(-SPEED);
        }
    }

    protected void move(int targetInches, MoveDirection moveDirection) throws InterruptedException {
        switchToReadOnly();

        double targetPos = (targetInches/ INCHES_PER_ROTATION) * NEVEREST_PPR;

        if (moveDirection == MoveDirection.FORWARD) {
            while((Math.abs(fLeft.getCurrentPosition()) < targetPos && Math.abs(fRight.getCurrentPosition()) < targetPos)
            &&(Math.abs(bLeft.getCurrentPosition()) < targetPos && Math.abs(bRight.getCurrentPosition()) < targetPos)) {

                System.out.println(fLeft.getCurrentPosition() + " " + fRight.getCurrentPosition() +
                        " " + bLeft.getCurrentPosition() + " " + bRight.getCurrentPosition());

                System.out.println("Moving" + targetInches + " Inches Forward...");

                switchToWriteOnly();

                fLeft.setPower(SPEED);
                fRight.setPower(SPEED);
                bLeft.setPower(SPEED);
                bRight.setPower(SPEED);

                printEncoderValues();

                switchToReadOnly();
            }
        } else if (moveDirection == MoveDirection.BACKWARD) {
             while((Math.abs(fLeft.getCurrentPosition()) < targetPos && Math.abs(fRight.getCurrentPosition()) < targetPos)
             &&(Math.abs(bLeft.getCurrentPosition()) < targetPos && Math.abs(bRight.getCurrentPosition()) < targetPos)) {

                System.out.println(fLeft.getCurrentPosition() + " " + fRight.getCurrentPosition() +
                " " + bLeft.getCurrentPosition() + " " + bRight.getCurrentPosition());

                System.out.println("Moving"+targetInches+" Inches Backward...");

                switchToWriteOnly();

                fLeft.setPower(-SPEED);
                fRight.setPower(-SPEED);
                bLeft.setPower(-SPEED);
                bRight.setPower(-SPEED);

                printEncoderValues();

                switchToReadOnly();
             }
        }

        switchToWriteOnly();

        stopMotors();
    }

    protected void turn(int targetDegrees, TurnDirection turnDirection) throws InterruptedException {
        switchToReadOnly();

        double targetPos = (targetDegrees/DEGREES_PER_ROTATION) * NEVEREST_PPR;

        if(turnDirection == TurnDirection.CLOCKWISE) {
            switchToReadOnly();

            while((Math.abs(fLeft.getCurrentPosition()) < targetPos && Math.abs(fRight.getCurrentPosition()) < targetPos)
            ||(Math.abs(bLeft.getCurrentPosition()) < targetPos && Math.abs(bRight.getCurrentPosition()) < targetPos)) {

                System.out.println(fLeft.getCurrentPosition() + " " + fRight.getCurrentPosition() +
                        " " + bLeft.getCurrentPosition() + " " + bRight.getCurrentPosition());

                System.out.println("Turning " + targetDegrees + " Degrees Clockwise...");

                switchToWriteOnly();

                fLeft.setPower(SPEED);
                fRight.setPower(-SPEED);
                bLeft.setPower(SPEED);
                bRight.setPower(-SPEED);

                switchToReadOnly();
            }

            switchToWriteOnly();
        } else if(turnDirection == TurnDirection.COUNTER_CLOCKWISE) {
            switchToReadOnly();

            while((Math.abs(fLeft.getCurrentPosition()) < targetPos && Math.abs(fRight.getCurrentPosition()) < targetPos)
            ||(Math.abs(bLeft.getCurrentPosition()) < targetPos && Math.abs(bRight.getCurrentPosition()) < targetPos)) {

                System.out.println(fLeft.getCurrentPosition() + " " + fRight.getCurrentPosition() +
                        " " + bLeft.getCurrentPosition() + " " + bRight.getCurrentPosition());

                System.out.println("Turning "+targetDegrees+" Degrees Counter-Clockwise...");

                switchToWriteOnly();

                fLeft.setPower(-SPEED);
                fRight.setPower(SPEED);
                bLeft.setPower(-SPEED);
                bRight.setPower(SPEED);

                switchToReadOnly();
            }

            switchToWriteOnly();
        }

        switchToWriteOnly();

        printEncoderValues();

        stopMotors();
    }

    protected void nextState() {currentState = currentState.nextState();}

    protected void previousState() {currentState = currentState.previousState();}

    protected void stopMotors() {
        fLeft.setPower(0);
        fRight.setPower(0);
        bLeft.setPower(0);
        bRight.setPower(0);
    }

    protected void switchToWriteOnly() throws InterruptedException {
//        bLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
//        bRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
//        fLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
//        fRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
//
//        waitOneFullHardwareCycle();
//        waitOneFullHardwareCycle();
//        waitOneFullHardwareCycle();
    }

    protected void switchToReadOnly() throws InterruptedException {
//        bLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
//        bRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
//        fLeft.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
//        fRight.getController().setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
//
//        waitOneFullHardwareCycle();
//        waitOneFullHardwareCycle();
//        waitOneFullHardwareCycle();
    }

    protected void printEncoderValues() throws InterruptedException {
        switchToReadOnly();

        telemetry.addData("Motor", "Encoder Position is " + bLeft.getCurrentPosition());
        telemetry.addData("Motor", "Encoder Position is " + bRight.getCurrentPosition());
        telemetry.addData("Motor", "Encoder Position is " + fLeft.getCurrentPosition());
        telemetry.addData("Motor", "Encoder Position is " + fRight.getCurrentPosition());

        switchToWriteOnly();
    }
}
