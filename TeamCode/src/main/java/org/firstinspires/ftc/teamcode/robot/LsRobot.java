package org.firstinspires.ftc.teamcode.robot;


import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

import static org.firstinspires.ftc.teamcode.robot.Robot.FieldSide.BLUE;

public class LsRobot extends Robot {
    private ColorSensor colorSensor;
final int DISTANCE_TO_BUTTON = 12;

    public LsRobot(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(101.6, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                null,
                null);
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);

        colorSensor = opMode.hardwareMap.colorSensor.get("color");
    }

    @Override
    public void loop() {

    }

    @Override
    public void runAutonomous() throws InterruptedException {

        driveTrain.move(610, 90);
        driveTrain.move(610);
        driveTrain.move(610, 90);
        driveTrain.move(700);

        while (opMode.opModeIsActive()) {
            switch (fieldSide) {
                case RED:
                    while (!(colorSensor.red() - colorSensor.blue() >= 1)) {
                        driveTrain.moveIndefinitely(90);


                    }

                    driveTrain.move(DISTANCE_TO_BUTTON);
                    driveTrain.move(DISTANCE_TO_BUTTON, 180);


                    break;

                case BLUE:
                    while (!(colorSensor.blue() - colorSensor.red() >= 1)) {
                        driveTrain.moveIndefinitely(90);


                    }

                    driveTrain.move(DISTANCE_TO_BUTTON);
                    driveTrain.move(DISTANCE_TO_BUTTON, 180);


                    break;
            }

            }
        }
    }
// if(colorSensor.red() <= 1 && colorSensor.blue() <= 1) {
//                        driveTrain.moveIndefinitely(90);
//                    } else {
//                        driveTrain.stop();
//                        //Measure Distance
//                        if(colorSensor.red() - colorSensor.blue() >= 1) {
//                            driveTrain.move(DISTANCE_TO_BUTTON);
//                            driveTrain.move(12, 180);
//                            driveTrain.moveIndefinitely(90);
//
//                            if(colorSensor.blue() - colorSensor.red() >= 1){
//                                driveTrain.move(191, 270);
//                                driveTrain.move(12);
//                                driveTrain.move(12, 180);
//                                driveTrain.move(1000, 90);
//
//                            }
