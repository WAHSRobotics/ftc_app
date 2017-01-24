package org.firstinspires.ftc.teamcode.opmode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.firstinspires.ftc.teamcode.opmode.teleop.GGRobit;

@TeleOp(name = "LSTeleOp")
public class GGTeleop extends OpMode {
    private GGRobit robot = new GGRobit();
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.leftfront.setPower(0.0);
        robot.rightfront.setPower(0.0);
        robot.leftback.setPower(0.0);
        robot.rightback.setPower(0.0);
    }



    public double scale(double inPower) {
        float scaledPower = 0.0f;

        inPower = Range.clip(inPower, -1, 1);
        float[] possiblePowerValues = {
                0.00f, 0.05f, 0.09f, 0.10f, 0.12f,
                0.15f, 0.18f, 0.24f, 0.30f, 0.36f,
                0.43f, 0.50f, 0.60f, 0.72f, 0.85f,
                1.00f, 1.00f

        };

        int powerIndex = (int)(inPower * 16.0);

        if (powerIndex < 0) {
            powerIndex = -powerIndex;
        } else if (powerIndex > 16) {
            powerIndex = 16;
        }if (inPower < 0) {
            scaledPower = -possiblePowerValues[powerIndex];
        } else {
            scaledPower = possiblePowerValues[powerIndex];
        }
        return scaledPower;
    }
    public void loop() {

        robot.rightfront.setPower(scale(gamepad1.left_stick_y));
        robot.leftfront.setPower(scale(gamepad1.right_stick_y));
        robot.rightback.setPower(scale(gamepad1.right_stick_y));
        robot.leftback.setPower(scale(gamepad1.left_stick_y));
    }
}
