package org.firstinspires.ftc.team9202hme.hardware.audio;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Hardware interface for playing sounds through the
 * robot controller phone's speakers. However, due to
 * an error in the brain of the author, it is only capable
 * of playing sounds directly from or relating
 * to Nanalan, a Canadian children's television show
 *
 * @author Nathaniel Glover
 */
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

    /**
     * Plays the desired sound at the desired volume through
     * the robot controller phone's speakers
     *
     * @param sound The sound that will be played. To ensure
     *              that only Nanalan is used, just use the
     *              pre-made static final instances of Sound,
     *              such as Sound.YOU_TOOK_THE_PEEPO
     * @param volume The volume at which the sound will be
     *               played. Should be a value from 0.0 to
     *               1.0, where 1.0 is maximum volume
     *
     * @see Sound
     */
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
