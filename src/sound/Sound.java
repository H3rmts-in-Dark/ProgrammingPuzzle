package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	AudioInputStream audioInputStream;
	Info info;
	AudioFormat format;
	byte[] audio;
	int size;

	public Sound(File file) {
		load(file);
		play();
	}

	public void load(File file) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
			format = audioInputStream.getFormat();
			size = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
			audio = new byte[size];
			info = new Info(Clip.class, format, size);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			audioInputStream.read(audio, 0, size);
			@SuppressWarnings("resource")
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(format, audio, 0, size);
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / 1000);
		} catch (Exception e) {
		}
	}
}
