package org.firstinspires.ftc.team9202hme.program.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.Gatherer;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;
import org.firstinspires.ftc.team9202hme.hardware.shooting.RollerShooter;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

public class HmeTeleOpProgram extends TeleOpProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
    private RollerShooter shooter = new RollerShooter();

    public HmeTeleOpProgram(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
        shooter.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        driveTrain.driveControlled(opMode.gamepad1);
        shooter.shootControlled(opMode.gamepad1);

        updateTelemetry();
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
