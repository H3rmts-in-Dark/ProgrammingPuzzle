package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Enums.Signalcolor;
import Enums.Cabletype;
import Enums.Rotation;
import tiles.*;

// Diese Library haben wir von https://github.com/stleary/JSON-java
import json.JSONArray;
import json.JSONObject;

public class WorldLoader {

	public static void getWorld(String name) throws FileNotFoundException {
		JSONObject json = getJSONOBject(name);
		World world = new World(json.getInt("width"), json.getInt("height"));
		JSONArray tiles = json.getJSONArray("tiles");

		for (int i = 0; i < tiles.length(); i++) { // Übersetzer für die Tiles
			JSONObject tile = (JSONObject) tiles.get(i);
			System.out.println(tile);
			switch (tile.getString("type")) {
			case "computer":
				world.setTile(tile.getInt("x"), tile.getInt("y"),
						new Computer(Signalcolor.converter(tile.getString("signalcolor")),
								Cabletype.convert(tile.getString("cabletype"))));
				break;
			case "default":
				world.setTile(tile.getInt("x"), tile.getInt("y"),
						new Floor(Signalcolor.converter(tile.getString("signalcolor")),
								Cabletype.convert(tile.getString("cabletype"))));
				break;
			case "gewichtssensor":
				world.setTile(tile.getInt("x"), tile.getInt("y"),
						new GewichtsSensor(Signalcolor.converter(tile.getString("signalcolor")),
								Cabletype.convert(tile.getString("cabletype"))));
				break;
			case "förderband":
				world.setTile(tile.getInt("x"), tile.getInt("y"),
						new Förderband(Rotation.convert(tile.getString("rotation")), tile.getInt("speed"),
								Signalcolor.converter(tile.getString("signalcolor")),
								Cabletype.convert(tile.getString("cabletype"))));
				break;
			case "lampe":
				world.setTile(tile.getInt("x"), tile.getInt("y"),
						new Lampe(Signalcolor.converter(tile.getString("signalcolor")),
								Cabletype.convert(tile.getString("cabletype"))));
				break;
			case "tonne":
				world.setTile(tile.getInt("x"), tile.getInt("y"), new Tonne());
				break;
			default:
				break;
			}
		}
	}

	public static String getDescription(String name) {
		try {
			return getJSONOBject(name).getString("description");
		} catch (FileNotFoundException fnfe) {
			return "File Not Found.";
		}
	}

	public static Icon getIcon(String name) {
		return (Icon) new ImageIcon("rsc/worlds/" + name + ".png");
	}

	public static JSONObject getJSONOBject(String name) throws FileNotFoundException {
		String s = "";
		Scanner sc = new Scanner(new File("rsc/worlds/" + name + ".json"));
		while (sc.hasNextLine())
			s += sc.nextLine();
		return new JSONObject(s);
	}
}