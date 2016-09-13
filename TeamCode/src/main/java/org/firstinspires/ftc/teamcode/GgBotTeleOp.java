package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "GG Bot TeleOp", group = "Old Robots")
public class GgBotTeleOp extends OpMode {
    final double POWER_MIN = 0.05;
    final double POWER_MAX = 1.00;
    final double POWER_INTERVAL = 0.05;

    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
    }

    @Override
    public void loop() {
        leftMotor.setPower(scalePower(gamepad1.left_stick_y));
        rightMotor.setPower(scalePower(gamepad1.right_stick_y));
    }

    public double scalePower(double power) {
        double scaledPower;

        if(POWER_MIN <= power && power <= POWER_MAX) {
            scaledPower = ((int) (power / POWER_INTERVAL)) * POWER_INTERVAL;

            scaledPower = Math.round(scaledPower * 10000.0) / 10000.0;
        } else {
            scaledPower = 0.0;
        }

        return scaledPower;
    }
}
