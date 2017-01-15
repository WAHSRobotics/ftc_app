package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.Gatherer;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;

public abstract class TeleOpProgram {
    protected OpMode opMode;

    protected TeleOpProgram(OpMode opMode) {
        this.opMode = opMode;
    }

    public abstract void init();

    public void start() {

    }

    public abstract void loop();

    public void stop() {

    }
}
