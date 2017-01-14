package org.firstinspires.ftc.teamcode.robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

public class LsRobot extends Robot {



    private ColorSensor colorSensor;
    private GyroSensor gyroSensor;

    public LsRobot(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(101.6, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION), null, null);
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
        colorSensor.enableLed(false);
        gyroSensor.calibrate();
        final int DISTANCE_TO_BUTTON = 163;
        switch (fieldSide) {
            case RED:
        while(opMode.opModeIsActive()){

        while(gyroSensor.isCalibrating()){

                Thread.sleep(1);
        }
            driveTrain.move(1000,90);

            if ((30 >= gyroSensor.getHeading()) || (gyroSensor.getHeading() < 8)){
                driveTrain.rightPowerInc(.2);
            }
            else if((gyroSensor.getHeading()) <359 || (gyroSensor.getHeading())>329 ){

                driveTrain.leftPowerInc(.2);
            }









        }
               ;






//                while(!(colorSensor.red() - colorSensor.blue() >= 1)){
//                    driveTrain.moveIndefinitely(270);
//                }
//
//                while ((colorSensor.red() - colorSensor.blue() >= 1)){
//
//                    driveTrain.move(DISTANCE_TO_BUTTON);
//                    driveTrain.move(20,270);
//                    driveTrain.move(DISTANCE_TO_BUTTON);
//                    driveTrain.move(20,270);





                //}




                break;

            case BLUE:

                while (!(colorSensor.blue() - colorSensor.red() >= 1)) {
                    driveTrain.moveIndefinitely(90);
                    opMode.telemetry.addData("Autonomous", "Looking for beacon");
                    opMode.telemetry.update();
                    Thread.sleep(5);
                }
                while(colorSensor.blue() - colorSensor.red() >= 1){

                    driveTrain.move(30,90);
                    driveTrain.move(DISTANCE_TO_BUTTON);
                    driveTrain.move(DISTANCE_TO_BUTTON, 180);
                }
                break;
        }
    }
}
