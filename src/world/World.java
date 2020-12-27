package world;

import java.awt.Point;
import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Tile;
import frame.WorldWindow;
import logic.Constants;
import tiles.Default;

public class World implements Constants{

	/**
	 * Weltarray aus Tiles [x][y] layer0 1. horizontal (x) 2. vertikal (y)
	 */
	private Tile[][] world;

	private ArrayList<Entity> entitylist;

	private WorldWindow window;

	public World(Integer width, Integer height) {
		world = new Tile[width][height];
		entitylist = new ArrayList<>();
		window = new WorldWindow(this);
		
		fillempty();
	}

	public void setTile(Integer x, Integer y, Tile tile) {
		world[x][y] = tile;
		tile.setWorld(this);
	}

	public Tile getTile(int x, int y) {
		return world[x][y];
	}

	public WorldWindow getWindow() {
		return window;
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
				setTile(x, y, new Default());
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

}
