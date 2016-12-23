package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.hardware.HardwareConstants;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.PhoneCamera;

public class AutonomousTestingProgram extends AutonomousProgram {
    public AutonomousTestingProgram(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                null,
                null);
    }

    @Override
    public void run() throws InterruptedException {
        Navigator navigator = new Navigator(PhoneCamera.BACK, 4, true);

        driveTrain.init(opMode.hardwareMap);
//        navigator.init();

        opMode.waitForStart();

        final double AUTONOMOUS_SPEED = 0.2;

        driveTrain.turn(AUTONOMOUS_SPEED, 90);
        driveTrain.turn(AUTONOMOUS_SPEED, -90);
    }
}
