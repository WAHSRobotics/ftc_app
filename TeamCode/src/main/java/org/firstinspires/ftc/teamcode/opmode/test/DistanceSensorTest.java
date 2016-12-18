package org.firstinspires.ftc.teamcode.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@TeleOp(name = "Distance Sensor Test", group = "Tests")
//@Disabled
public class DistanceSensorTest extends OpMode {
    private OpticalDistanceSensor leftOds;
    private OpticalDistanceSensor rightOds;

    @Override
    public void init() {
        leftOds = hardwareMap.opticalDistanceSensor.get("leftOds");
        rightOds = hardwareMap.opticalDistanceSensor.get("rightOds");
    }

    @Override
    public void loop() {
        telemetry.addData("Left ODS", leftOds.getLightDetected() * 10000);
        telemetry.addData("Right ODS", rightOds.getLightDetected() * 10000);

        telemetry.update();
    }
}
