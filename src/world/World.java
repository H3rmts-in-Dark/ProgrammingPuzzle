package world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import frame.WorldLabel;
import logic.Main;
import tiles.Default;

public class World {
	
	private WorldLabel worldLabel;

	public enum Layers {Floor, Floordecoration, Objects, Entitys, Effects}

	/**
	 * Weltarray aus Tiles [x][y] layer0 1. horizontal (x) 2. vertikal (y)
	 */
	private Tile[][] world;

	private ArrayList<Entity> entitylist;

	public World(Integer width, Integer height) {
		world = new Tile[width][height];
		entitylist = new ArrayList<>();
		fillempty();
		
		worldLabel = new WorldLabel(this);
		Main.frame.addWindow(worldLabel);
	}

	public void setTile(Integer x, Integer y, Tile tile) {
		world[x][y] = tile;
		tile.setWorld(this);
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
		for (Iterator<Entity> iterator = entitylist.iterator(); iterator.hasNext();) {
			Entity entity = iterator.next();
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
		entity.setWorld(this);
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
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				setTile(x, y, new Default());
			}
		}
	}

	public Boolean isEmty() {
		if(getWidth() > 0 && getHeight() > 0) {
			return false;
		}
		return true;
	}
}
