package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Ultrasonic Distance Sensor Test", group = "Tests")
@Disabled
public class UltrasonicDistanceSensorTest extends OpMode {
    private ModernRoboticsI2cRangeSensor rangeSensor1, rangeSensor2;

    @Override
    public void init() {
        rangeSensor2 = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "us2");
        rangeSensor1 = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "us1");
    }

    @Override
    public void loop() {
        telemetry.addData("Raw Ultrasonic 1", rangeSensor1.rawUltrasonic());
//        telemetry.addData("Distance (Millimeters)", rangeSensor1.getDistance(DistanceUnit.MM));

        telemetry.addData("Raw Ultrasonic 2", rangeSensor2.rawUltrasonic());
//        telemetry.addData("Distance (Millimeters)", rangeSensor2.getDistance(DistanceUnit.MM));

        telemetry.update();
    }
}
