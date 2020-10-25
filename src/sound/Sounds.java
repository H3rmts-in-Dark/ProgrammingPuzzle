package sound;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

/**
 * Spielt ein Soundfile auf anfrage ab.
 * 
 * @author Jan
 *
 */
public class Sounds {

	private static HashMap<File,Sound> sounds = new HashMap<>();

	private Sounds() {
	}
	
	public static Sound getSound(String path) {
		if (sounds.get(new File(path)) != null)
			return sounds.get(new File(path));
		Sound newimage = null;
		newimage = new Sound(new File(path));
		sounds.put(new File(path), newimage);
		return newimage;
	}
}