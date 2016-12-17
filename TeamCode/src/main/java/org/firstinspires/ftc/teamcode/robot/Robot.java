package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.driving.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.gathering.Gatherer;
import org.firstinspires.ftc.teamcode.hardware.shooting.Shooter;

public abstract class Robot {
    protected LinearOpMode opMode;
    protected DriveTrain driveTrain;
    protected Gatherer gatherer;
    protected Shooter shooter;
    protected FieldSide fieldSide;

    public enum FieldSide {
        RED, BLUE
    }

    protected Robot(LinearOpMode opMode, FieldSide fieldSide, DriveTrain driveTrain, Gatherer gatherer, Shooter shooter) {
        this.opMode = opMode;
        this.fieldSide = fieldSide;
        this.driveTrain = driveTrain;
        this.gatherer = gatherer;
        this.shooter = shooter;
    }

    public abstract void init();

    public void start() {

    }

    public abstract void loop();

    public abstract void runAutonomous() throws InterruptedException;

    public void stop() {

    }

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
