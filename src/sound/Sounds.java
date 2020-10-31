package sound;

import java.io.File;
import java.util.HashMap;

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