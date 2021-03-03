package world;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Enums.Cabletype;
import Enums.Rotation;
import Enums.Signalcolor;
import entitys.Box;
import entitys.Player;
// Diese Library haben wir von https://github.com/stleary/JSON-java
import json.JSONArray;
import json.JSONObject;
import tiles.Computer;
import tiles.Ende;
import tiles.Floor;
import tiles.Förderband;
import tiles.GewichtsSensor;
import tiles.Lampe;
import tiles.Schalter;
import tiles.Tonne;
import tiles.Wand;


public class WorldLoader {

	public static World getWorld(String name) throws FileNotFoundException {
		JSONObject json = getJSONOBject(name);
		World world = new World(json.getInt("width"),json.getInt("height"));
		JSONArray tiles = json.getJSONArray("tiles");

		for (int i = 0; i < tiles.length(); i++) { // Übersetzer für die Tiles
			JSONObject tile = (JSONObject) tiles.get(i);
			switch (tile.getString("type")) {
				case "computer":
					world.setTile(tile.getInt("x"),tile.getInt("y"),
						new Computer(Signalcolor.converter(tile.getString("signalcolor")),
							Cabletype.convert(tile.getString("cabletype"))));
				break;
				case "default":
					world.setTile(tile.getInt("x"),tile.getInt("y"),
						new Floor(Signalcolor.converter(tile.getString("signalcolor")),
							Cabletype.convert(tile.getString("cabletype"))));
				break;
				case "gewichtssensor":
					world.setTile(tile.getInt("x"),tile.getInt("y"),
						new GewichtsSensor(Signalcolor.converter(tile.getString("signalcolor")),
							Cabletype.convert(tile.getString("cabletype"))));
				break;
				case "förderband":
					world.setTile(tile.getInt("x"),tile.getInt("y"),
						new Förderband(Rotation.convert(tile.getString("rotation")),tile.getInt("speed"),
							Signalcolor.converter(tile.getString("signalcolor")),
							Cabletype.convert(tile.getString("cabletype"))));
				break;
				case "lampe":
					world.setTile(tile.getInt("x"),tile.getInt("y"),
						new Lampe(Signalcolor.converter(tile.getString("signalcolor")),
							Cabletype.convert(tile.getString("cabletype"))));
				break;
				case "tonne":
					world.setTile(tile.getInt("x"),tile.getInt("y"),new Tonne());
				break;
				case "wand":
					world.setTile(tile.getInt("x"),tile.getInt("y"),new Wand());
				break;
				case "ende":
					world.setTile(tile.getInt("x"),tile.getInt("y"),new Ende());
				break;
				case "schalter":
					world.setTile(tile.getInt("x"),tile.getInt("y"),
						new Schalter(Signalcolor.converter(tile.getString("signalcolor")),
							Cabletype.convert(tile.getString("cabletype"))));
				break;
			}
		}

		// Entities
		JSONArray entities = json.getJSONArray("entities");
		for (int i = 0; i < entities.length(); i++) {
			JSONObject entity = (JSONObject) entities.get(i);
			switch (entity.getString("type")) {
				case "player":
					world.player = new Player(new Point(entity.getInt("x"),entity.getInt("y")),
						Rotation.convert(entity.getString("rotation")));
					world.addEntity(world.player);
				break;
				case "box":
					world.addEntity(new Box(entity.getInt("x"),entity.getInt("y")));
				break;
			}
		}
		return world;
	}

	public static String getDescription(String name) {
		try {
			return getJSONOBject(name).getString("description");
		} catch (FileNotFoundException fnfe) {
			return "File Not Found.";
		}
	}

	public static Icon getIcon(String name) {
		return new ImageIcon("rsc/worlds/" + name + ".png");
	}

	public static JSONObject getJSONOBject(String name) throws FileNotFoundException {
		String s = "";
		Scanner sc = new Scanner(new File("rsc/worlds/" + name + ".json"));
		while (sc.hasNextLine())
			s += sc.nextLine();
		return new JSONObject(s);
	}

}
