package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import world.World;

public class WorldSaver {

	private static ObjectOutputStream os;
	private static ObjectInputStream is;
	private static World o;

	private WorldSaver() {
	}

	public static void outputStream(World o, String path) {
		try {
			os = new ObjectOutputStream(new FileOutputStream(new File(path)));
			os.writeObject(o);
			os.close();
		} catch (IOException e) {
		}
	}

	public static World inputStream(String path) {

		try {
			is = new ObjectInputStream(new FileInputStream(new File(path)));
			try {
				o = (World) is.readObject();
			} catch (ClassCastException | ClassNotFoundException e) {
				o = null;
			}
			is.close();
		} catch (IOException e) {
		}
		return o;
	}

}
