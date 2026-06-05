import javax.sound.sampled.*;
import java.io.*;

public class SoundManager {

    public void playRadarPing() {
        playSound("sounds/radar_ping.wav");
    }

    public void playAlert() {
        playSound("sounds/alert.wav");
    }

    public void playExplosion() {
        playSound("sounds/explosion.wav");
    }
    public void playMiss() {
        playSound("sounds/miss.wav");
    }
    public void playLaunch() {
        playSound("sounds/launch.wav");
    }
    
    public void playCommandConfirm() {
        playSound("sounds/command_confirm.wav");
    }
    
    public void playVictory() {
    	playSound("sounds/victory.wav");
    }
    
    public void playLoss() {
    	playSound("sounds/lose.wav");
    }

    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);

            if (!soundFile.exists()) {
                System.out.println("Missing sound file: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clup = AudioSystem.getClip();
            clup.open(audioStream);
            clup.start();

        } catch (Exception e) {
            System.out.println("Could not play sound: " + filePath);
            System.out.println(e.getMessage());
        }
    }
}