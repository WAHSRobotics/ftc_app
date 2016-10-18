package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Testing", group = "Tests")
public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo1 = hardwareMap.servo.get("s1");
        Servo servo2 = hardwareMap.servo.get("s2");
        Servo servo3 = hardwareMap.servo.get("s3");
        Servo servo4 = hardwareMap.servo.get("s4");

        waitForStart();

        servo1.setPosition(1.0);
        servo2.setPosition(1.0);
        servo3.setPosition(1.0);
        servo4.setPosition(1.0);
    }
}
