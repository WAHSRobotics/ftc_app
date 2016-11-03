package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.driving.DriveTrain;
import org.firstinspires.ftc.teamcode.util.MathUtil;
import org.firstinspires.ftc.teamcode.util.PowerScale;

@TeleOp(name = "Testing Environment", group = "Tests")
public class MotorTest extends OpMode {
    DcMotor one, two, theThirdOne;
    Servo servo;

    @Override
    public void init() {
        one = hardwareMap.dcMotor.get("one");
        two = hardwareMap.dcMotor.get("two");
        theThirdOne = hardwareMap.dcMotor.get("three");
        servo = hardwareMap.servo.get("servo");
    }

    @Override
    public void loop() {
        one.setPower(1);
        two.setPower(1);
        theThirdOne.setPower(1);
        servo.setPosition(1);
    }
}

