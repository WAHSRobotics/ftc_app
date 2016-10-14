package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.HolonomicTestBot;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "HME Holonomic Drive Test", group = "HME/Tests")
//@Disabled
public class HolonomicTest extends OpMode {
    private Robot robot = new HolonomicTestBot(this);

    @Override
    public void init() {
        robot.init();
    }

    @Override
    public void start() {
        robot.start();
    }

    @Override
    public void loop() {
        robot.loop();
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
