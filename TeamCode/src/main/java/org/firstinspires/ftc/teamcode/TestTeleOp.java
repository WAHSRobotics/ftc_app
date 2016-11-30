package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "Test TeleOp")
public class TestTeleOp extends OpMode {

    private LSRobit robot = new LSRobit();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.buttonPusher.setPosition(80/180);

        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightBack.setPower(0);
    }

    public double scale(double inPower) {
        return inPower; //todo: make a real scale method
    }

    public void loop() {
        double y = gamepad1.right_stick_y;
        double x = gamepad1.right_stick_x;
        double z = 0.0;

        if (gamepad1.right_bumper) {
            robot.buttonPusher.setPosition(10/180);
        } else {
            robot.buttonPusher.setPosition(80/180);
        }

        if (gamepad1.left_trigger > 0.0) {
            z = gamepad1.left_trigger;
        } else if (gamepad1.right_trigger > 0.0) {
            z = -gamepad1.right_trigger;
        }

        robot.rightFront.setPower(scale(+ y - x + z));
        robot.leftFront.setPower(scale(- y - x + z));
        robot.rightBack.setPower(scale(+ y + x + z));
        robot.leftBack.setPower(scale(- y + x + z));
    }

}
