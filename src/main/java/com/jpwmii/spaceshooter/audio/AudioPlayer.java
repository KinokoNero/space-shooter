package com.jpwmii.spaceshooter.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.util.Objects;

public class AudioPlayer {
    public static void playBackgroundMusic() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(Objects.requireNonNull(AudioPlayer.class.getResourceAsStream("/sounds/background-music.wav")));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playLaserSound() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(Objects.requireNonNull(AudioPlayer.class.getResourceAsStream("/sounds/laser.wav")));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playExplosionSound() {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(Objects.requireNonNull(AudioPlayer.class.getResourceAsStream("/sounds/explosion.wav")));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
