package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.teamcode.hardware.gathering.HungryRobitsGatherer;
import org.firstinspires.ftc.teamcode.hardware.shooting.RollerShooter;

public class HmeBot extends Robot {
    private OpticalDistanceSensor leftOds, rightOds;
    private boolean blue;

    public HmeBot(OpMode opMode, boolean blue) {
        super(opMode,
                new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                new HungryRobitsGatherer(),
                new RollerShooter()
        );

        this.blue = blue;
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
        if(blue) {
            driveTrain.move(990);

            driveTrain.turn(120);

            driveTrain.move(1829);
        } else {
            driveTrain.move(990);

            driveTrain.turn(210);

            driveTrain.move(1829);
        }
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);
        gatherer.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
