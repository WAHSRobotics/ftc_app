package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.teamcode.hardware.gathering.HungryRobitsGatherer;
import org.firstinspires.ftc.teamcode.hardware.shooting.RollerShooter;

public class HmeBot extends Robot {
    private OpticalDistanceSensor leftOds, rightOds;

    public HmeBot(OpMode opMode, FieldSide side) {
        super(opMode, side,
                new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                new HungryRobitsGatherer(),
                new RollerShooter()
        );
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
        gatherer.init(opMode.hardwareMap);

        leftOds = opMode.hardwareMap.opticalDistanceSensor.get("leftOds");
        rightOds = opMode.hardwareMap.opticalDistanceSensor.get("rightOds");
    }

    @Override
    public void loop() {
        driveTrain.driveControlled(opMode.gamepad1);
        gatherer.gatherControlled(opMode.gamepad1);

        updateTelemetry();
    }

    @Override
    public void runAutonomous() throws InterruptedException {

    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);
        gatherer.logTelemetry(opMode.telemetry);

        opMode.telemetry.addData("Left ODS", leftOds.getLightDetected() * 10000);
        opMode.telemetry.addData("Right ODS", rightOds.getLightDetected() * 10000);

        opMode.telemetry.update();
    }
}
