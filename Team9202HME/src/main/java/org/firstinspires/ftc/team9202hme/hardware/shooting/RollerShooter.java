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
    private DcMotor left, right, lift, spinner;

    private final double SHOOT_POWER = 0.7;

    @Override
    public void shootControlled(Gamepad controller) {
        if(controller.right_trigger > 0.1){
            left.setPower(SHOOT_POWER);
            right.setPower(SHOOT_POWER);
        } else {
            left.setPower(0);
            right.setPower(0);
        }

        if(controller.right_trigger > 0.1) {
            lift.setPower(1);
        } else {
            lift.setPower(0);
        }

        if(controller.left_trigger > 0.1) {
            spinner.setPower(1);
        } else {
            spinner.setPower(0);
        }
    }

    @Override
    public void shoot() throws InterruptedException {
        left.setPower(SHOOT_POWER);
        right.setPower(SHOOT_POWER);

        Thread.sleep(500);

        lift.setPower(1);
        spinner.setPower(1);

        Thread.sleep(2000);

        left.setPower(0);
        right.setPower(0);

        lift.setPower(0);
        spinner.setPower(0);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        lift = hardwareMap.dcMotor.get("lift");
        spinner = hardwareMap.dcMotor.get("spinner");
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
