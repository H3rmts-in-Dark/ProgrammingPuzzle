package world;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import world.World.Layers;

public abstract class Tile {
	
	protected Tile(Integer X,Integer Y,Boolean interactable) {
		this.location = new Point(X,Y);
		this.interactable = interactable;
	}

	public final Point location;

	public final Boolean interactable;
	
	private Boolean passable;
	
	private HashMap<Layers,BufferedImage> images;
	
	public Point getLocation() {
		return location;
	}
	public Boolean getInteractable() {
		return interactable;
	}
	public Boolean getPassable() {
		return passable;
	}
	public BufferedImage getImage(Layers layer) {
		return images.get(layer);
	}
	
	public void setPassable(Boolean passable) {
		this.passable = passable;
	}

	/**
	 * Wenn der Spieler mit dem Tile zu interagiert wird diese Methode
	 * aufgerufen
	 */
	public abstract void onInteract();

	/**
	 * Wird ausgel�st, wenn der Spieler das Tile betritt
	 */
	public abstract void onSteppedUpon();
}