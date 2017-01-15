package org.firstinspires.ftc.team9202hme.hardware.audio;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.R;
import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Speaker extends HardwareComponent {
    private SoundPool soundPool;
    private HardwareMap hardwareMap;

    private Map<Sound, Integer> soundMap = new HashMap<>();

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        //noinspection deprecation
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    public void playSound(Sound sound, float volume) {
        if(soundMap.containsKey(sound)) {
            soundPool.play(soundMap.get(sound), volume, volume, 1, 0, 1);
        } else {
            soundMap.put(sound, soundPool.load(hardwareMap.appContext, sound.getResourceId(), 1));
            soundPool.play(soundMap.get(sound), volume, volume, 1, 0, 1);
        }
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        
    }
}
