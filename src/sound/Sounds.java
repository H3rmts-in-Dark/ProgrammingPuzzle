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
		Sound newsound = null;
		newsound = new Sound(new File(path));
		sounds.put(new File(path), newsound);
		return newsound;
	}
}