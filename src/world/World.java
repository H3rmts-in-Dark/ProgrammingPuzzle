package world;

import java.awt.Point;
import java.util.ArrayList;

import logic.Main;
import tiles.Default;

public class World {

	public enum Layers {
		Floor, Floordecoration, Objects, Effects
	}

	/**
	 * Weltarray aus Tiles [x][y] layer0 1. horizontal (x) 2. vertikal (y)
	 */
	private Tile[][] world;

	private ArrayList<Entity> entitylist;

	public World(Integer width, Integer height) {
		world = new Tile[width][height];
		entitylist = new ArrayList<Entity>();
	}

	public void setTile(Integer x, Integer y, Tile tile) {
		world[x][y] = tile;
	}

	public Tile getTile(int x, int y) {
		return world[x][y];
	}

	/**
	 * Entfernt das Tile an Stelle [x][y] (und ersetzt es durch Default) sowie alle
	 * auf diesem Feld befindenden Entitiess
	 */
	public void eraseTile(int x, int y) {
		setTile(x, y, new Default());
		for (Entity entity : entitylist) {
			if (entity.getPosition().equals(new Point(x, y)))
				entitylist.remove(entity);
		}
	}

	public Point getTilePoint(Tile tile) {
		for (Integer x = 0; x < getWidth(); x++) {
			for (Integer y = 0; y < getHeight(); y++) {
				if (world[x][y].equals(tile)) {
					return new Point(x, y);
				}
			}
		}
		return new Point(-1, -1);
	}

	public void addEntity(Entity entity) {
		entitylist.add(entity);
	}

	public void removeEntity(Entity entity) {
		entitylist.remove(entity);
	}

	public Entity getEntity(Integer index) {
		return entitylist.get(index);
	}

	public Integer getIndex(Entity e) {
		return entitylist.lastIndexOf(e);
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

	public void fillempty() {
		for (int x = 0; x < Main.world.getWidth(); x++) {
			for (int y = 0; y < Main.world.getHeight(); y++) {
				setTile(x, y, new Default());
			}
		}
		System.out.println("finished");
	}
}
