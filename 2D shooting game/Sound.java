import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	public static void playGameMusic() throws InterruptedException {
		Clip play = null;
		try {
			File in = new File("music.wav");
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(in);
			play = AudioSystem.getClip();
			play.open(audioInputStream);
			FloatControl volume = (FloatControl) play
					.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(1.0f); // Reduce volume
			play.start();
			do {
				Thread.sleep(15);
			} while (play.isRunning());
			play.drain();
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException ex) {
			ex.printStackTrace();
		} finally {
			try {
				play.close();
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}

	public static void playStartMusic() throws InterruptedException {
		Clip play = null;
		try {

			File in = new File("music.wav");
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(in);
			play = AudioSystem.getClip();
			play.open(audioInputStream);
			FloatControl volume = (FloatControl) play
					.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(1.0f); // Reduce volume
			play.start();
			do {
				Thread.sleep(15);
			} while (play.isRunning());
			play.drain();

		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException ex) {
			ex.printStackTrace();
		} finally {
			try {
				play.close();
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

}
