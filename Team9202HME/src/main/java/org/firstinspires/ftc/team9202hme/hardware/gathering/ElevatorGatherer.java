package org.firstinspires.ftc.team9202hme.hardware.gathering;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Gathering mechanism that uses a zip tie spinner and
 * a vertical shaft to collect particles and bring them
 * them to the shooter
 *
 * @author John Eichelberger
 */
public class ElevatorGatherer extends Gatherer {
    private DcMotor lift, spinner;

    @Override
    public void gatherControlled(Gamepad controller) {
        if(controller.left_trigger > 0.1) {
            spinner.setPower(1);
        } else {
            spinner.setPower(0);
        }

        if(controller.right_trigger > 0.1) {
            lift.setPower(1);
        } else {
            lift.setPower(0);
        }
    }

    @Override
    public void gather() throws InterruptedException {

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        lift = hardwareMap.dcMotor.get("lift");
        spinner = hardwareMap.dcMotor.get("spinner");
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
