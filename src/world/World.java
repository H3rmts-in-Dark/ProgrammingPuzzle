package world;

public class World {

	/**
	 * Weltarray aus Tiles [x][y]
	 */
	private Tile[][] world;

	/**
	 * Listet alle Entitys in dieser Welt auf
	 */
	private Entity[] entitylist;

	/**
	 * Setzt das tile an stelle [x][y]
	 * 
	 * @param x
	 * @param y
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
	public void addEntity(Entity e) {
		Entity[] list = new Entity[entitylist.length];
		for (int i = 0; i < entitylist.length; i++) {
			list[i] = entitylist[i];
		}
		list[list.length - 1] = e;
	}

	/**
	 * Entfernt das Entity an position im Array entitylist
	 * 
	 * @param position
	 */
	public void removeEntity(int position) {
		Entity[] list = new Entity[entitylist.length - 2];
		int a = 0;
		for (int i = 0; i < position; i++) {
			list[i] = entitylist[i];
			a++;
		}
		for (int i = a; i < (list.length - a); i++) { // TODO Überprüfen ob das so funktioniert, bin mir nicht sicher
			list[i] = entitylist[i + 1];
		}
	}

	/**
	 * Gibt das Entity an position zurück
	 * 
	 * @param position
	 * @return Entity
	 */
	public Entity getEntity(int position) {
		if (position < entitylist.length)
			return entitylist[position];
		else
			return null;
	}
}
