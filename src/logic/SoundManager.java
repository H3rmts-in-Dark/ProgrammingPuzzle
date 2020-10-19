package logic;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.*;

/**
 * Spielt ein Soundfile auf anfrage ab.
 * 
 * @author Jan
 *
 */
public class SoundManager {

	private static ArrayList<Sound> sounds;

	public SoundManager() {
		sounds = new ArrayList<>();
	}

	public static void playSound(String name) {
		for (Sound sound : sounds)
			if (sound.name.equals(name))
				sound.play();
		sounds.add(new Sound(name));
	}

	public static class Sound {

		AudioInputStream audioInputStream;
		String name;
		DataLine.Info info;
		AudioFormat format;
		byte[] audio;
		int size;

		public Sound(String name) {
			initialize(name);
			play();
		}

		public void initialize(String name) {
			try {
				File soundFile = getSoundFile(name);
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
				format = audioInputStream.getFormat();
				size = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
				audio = new byte[size];
				info = new DataLine.Info(Clip.class, format, size);
			} catch (Exception e) {
			}
		}

		public void play() {
			try {
				audioInputStream.read(audio, 0, size);
				Clip clip = (Clip) AudioSystem.getLine(info);
				clip.open(format, audio, 0, size);
				clip.start();
				Thread.sleep((long) (clip.getMicrosecondLength() / 1000));
			} catch (Exception e) {
			}
		}

		private static File getSoundFile(String name) {
			return new File("rsc/sound/" + name + ".wav");
		}
	}
}