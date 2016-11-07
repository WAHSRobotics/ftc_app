package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

public class HmeBot extends Robot {
    public HmeBot(OpMode opMode) {
        super(opMode,
                new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
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
        driveTrain.move(1000);
        driveTrain.stop();

        Thread.sleep(100);

        driveTrain.move(1000, 45);
    }
}
