package world;

import java.awt.Point;
import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Tile;
import frame.WorldWindow;
import tiles.Default;

public class World {

	public static enum Layers {Floor, Cable, Objects, Entitys, Effects}

	/**
	 * Weltarray aus Tiles [x][y] layer0 1. horizontal (x) 2. vertikal (y)
	 */
	private Tile[][] world;

	private ArrayList<Entity> entitylist;

	public World(Integer width, Integer height) {
		world = new Tile[width][height];
		entitylist = new ArrayList<>();
		fillempty();
		
		new WorldWindow(this);
	}

	public void setTile(Integer x, Integer y, Tile tile) {
		world[x][y] = tile;
		tile.setWorld(this);
	}

	public Tile getTile(int x, int y) {
		return world[x][y];
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
		return entitylist.indexOf(e);
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
		if(getWidth() > 0 && getHeight() > 0) {
			return false;
		}
		return true;
	}

	public ArrayList<Entity> getEntitys() {
		return entitylist;
	}
}
