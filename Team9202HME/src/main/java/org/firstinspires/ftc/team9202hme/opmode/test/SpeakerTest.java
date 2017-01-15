package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.hardware.audio.Sound;
import org.firstinspires.ftc.team9202hme.hardware.audio.Speaker;


public class SpeakerTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Speaker speaker = new Speaker();

        speaker.init(hardwareMap);

        waitForStart();

        speaker.playSound(Sound.YOU_TOOK_THE_PEEPO, 1.0f);
        Thread.sleep(1000);
    }
}
