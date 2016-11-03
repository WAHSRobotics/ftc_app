package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Testing", group = "Tests")
public class ServoTest extends OpMode {
    CRServo left, right;

    @Override
    public void init() {
        left = hardwareMap.crservo.get("s_left");
        right = hardwareMap.crservo.get("s_right");

        left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if(gamepad1.left_trigger > 0.1) {
            left.setPower(-gamepad1.left_trigger);
            right.setPower(-gamepad1.left_trigger);
        }
        else if(gamepad1.right_trigger > 0.1) {
            left.setPower(gamepad1.right_trigger);
            right.setPower(gamepad1.right_trigger);
        }
        else {
            left.setPower(0);
            right.setPower(0);
        }
    }
}
