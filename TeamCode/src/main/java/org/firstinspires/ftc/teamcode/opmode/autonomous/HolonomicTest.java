package org.firstinspires.ftc.teamcode.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.HolonomicTestBot;
import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name = "HME Holonomic Auto Test", group = "HME/Tests")
//@Disabled
public class HolonomicTest extends OpMode {
    private Robot robot = new HolonomicTestBot(this);

    @Override
    public void init() {
        robot.init();
    }

    @Override
    public void loop() {
        robot.loopAutonomous();
    }
}
