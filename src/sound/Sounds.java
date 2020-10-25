package sound;

public class Sounds {

	private static java.util.HashMap<java.io.File, Sound> sounds = new java.util.HashMap<>();

	private Sounds() {
	}

	public static Sound getSound(String path) {
		if (sounds.containsKey(new java.io.File(path)))
			return sounds.get(new java.io.File(path));
		Sound newimage = new Sound(new java.io.File(path));
		sounds.put(new java.io.File(path), newimage);
		return newimage;
	}
}