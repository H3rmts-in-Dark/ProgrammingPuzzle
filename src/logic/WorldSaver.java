package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import world.World;

// Haha wir retten die Welt xD
public class WorldSaver {

	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;

	private WorldSaver() {
	}

	public static void outputStream(Object object, String path) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(object);
			oos.close();
		} catch (IOException e) {
		}
	}

	public static Object inputStream(String path) {
		Object object = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(path)));
			try {
				object = ois.readObject();
			} catch (ClassCastException | ClassNotFoundException e) {
			}
			ois.close();
		} catch (IOException e) {
		}
		return object;
	}

	public static World loadWorld(String path) {
		Object object = inputStream(path);
		return (object.getClass() == World.class) ? (World) object : null;
	}

	public static void saveWorld(World world, String path) {
		outputStream(world, path);
	}
}
