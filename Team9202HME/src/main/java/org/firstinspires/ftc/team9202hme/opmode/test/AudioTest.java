package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.R;
import org.firstinspires.ftc.team9202hme.audio.Sound;

@TeleOp(name = "Audio Test", group = "Tests")
//@Disabled
public class AudioTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Sound sound = new Sound();
        sound.load(hardwareMap, R.raw.you_took_the_peepo);

        waitForStart();

        sound.setVolume(1.0f, 1.0f);

        sound.play();
    }
}
