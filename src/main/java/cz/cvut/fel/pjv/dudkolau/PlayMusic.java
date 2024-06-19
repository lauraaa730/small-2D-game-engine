package cz.cvut.fel.pjv.dudkolau;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class PlayMusic {
    private Clip clip;

    public Clip getClip() {
        return clip;
    }

    public PlayMusic(String filepath) {
        File musicPath = new File(filepath);
        //maybe add whether program can find this file
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            this.clip = AudioSystem.getClip();
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
