package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.library.hardware.HardwareSpecifications;
import org.firstinspires.ftc.teamcode.library.hardware.driving.HolonomicDriveTrain;

public class HmeBot extends Robot {
    private HardwareSpecifications specifications = new HardwareSpecifications(152.4, 1120, 0.0, 0.0, 0.0);

    public HmeBot(OpMode opMode) {
        super(opMode, new HolonomicDriveTrain(), null, null);
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
    public void runAutonomous() {
        driveTrain.move(1000);

        driveTrain.move(1000, 45);

        driveTrain.turn(180);

        driveTrain.stop();
    }
}
