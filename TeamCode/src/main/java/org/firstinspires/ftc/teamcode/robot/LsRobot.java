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
        gyroSensor = opMode.hardwareMap.gyroSensor.get("gyro");
    }

    @Override
    public void loop() {

    }

    @Override
    public void runAutonomous() throws InterruptedException {
        //Disable color sensor LED so the light reflecting off of the beacon's surface doesn't interfere with color sensor
        colorSensor.enableLed(false);

        //Calibrate gyro, and pause the thread while it is calibrating (takes about 3-4 seconds)
        gyroSensor.calibrate();
        while(gyroSensor.isCalibrating()){
            Thread.sleep(5);
        }

        //Move forward off of the wall
        driveTrain.move(500, 0);

        switch (fieldSide) {
            //If on red side...
            case RED:
                //Move left to the ramp, turn towards it, and drive up it
                driveTrain.move(1000, 90);
                driveTrain.turn(-135, 0.5);
                driveTrain.move(500,0);

                break;
            //If on blue side...
            case BLUE:
                //Move right to the ramp, turn towards it, and drive up it
                driveTrain.move(1000, 270);
                driveTrain.turn(135, 0.5);
                driveTrain.move(500,270);

                break;
        }
    }
}
