package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.library.hardware.driving.DriveTrain;
import org.firstinspires.ftc.teamcode.library.hardware.driving.TankDrive;
import org.firstinspires.ftc.teamcode.library.hardware.gathering.Gatherer;
import org.firstinspires.ftc.teamcode.library.hardware.shooting.Shooter;

public abstract class Robot {
    protected OpMode opMode;
    protected DriveTrain driveTrain;
    protected Gatherer gatherer;
    protected Shooter shooter;

    protected Robot(OpMode opMode, DriveTrain driveTrain, Gatherer gatherer, Shooter shooter) {
        this.opMode = opMode;
        this.driveTrain = driveTrain;
        this.gatherer = gatherer;
        this.shooter = shooter;
    }

    public abstract void init();

    public void start() {

    }

    public abstract void loop();

    public abstract void runAutonomous();

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
