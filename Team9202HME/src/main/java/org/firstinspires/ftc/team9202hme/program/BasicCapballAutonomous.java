package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.hardware.HardwareConstants;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.HungryRobitsGatherer;

public class BasicCapballAutonomous extends AutonomousProgram {
    public BasicCapballAutonomous(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                new HungryRobitsGatherer(),
                null
        );
    }

    @Override
    public void run() throws InterruptedException {

    }
}
