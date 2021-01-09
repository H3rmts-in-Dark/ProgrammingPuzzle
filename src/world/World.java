package world;


import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import abstractclasses.Entity;
import abstractclasses.Tile;
import frame.WorldWindow;
import logic.Animations;
import logic.Constants;
import logic.Layers;
import logic.Rotations;
import tiles.Computer;
import tiles.Default;


public class World implements Constants {

	/**
	 * Weltarray aus Tiles [x][y] layer0 1. horizontal (x) 2. vertikal (y)
	 */
	private Tile[][] world;

	private ArrayList<Entity> entitylist;

	private WorldWindow window;

	public World(Integer width,Integer height) {
		world = new Tile[width][height];
		entitylist = new ArrayList<>();

		fillempty();

		window = new WorldWindow(this);

	}

	public void setTile(Integer x,Integer y,Tile tile) {
		try {
			world[x][y].delete();
		} catch (NullPointerException e) {
		}
		world[x][y] = tile;
		tile.setWorld(this);
	}

	public Tile getTile(int x,int y) {
		return world[x][y];
	}

	public WorldWindow getWindow() {
		return window;
	}

	public Point getTilePoint(Tile tile) {
		for (Integer x = 0; x < getWidth(); x++) {
			for (Integer y = 0; y < getHeight(); y++) {
				if (world[x][y] == tile) {
					return new Point(x,y);
				}
			}
		}
		return new Point(-1,-1);
	}

	public void addEntity(Entity entity) {
		entitylist.add(entity);
		entity.setWorld(this);
	}

	public void removeEntity(Entity entity) {
		entitylist.remove(entity);
	}

	public Entity getEntityAt(Tile tile) {
		for (Entity entity : entitylist) {
			if (entity.getPosition().equals(tile.getPosition()))
				return entity;
		}
		return null;
	}

	public Integer getWidth() {
		return world.length;
	}

	public Integer getHeight() {
		return world[0].length;
	}

	public Integer getEntitylistLength() {
		return entitylist.size();
	}

	private void fillempty() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				setTile(x,y,new Default());
			}
		}
	}

	public Boolean isEmty() {
		if (getWidth() > 0 && getHeight() > 0) {
			return false;
		}
		return true;
	}

	public ArrayList<Entity> getEntitys() {
		return entitylist;
	}

	public static void loadAnimation(Rotations rotation,Animations animation,Tile tile) {
		var add = new ArrayList<String>();
		for (File file : new File(
				"rsc/objekt pictures/" + tile.getClass().getSimpleName() + "/" + Rotations.toString(rotation) + animation)
						.listFiles()) {
			add.add(file.getPath());
		}
		if (!tile.animations.containsKey(rotation)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.animations.put(rotation,addMap);
		}
		tile.animations.get(rotation).put(animation,add);
	}

	public static void loadAnimation(Rotations rotation,Animations animation,Entity entity) {
		var add = new ArrayList<String>();
		for (File file : new File(
				"rsc/entity pictures/" + entity.getClass().getSimpleName() + "/" + Rotations.toString(rotation) + animation)
						.listFiles()) {
			add.add(file.getPath());
		}
		if (!entity.animations.containsKey(rotation)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			entity.animations.put(rotation,addMap);
		}
		entity.animations.get(rotation).put(animation,add);
	}

	public static void loadPicture(Layers layer,Animations animation,Tile tile,String name) {
		var add = new ArrayList<String>();
		for (File file : new File("rsc/" + Layers.toString(layer) + name + "/" + (layer == Layers.Floor ? "" : animation))
				.listFiles()) {
			add.add(file.getPath());
		}
		if (!tile.pictures.containsKey(layer)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.pictures.put(layer,addMap);
		}

		tile.pictures.get(layer).put(animation,add);
	}

}
