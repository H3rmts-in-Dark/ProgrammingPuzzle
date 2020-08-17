package world;

import java.util.ArrayList;

import tiles.Default;

public class World {

	public enum Layers{Floor,Floordecoration,Objects,Effects}
	
	private Integer DefaultWhidth = 20;
	private Integer DefaultHeight = 20;
	
	/**
	 * Weltarray aus Tiles [x][y] layer0
	 * 1. horizontal (x)
	 * 2. vertikal (y)
	 */
	private Tile[][] world;
	
	private ArrayList<Entity> entitylist;

	
	public World() {
		world = new Tile[DefaultWhidth][DefaultHeight];
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
	public void setTile(int x, int y, Tile tile) {
		world[x][y] = tile;
	}

	/**
	 * gibt das tile an stelle [x][y] zurück
	 * 
	 * @param x
	 * @param y
	 * @return Tile
	 */
	public Tile getTile(int x, int y) {
		return world[x][y];
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
}
