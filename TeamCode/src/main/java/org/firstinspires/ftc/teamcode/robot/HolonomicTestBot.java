package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareSpecifications;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

public class HolonomicTestBot extends Robot {
    public HolonomicTestBot(OpMode opMode) {
        super(opMode, new HolonomicDriveTrain(new HardwareSpecifications(152.4, 1120, 0.0, 0.0, 0.0)), null, null);
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
    public void loopAutonomous() {
        driveTrain.move(1000);

        driveTrain.move(1000, 90);

        driveTrain.turn(60);

        driveTrain.stop();
    }
}
