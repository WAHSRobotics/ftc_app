package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Ultrasonic Distance Sensor Test", group = "Tests")
//@Disabled
public class UltrasonicDistanceSensorTest extends OpMode {
    private ModernRoboticsI2cRangeSensor rangeSensor;

    @Override
    public void init() {
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "ultrasonic");
    }

    @Override
    public void loop() {
        telemetry.addData("Raw Ultrasonic", rangeSensor.rawUltrasonic());
        telemetry.addData("Distance (Millimeters)", rangeSensor.getDistance(DistanceUnit.MM));
        telemetry.update();
    }
}
