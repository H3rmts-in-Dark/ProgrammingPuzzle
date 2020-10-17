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
	private static World world;

	private WorldSaver() {
	}

	public static void outputStream(World world, String path) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(world);
			oos.close();
		} catch (IOException e) {
		}
	}

	public static World inputStream(String path) {

		try {
			ois = new ObjectInputStream(new FileInputStream(new File(path)));
			try {
				world = (World) ois.readObject();
			} catch (ClassCastException | ClassNotFoundException e) {
				world = null;
			}
			ois.close();
		} catch (IOException e) {
		}
		return world;
	}
}
