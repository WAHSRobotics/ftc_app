package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.hardware.HardwareConstants;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;

public class TestingAutonomousProgram extends AutonomousProgram {
    public TestingAutonomousProgram(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                null,
                null);
    }

    @Override
    public void run() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);

        opMode.waitForStart();

        final double AUTONOMOUS_SPEED = 0.1;

        driveTrain.turn(AUTONOMOUS_SPEED, 180);
    }
}
