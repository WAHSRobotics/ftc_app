package org.firstinspires.ftc.teamcode.hardware.shooting;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RollerShooter extends Shooter {
    private DcMotor left, right;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.dcMotor.get("shooter_left");
        right = hardwareMap.dcMotor.get("shooter_right");
    }

    @Override
    public void shootControlled(Gamepad gamepad) {
        if(gamepad.right_trigger >= 0.2) {
            setMotorPowers(1.0);
        } else {
            setMotorPowers(0.0);
        }
    }

    private void setMotorPowers(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    @Override
    public void shoot() {
        setMotorPowers(1.0);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setMotorPowers(0.0);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
