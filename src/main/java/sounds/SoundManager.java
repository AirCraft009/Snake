package main.java.sounds;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {


    public void playSound(Sound sound){
        try {
            File yourFile;
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch (Exception e) {
            //whatevers
        }
    }
}
