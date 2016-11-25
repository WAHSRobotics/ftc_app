package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

@TeleOp(name = "Gyro No Worko", group = "Things that don't work")
public class GyroTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GyroSensor gyro = hardwareMap.gyroSensor.get("gyro");

        waitForStart();

        gyro.calibrate();

        while(gyro.isCalibrating()) {
            Thread.sleep(1);
        }

        while(opModeIsActive()) {
            telemetry.addData("Gyro X", gyro.rawX());
            telemetry.addData("Gyro Y", gyro.rawY());
            telemetry.addData("Gyro Z", gyro.rawZ());
            telemetry.addData("Heading", gyro.getHeading());

            telemetry.update();
        }
    }
}
