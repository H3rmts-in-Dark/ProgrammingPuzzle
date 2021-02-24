package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Enums.Signalcolor;
import Enums.Cabletype;
import Enums.Rotation;
import tiles.*;

// Diese Library haben wir von https://github.com/stleary/JSON-java
import json.JSONArray;
import json.JSONObject;

public class WorldLoader {

	public static void main(String[] args) {
		try {
			World world = getWorld("testworld");
			System.out.println(world.getTile(1, 1));
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
	}

	public static World getWorld(String name) throws FileNotFoundException {
		World world;
		String s = "";

		Scanner sc = new Scanner(new File("rsc/worlds/" + name + ".json"));
		while (sc.hasNextLine())
			s += sc.nextLine();
		JSONObject json = new JSONObject(s);

		world = new World(json.getInt("width"), json.getInt("height"));

		JSONArray tiles = json.getJSONArray("tiles");
		for (int i = 0; i < tiles.length(); i++) {
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
		return world;
	}
}