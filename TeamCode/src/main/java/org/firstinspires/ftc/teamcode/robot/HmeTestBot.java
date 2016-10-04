package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.identification.HardwareIdentifier;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

public class HmeTestBot extends Robot {
    public HmeTestBot(LinearOpMode opMode) {
        super(opMode, new HolonomicDriveTrain(), null, null, null);
    }

    @Override
    protected void init() throws InterruptedException {
        HardwareIdentifier identifier = new HardwareIdentifier(opMode.hardwareMap);

        driveTrain.init(identifier);
    }

    @Override
    protected void loop() throws InterruptedException {
        driveTrain.driveControlled(opMode.gamepad1);

        driveTrain.logTelemetry(opMode.telemetry);
    }

    @Override
    protected void runAutonomous() throws InterruptedException {

    }
}
