package org.firstinspires.ftc.team9202hme.hardware.shooting;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Shooting mechanism composed of two
 * spinning wheels
 *
 * @author John Eichelberger
 */
public class RollerShooter extends Shooter {
    private DcMotor left, right;

    @Override
    public void shootControlled(Gamepad controller) {
        if(controller.left_bumper){
            left.setPower(1);
            right.setPower(1);
        } else {
            left.setPower(0);
            right.setPower(0);
        }
    }

    @Override
    public void shoot() throws InterruptedException {

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
