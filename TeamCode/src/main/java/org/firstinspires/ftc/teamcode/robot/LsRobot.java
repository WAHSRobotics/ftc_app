package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.driving.HolonomicDriveTrain;

public class LsRobot extends Robot {
    public LsRobot(OpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(101.6, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                null,
                null);
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {

    }

    @Override
    public void runAutonomous() throws InterruptedException {
        driveTrain.move(5000);
    }
}
