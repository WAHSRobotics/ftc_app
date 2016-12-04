package org.firstinspires.ftc.teamcode.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "Color Sensor Test", group = "Tests")
//@Disabled
public class ColorSensorTest extends OpMode {
    private ColorSensor colorSensor;

    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("color");
    }

    @Override
    public void loop() {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());

        boolean isRed = false, isBlue = false;

        if(colorSensor.red() - colorSensor.blue() > 2) {
            isRed = true;
        } else if(colorSensor.blue() - colorSensor.red() > 2) {
            isBlue = true;
        }

        telemetry.addData("Red Beacon", isRed);
        telemetry.addData("Blue Beacon", isBlue);
    }
}
