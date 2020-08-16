package world;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthScrollBarUI;

public class World {

	enum Layers{Floor,Floordecoration,Objects,Effekte}
	
	/**
	 * Weltarray aus Tiles [x][y] layer0
	 * 1. horizontal (x)
	 * 2. vertikal (y)
	 */
	private ArrayList<ArrayList<Tile>> world;
	
	private Integer Whidth = 20;
	private Integer Height = 20;
	

	/**
	 * Listet alle Entitys in dieser Welt auf
	 */
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
				temp.add(null);
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
	public void setTile(int x, int y,Integer layer, Tile tile) {
		switch (layer) {
		case 0:
			try {layer0tiles.get(x).set(y,tile);
			} catch (IndexOutOfBoundsException e) {e.printStackTrace();}
			break;
		case 1:
			layer1tiles.get(x).set(y,tile);
			break;
		default:
			System.out.println(layer + "is not a valid option vor layer");
			break;
		}
	}

	/**
	 * gibt das tile an stelle [x][y] zurück
	 * 
	 * @param x
	 * @param y
	 * @return Tile
	 */
	public Tile getTile(int x, int y,Integer layer) {
		switch (layer) {
		case 0:
			try {return layer0tiles.get(x).get(y);
			} catch (IndexOutOfBoundsException e) {e.printStackTrace();}
			break;
		case 1:
			try {return layer0tiles.get(x).get(y);
			} catch (IndexOutOfBoundsException e) {e.printStackTrace();}
			break;
		default:
			System.out.println(layer + "is not a valid option vor layer");
			break;
		}
		return null;
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
