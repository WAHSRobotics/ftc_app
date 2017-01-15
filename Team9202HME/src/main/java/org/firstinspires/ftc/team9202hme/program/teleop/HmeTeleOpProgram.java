package org.firstinspires.ftc.team9202hme.program.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.team9202hme.hardware.HardwareConstants;
import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.Gatherer;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

public class HmeTeleOpProgram extends TeleOpProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(HardwareConstants.MM_OMNI_WHEEL_DIAMETER, HardwareConstants.ENCODER_TICKS_PER_ROTATION);

    public HmeTeleOpProgram(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        driveTrain.driveControlled(opMode.gamepad1);

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
