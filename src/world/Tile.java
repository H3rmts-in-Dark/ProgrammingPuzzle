package world;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Tile {

	public enum Floortype {
		Factory
	}
	
	public Tile (){
		// TODO Constructor
	}

	/**
	 * Bodentyp
	 */
	private Floortype floortype;

	/**
	 * Gibt den Punkt des Tiles in der Map an
	 */
	Point location;

	String name;

	/**
	 * Zeigt, ob der Spieler mit diesem Tile interagieren kann
	 */
	boolean interactable;

	boolean passable;

	/**
	 * das Aussehen des Tiles
	 */
	BufferedImage image;

	/**
	 * Gibt den Bodentypen zur�ck
	 * 
	 * @return floortype
	 */
	public Floortype getFloor() {
		return floortype;
	}

	/**
	 * Setzt den Punkt des Tiles in der Map
	 * 
	 * @param p
	 */
	public void setLocation(Point p) {
		location = p;
	}

	/**
	 * Gibt den Punkt des Tiles in der Map zur�ck
	 * 
	 * @return location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Gibt den Namen des Tiles zur�ck
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * gibt aus, ob der Spieler mit diesem Tile interagieren kann
	 * 
	 * @return interactable
	 */
	public boolean getInteractable() {
		return interactable;
	}

	/**
	 * gibt aus, ob der Spieler durch dieses Tile gehen kann
	 * 
	 * @return passable
	 */
	public boolean getPassable() {
		return passable;
	}

	/**
	 * Bild setzen
	 * 
	 * @param img
	 */
	public void setImage(BufferedImage img) {
		image = img;
	}

	/**
	 * gibt das Bild zur�ck
	 * 
	 * @return image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Wenn der Spieler versucht mit dem Tile zu interagieren wird diese Methode
	 * aufgerufen
	 */
	public abstract void onInteract();;

	/**
	 * Wird ausgel�st, wenn der Spieler das Tile betritt
	 */
	public abstract void onSteppedUpon();;
}