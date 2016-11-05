package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.HardwareSpecifications;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.teamcode.hardware.gathering.HungryRobitsGatherer;
import org.firstinspires.ftc.teamcode.hardware.shooting.RollerShooter;

public class HmeBot extends Robot {
    public HmeBot(OpMode opMode) {
        super(opMode,
                new HolonomicDriveTrain(new HardwareSpecifications(152.4, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION, 0.0, 0.0, 0.0)),
                new HungryRobitsGatherer(),
                new RollerShooter()
        );
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        driveTrain.driveControlled(opMode.gamepad1);
        gatherer.gatherControlled(opMode.gamepad2);
        shooter.shootControlled(opMode.gamepad2);

        driveTrain.logTelemetry(opMode.telemetry);
        gatherer.logTelemetry(opMode.telemetry);
        shooter.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }

    @Override
    public void runAutonomous() {
        driveTrain.move(1000);

        driveTrain.move(1000, 45);

        driveTrain.turn(180);

        driveTrain.stop();
    }
}
