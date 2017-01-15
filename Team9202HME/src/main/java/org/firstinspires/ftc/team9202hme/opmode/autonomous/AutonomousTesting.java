package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.autonomous.ImageAlignmentTest;

@Autonomous(name = "Image Alignment Testing")
public class AutonomousTesting extends LinearOpMode {
    private AutonomousProgram program = new ImageAlignmentTest(this, AutonomousProgram.FieldSide.RED);

    @Override
    public void runOpMode() throws InterruptedException {
        program.run();
    }
}
