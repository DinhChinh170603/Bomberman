package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30]; // soundURL luu tru duong dan tep

    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/res/Sound/track1.wav");
        soundURL[1] = getClass().getResource("/res/Sound/BOM_SET.wav");
        soundURL[2] = getClass().getResource("/res/Sound/BOM_11_M.wav");
        soundURL[3] = getClass().getResource("/res/Sound/menu.wav");
        soundURL[4] = getClass().getResource("/res/Sound/CRYST_UP.wav");
        soundURL[5] = getClass().getResource("/res/Sound/cursor.wav");
        soundURL[6] = getClass().getResource("/res/Sound/deadplayer.wav");
        soundURL[7] = getClass().getResource("/res/Sound/endgame3.wav");
        soundURL[8] = getClass().getResource("/res/Sound/win.wav");
        soundURL[9] = getClass().getResource("/res/Sound/Tele.wav");
        soundURL[10] =getClass().getResource("/res/Sound/map.wav");
    }

    public void setFile(int num) {
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(soundURL[num]);
            clip = AudioSystem.getClip();
            clip.open(input);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
            // dinh dang de mo tap am thanh trong java
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // khi muon phat tep am thanh chung ta goi phuong thuc hien thi
    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
