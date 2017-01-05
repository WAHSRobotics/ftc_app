package org.firstinspires.ftc.teamcode.robot;


import android.widget.Button;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;
import static org.firstinspires.ftc.teamcode.robot.Robot.FieldSide.BLUE;

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
        colorSensor.enableLed(false);



        final int DISTANCE_TO_BUTTON = 163;

        switch (fieldSide) {
            case RED:

                driveTrain.move(610, 90);
                driveTrain.move(610);
                driveTrain.move(610, 90);
                driveTrain.move(850);





                do {
                    driveTrain.move(10000,90);
                    opMode.telemetry.addData("Autonomous", "Looking for beacon...");
                    opMode.telemetry.addData("Red", colorSensor.red());
                    opMode.telemetry.addData("Blue", colorSensor.blue());
                    opMode.telemetry.update();
                    Thread.sleep(5);
                } while (!(colorSensor.red() - colorSensor.blue() >= 1));

                driveTrain.move(50, 90);
                driveTrain.move(DISTANCE_TO_BUTTON);

                driveTrain.move(DISTANCE_TO_BUTTON, 180);


                break;

            case BLUE:
                while (!(colorSensor.blue() - colorSensor.red() >= 1)) {
                    driveTrain.moveIndefinitely(90);
                    opMode.telemetry.addData("Autonomous", "Looking for beacon");
                    opMode.telemetry.update();
                    Thread.sleep(5);

                }

                driveTrain.move(30,90);
                driveTrain.move(DISTANCE_TO_BUTTON);
                driveTrain.move(DISTANCE_TO_BUTTON, 180);


                break;
        }
    }
}
