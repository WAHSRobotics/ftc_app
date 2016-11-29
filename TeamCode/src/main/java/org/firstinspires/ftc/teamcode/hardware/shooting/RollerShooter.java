package org.firstinspires.ftc.teamcode.hardware.shooting;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RollerShooter extends Shooter {
    private DcMotor left, right;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.dcMotor.get("shootLeft");
        right = hardwareMap.dcMotor.get("shootRight");
    }

    @Override
    public void shootControlled(Gamepad gamepad) {
        if(gamepad.a) {
            fire(1.0);
        } else {
            fire(0.0);
        }
    }

    private void fire(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    @Override
    public void shoot() throws InterruptedException {
        fire(1.0);

        Thread.sleep(500);

        fire(0.0);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
