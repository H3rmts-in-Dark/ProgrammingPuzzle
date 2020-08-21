package world;

import java.util.ArrayList;

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

	/**
	 * Setzt das tile an stelle [x][y]
	 * 
	 * @param x
	 * @param y
	 * @param layer
	 * @param tile
	 */
	public void setTile(Tile tile) {
		world[(int) (tile.getLocation().getX())][(int) (tile.getLocation().getY())] = tile;
	}

	/**
	 * gibt das Tile an Stelle [x][y] zurück
	 * 
	 * @param x
	 * @param y
	 * @return Tile
	 */
	public Tile getTile(int x, int y) {
		return world[x][y];
	}

	/**
	 * Entfernt das Tile an Stelle [x][y] (und ersetzt es durch Default) sowie alle
	 * auf diesem Feld befindenden Entities
	 * 
	 * @param x
	 * @param y
	 */
	public void eraseTile(int x, int y) {
		setTile(new tiles.Default(x, y));
		for (int i = 0; i < entitylist.size(); i++) {
			if (entitylist.get(i).getPosition().equals(new java.awt.Point(x, y)))
				entitylist.remove(i);
		}
	}

	/**
	 * Fügt Entity e am ende der entitylist an
	 * 
	 * @param e
	 */
	public void addEntity(Entity entity) {
		entitylist.add(entity);
	}

	/**
	 * Entfernt das Entity an position im Array entitylist
	 * 
	 * @param position
	 */
	public void removeEntity(Entity entity) {
		entitylist.remove(entity);
	}

	/**
	 * Gibt das Entity an position zurück
	 * 
	 * @param position
	 * @return Entity
	 */
	public Entity getEntity(Integer index) {
		return entitylist.get(index);
	}

	public Integer searchEntity(Entity e) {
		Integer i;
		for (i = 0; i < entitylist.size(); i++) {
			if (entitylist.get(i) == e) {
				break;
			}
		}
		return i;
	}
}
