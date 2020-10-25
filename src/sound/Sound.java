package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	Info info;
	AudioFormat format;
	byte[] audio;

	public Sound(File file) {
		load(file);
	}

	public void load(File file) {
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
			format = audioInputStream.getFormat();
			Integer size = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
			audio = new byte[size];
			info = new Info(Clip.class, format, size);
			audioInputStream.read(audio, 0, size);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		new Thread() {
			@Override
			public void run() {
				try (Line line = AudioSystem.getLine(info)) {
					try (Clip clip = (Clip) line) {
						System.out.println("play:" + audio.length);
						clip.open(format, audio, 0, audio.length);
						clip.start();
						sleep(clip.getMicrosecondLength() / 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
