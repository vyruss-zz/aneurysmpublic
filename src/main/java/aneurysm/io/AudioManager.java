package aneurysm.io;

import aneurysm.structures.DigitalSound;
import aneurysm.ui.DataLists;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;

public class AudioManager {

    public static void playSound(DigitalSound sound) {
        AudioInputStream audioInputStream;
        Clip clip;
        try {
            audioInputStream = new AudioInputStream(new ByteArrayInputStream(sound.getSoundData()), new AudioFormat(sound.getSamplerate(), 8, 1, DataLists.isCdOrCart(), false), sound.getSoundData().length);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            while(clip.getFramePosition() != clip.getFrameLength()) {

            }
            audioInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
