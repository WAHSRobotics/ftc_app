package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.autonomous.BeaconAutonomousProgram;

@Autonomous(name = "HME Autonomous Blue")
public class HmeAutonomousBlue extends LinearOpMode {
    private AutonomousProgram program = new BeaconAutonomousProgram(this, AutonomousProgram.FieldSide.BLUE);

    @Override
    public void runOpMode() throws InterruptedException {
        program.run();
    }
}
