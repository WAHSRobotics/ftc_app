package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

/**
 * Created by Sage on 2/8/2017.
 */

@Autonomous(name = "MovementTest")

public class MovementTest extends AutonomousProgram {
    /**
     * Initializes protected members so that
     * they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     */
    public MovementTest(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide);
    }

    @Override
    public void run() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
        driveTrain.init(opMode.hardwareMap);

        opMode.waitForStart();

        driveTrain.moveAndTurn(.3, 180, .2);
        Thread.sleep(6000);
    }
}
