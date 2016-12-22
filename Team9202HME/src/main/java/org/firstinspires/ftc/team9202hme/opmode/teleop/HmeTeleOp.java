package org.firstinspires.ftc.team9202hme.opmode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.program.HmeTeleOpProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

@TeleOp(name = "HME TeleOp", group = "HME")
public class HmeTeleOp extends OpMode {
    private TeleOpProgram robot = new HmeTeleOpProgram(this);

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
