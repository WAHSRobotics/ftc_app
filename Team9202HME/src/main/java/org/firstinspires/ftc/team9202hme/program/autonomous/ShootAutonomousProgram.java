package org.firstinspires.ftc.team9202hme.program.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.hardware.shooting.RollerShooter;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;


public class ShootAutonomousProgram extends AutonomousProgram {
    /**
     * Initializes protected members so that
     * they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     */
    public ShootAutonomousProgram(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide);
    }

    @Override
    public void run() throws InterruptedException {
        Shooter shooter = new RollerShooter();
        shooter.init(opMode.hardwareMap);

        opMode.waitForStart();

        shooter.shoot();
    }
}
