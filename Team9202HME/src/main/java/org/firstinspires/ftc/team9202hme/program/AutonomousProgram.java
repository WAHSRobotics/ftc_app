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

    protected AutonomousProgram(LinearOpMode opMode, FieldSide fieldSide) {
        this.opMode = opMode;
        this.fieldSide = fieldSide;
    }

    public abstract void run() throws InterruptedException;
}
