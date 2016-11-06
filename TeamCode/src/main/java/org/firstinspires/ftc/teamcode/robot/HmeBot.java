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
                new HolonomicDriveTrain(new HardwareSpecifications(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION, 533.4)),
                /*new HungryRobitsGatherer()*/null,
                /*new RollerShooter()*/null
        );
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        driveTrain.driveControlled(opMode.gamepad1);

        driveTrain.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        driveTrain.turn(360);
    }
}
