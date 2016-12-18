package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.Gatherer;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;

public abstract class TeleOpProgram {
    protected OpMode opMode;
    protected DriveTrain driveTrain;
    protected Gatherer gatherer;
    protected Shooter shooter;

    protected TeleOpProgram(OpMode opMode, DriveTrain driveTrain, Gatherer gatherer, Shooter shooter) {
        this.opMode = opMode;
        this.driveTrain = driveTrain;
        this.gatherer = gatherer;
        this.shooter = shooter;
    }

    public abstract void init();

    public void start() {

    }

    public abstract void loop();

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
