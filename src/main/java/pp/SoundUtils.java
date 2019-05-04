package pp;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundUtils {
    public static void play() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("battle.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            Utils.sleep(1000);
        } catch (UnsupportedAudioFileException e) {

        } catch (IOException e) {

        } catch (Throwable e) {

        }
    }
}

