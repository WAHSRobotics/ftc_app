package org.firstinspires.ftc.team9202hme.hardware.audio;


import android.media.MediaPlayer;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 *
 *
 * @author Nathaniel Glover
 */
public class Sound {
    private MediaPlayer mediaPlayer;
    private Thread soundThread;
    private boolean isLoaded = false;

    public void load(HardwareMap hardwareMap, int resourceId) {
        mediaPlayer = MediaPlayer.create(hardwareMap.appContext, resourceId);

        soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(mediaPlayer.isPlaying()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        isLoaded = true;
    }

    public void play() {
        mediaPlayer.start();

        soundThread.start();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(looping);
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public void setVolume(float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    public void pause() throws InterruptedException {
        mediaPlayer.pause();

        soundThread.join();
    }

    public void stop() throws InterruptedException {
        mediaPlayer.stop();

        soundThread.join();
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
