package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.driving.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.gathering.Gatherer;
import org.firstinspires.ftc.teamcode.hardware.sensory.SensorSet;
import org.firstinspires.ftc.teamcode.hardware.shooting.Shooter;

public abstract class Robot {
    protected LinearOpMode opMode;
    protected DriveTrain driveTrain;
    protected Gatherer gatherer;
    protected Shooter shooter;
    protected SensorSet sensorSet;

    public Robot(LinearOpMode opMode, DriveTrain driveTrain, Gatherer gatherer, Shooter shooter, SensorSet sensorSet) {
        this.opMode = opMode;
        this.driveTrain = driveTrain;
        this.gatherer = gatherer;
        this.shooter = shooter;
        this.sensorSet = sensorSet;
    }

    public void run() throws InterruptedException {
        init();

        opMode.waitForStart();

        start();

        if(opMode.getClass().isAnnotationPresent(TeleOp.class)) {
            while(opMode.opModeIsActive()) {
                loop();

                if(opMode.isStopRequested()) {
                    stop();
                }
            }
        } else if(opMode.getClass().isAnnotationPresent(Autonomous.class)) {
            runAutonomous();

            stop();
        }
    }

    protected abstract void init() throws InterruptedException;

    protected void start() throws InterruptedException {

    }

    protected abstract void loop() throws InterruptedException;

    protected abstract void runAutonomous() throws InterruptedException;

    protected void stop() throws InterruptedException {

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

    public SensorSet getSensorSet() {
        return sensorSet;
    }
}
