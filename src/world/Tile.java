package world;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Tile {
	
	/**
	 * Default = black;
	 *
	 */
	public enum Floortype {
		Factory,Default
	}

	/**
	 * Bodentyp
	 */
	final private Floortype floortype;

	/**
	 * Gibt den Punkt des Tiles in der Map an
	 */
	final private Point location;

	/**
	 * der Name des Tiles
	 */
	final private String name;

	/**
	 * Zeigt, ob der Spieler mit diesem Tile interagieren kann
	 */
	private boolean interactable;

	/**
	 * Zeigt, ob der Spieler durch dieses Tile gehen kann
	 */
	private boolean passable;

	/**
	 * das Aussehen des Tiles
	 */
	final private BufferedImage image;

	public Tile(String name,BufferedImage image, Floortype floortype, Point location) {
		this.floortype = floortype;
		this.location = location;
		this.name = name;
		this.image = image;
		// TODO Auto-generated constructor stub
	}
	
	public Tile(Point location) {
		this.floortype = Floortype.Default;
		this.location = location;
		this.name = "default";
		this.image = null;
	}
	
	/**
	 * Gibt den Bodentypen zurück
	 * 
	 * @return floortype
	 */
	public Floortype getFloor() {
		return floortype;
	}


	/**
	 * Gibt den Punkt des Tiles in der Map zurück
	 * 
	 * @return location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Gibt den Namen des Tiles zurück
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
	 * gibt das Bild zurück
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
	public void onInteract() {
	}

	/**
	 * Wird ausgelöst, wenn der Spieler das Tile betritt
	 */
	public void onSteppedUpon() {
	}
}