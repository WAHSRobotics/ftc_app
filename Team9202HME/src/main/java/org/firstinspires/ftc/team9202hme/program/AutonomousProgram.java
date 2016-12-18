package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.Gatherer;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;

public abstract class AutonomousProgram {
    public enum FieldSide {
        RED, BLUE
    }

    protected LinearOpMode opMode;
    protected FieldSide fieldSide;
    protected DriveTrain driveTrain;
    protected Gatherer gatherer;
    protected Shooter shooter;

    protected AutonomousProgram(LinearOpMode opMode, FieldSide fieldSide, DriveTrain driveTrain, Gatherer gatherer, Shooter shooter) {
        this.opMode = opMode;
        this.fieldSide = fieldSide;
        this.driveTrain = driveTrain;
        this.gatherer = gatherer;
        this.shooter = shooter;
    }

    public abstract void run() throws InterruptedException;

    public DriveTrain getDriveTrain() {
        return driveTrain;
    }

    public Gatherer getGatherer() {
        return gatherer;
    }

    public Shooter getShooter() {
        return shooter;
    }
}
