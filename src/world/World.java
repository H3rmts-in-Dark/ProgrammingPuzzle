package world;


import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Enums.Animations;
import Enums.Rotations;
import Enums.Signalcolors;
import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Constants;
import logic.Layers;
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

	public void setTile(int x,int y,Tile tile) {
		world[x][y] = tile;
		tile.setWorld(this);
		tile.setPosition(new Point(x,y));
	}

	public Tile getTile(int x,int y) {
		return world[x][y];
	}

	public WorldWindow getWindow() {
		return window;
	}

	public void addEntity(Entity entity) {
		entitylist.add(entity);
		entity.setHeight(getTile(entity.getPosition().x,entity.getPosition().y).getHeight());
		entity.setWorld(this);
	}

	public void removeEntity(Entity entity) {
		entitylist.remove(entity);
	}

	public Entity getEntityAt(Tile tile) {
		for (Entity entity : entitylist)
			if (entity.getPosition().equals(tile.getPosition()))
				return entity;
		return null;
	}

	public ArrayList<Entity> getEntitysAt(Tile tile) {
		var list = new ArrayList<Entity>();
		for (Entity entity : entitylist)
			if (entity.getPosition().equals(tile.getPosition()))
				list.add(entity);
		return list;
	}

	public Integer getWidth() {
		return world.length;
	}

	public Integer getHeight() {
		return world[0].length;
	}

	public ArrayList<Entity> getEntitylist() {
		return entitylist;
	}

	private void fillempty() {
		for (int x = 0; x < getWidth(); x++)
			for (int y = 0; y < getHeight(); y++)
				setTile(x,y,new Default(Signalcolors.nocolor));
	}

	public static void loadAnimation(Rotations rotation,Animations animation,Tile tile) {
		var add = new ArrayList<String>();
		try {
			for (File file : new File(
					"rsc/objekt pictures/" + tile.getClass().getSimpleName() + "/" + Rotations.toPath(rotation) + animation)
							.listFiles()) {
				add.add(file.getPath());
			}
		} catch (NullPointerException e) {
			System.err.println("rsc/objekt pictures/" + tile.getClass().getSimpleName() + "/" + Rotations.toPath(rotation)
					+ animation + "  did not list any files ");
			return;
		}
		if (!tile.getAnimations().containsKey(rotation)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.getAnimations().put(rotation,addMap);
		}
		tile.getAnimations().get(rotation).put(animation,add);
	}

	public static void loadAnimation(Rotations rotation,Animations animation,Entity entity) {
		var add = new ArrayList<String>();
		try {
			for (File file : new File("rsc/entity pictures/" + entity.getClass().getSimpleName() + "/"
					+ Rotations.toPath(rotation) + animation).listFiles()) {
				add.add(file.getPath());
			}
		} catch (NullPointerException e) {
			System.err.println("rsc/entity pictures/" + entity.getClass().getSimpleName() + "/"
					+ Rotations.toPath(rotation) + animation + "  did not list any files ");
			return;
		}

		if (!entity.animations.containsKey(rotation)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			entity.animations.put(rotation,addMap);
		}
		entity.animations.get(rotation).put(animation,add);
	}

	public static void loadPicture(Layers layer,Animations animation,Tile tile,String name) {
		String path = "";
		try {
			path = new File(
					"rsc/" + Layers.toString(layer) + "/" + name + (layer == Layers.Floor ? "" : "/" + animation) + "/0.png")
							.getPath();
		} catch (NullPointerException e) {
			System.err.println("rsc/" + Layers.toString(layer) + name + "/" + (layer == Layers.Floor ? "" : animation)
					+ "/0.png" + "  did not list any files ");
			return;
		}
		if (!tile.getPictures().containsKey(layer)) {
			HashMap<Animations,String> addMap = new HashMap<Animations,String>();
			tile.getPictures().put(layer,addMap);
		}

		tile.getPictures().get(layer).put(animation,path);
	}

}
