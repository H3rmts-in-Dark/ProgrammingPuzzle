package world;

import java.util.ArrayList;

import tiles.Default;

public class World {

	public enum Layers{Floor,Floordecoration,Objects,Effekte}
	
	private Integer Whidth = 20;
	private Integer Height = 20;
	
	/**
	 * Weltarray aus Tiles [x][y] layer0
	 * 1. horizontal (x)
	 * 2. vertikal (y)
	 */
	private ArrayList<ArrayList<Tile>> world;
	
	private ArrayList<Entity> entitylist;

	
	public World() {
		world = getdefaultList(Whidth,Height);
		entitylist = new ArrayList<Entity>();
	}
	
	private ArrayList<ArrayList<Tile>> getdefaultList(Integer defaultwhidht,Integer defaultheight) {
		ArrayList<ArrayList<Tile>> newList = new ArrayList<ArrayList<Tile>>();
		for (int x = 0; x < defaultwhidht; x++) {
			ArrayList<Tile> temp = new ArrayList<Tile>();
			for (int y = 0; y < defaultheight; y++) {
				temp.add(new Default(x,y));
			}
			newList.add(temp);
		}
		return newList;
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
		world.get(x).set(y,tile);
	}

	/**
	 * gibt das tile an stelle [x][y] zurück
	 * 
	 * @param x
	 * @param y
	 * @return Tile
	 */
	public Tile getTile(int x, int y) {
		return world.get(x).get(y);
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
