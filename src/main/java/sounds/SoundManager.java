package main.java.sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SoundManager{

    private Sound hitSound;
    private Sound moveSound;
    private Sound eatSound;

    public SoundManager(){
        hitSound = new Sound("/audio/sounds/hit.wav");
        moveSound =  new Sound("/audio/sounds/move.wav");
        eatSound = new Sound("/audio/sounds/eat.wav");
    }

    public void playHitsound(){
        playSound(hitSound.soundPath);
    }

    public void playMoveSound(){
        playSound(moveSound.soundPath);
    }

    public void playSoundLoop(String filePath){
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(SoundManager.class.getResource(filePath)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(100);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playEatSound(){
        playSound(eatSound.soundPath);
    }

    public static void playSound(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(SoundManager.class.getResource(filePath)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


}