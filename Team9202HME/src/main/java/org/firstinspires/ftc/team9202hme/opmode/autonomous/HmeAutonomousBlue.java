package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.BasicCapballAutonomous;

@Autonomous(name = "HME Autonomous Blue", group = "HME")
@Disabled
public class HmeAutonomousBlue extends LinearOpMode {
    private AutonomousProgram program = new BasicCapballAutonomous(this, AutonomousProgram.FieldSide.BLUE);

    @Override
    public void runOpMode() throws InterruptedException {
        program.run();
    }
}
