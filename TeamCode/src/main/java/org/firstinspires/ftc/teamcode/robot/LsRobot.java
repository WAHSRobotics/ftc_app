package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

public class LsRobot extends Robot {
    private ColorSensor colorSensor;

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
//        driveTrain.moveIndefinitely(610, 90);
//
//        driveTrain.moveIndefinitely(610);
//
//        driveTrain.moveIndefinitely(610, 90);
//
//        driveTrain.moveIndefinitely(700);

        while(opMode.opModeIsActive()) {
            switch(fieldSide) {
                case RED:
//                    driveTrain.moveIndefinitely(1000, 90);
                    if(colorSensor.red() <= 1 && colorSensor.blue() <= 1) {
                        driveTrain.moveIndefinitely(90);
                    } else {
                        driveTrain.stop();
                        if(colorSensor.red() - colorSensor.blue() >= 1) {
                            driveTrain.move(12);
                            driveTrain.move(12, 180);
                            driveTrain.moveIndefinitely(90);

                            if(colorSensor.blue() - colorSensor.red() >= 1){
                                driveTrain.move(191, 270);
                                driveTrain.move(12);
                                driveTrain.move(12, 180);
                                driveTrain.move(1000, 90);

                            }
                        }
                    }
                    break;
                case BLUE:


                    break;
            }
        }
    }
}
