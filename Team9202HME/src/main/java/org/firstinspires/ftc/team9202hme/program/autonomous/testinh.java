package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.opmode.test.MovementTest;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.autonomous.ImageAlignmentTest;

@Autonomous(name = "MovementTest")
public class testinh extends LinearOpMode {
    private AutonomousProgram program = new MovementTest(this, AutonomousProgram.FieldSide.RED);

    @Override
    public void runOpMode() throws InterruptedException {
        program.run();
    }
}
