package world;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import world.World.Layers;

public abstract class Tile {

	public final Point location;

	public final Boolean interactable;

	private Boolean passable;

	private HashMap<Layers,Imageholder> images;

	protected Tile(Integer x, Integer y, Boolean passable, Boolean interactable) {
		this.location = new Point(x, y);
		this.interactable = interactable;
		this.passable = passable;
		images = new HashMap<Layers,Imageholder>();
	}

	public boolean hasLayer(World.Layers layer) {
		return !images.get(layer).equals(null);
	}

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
		return images.get(layer).getActualImg();
	}

	public void setImage(Layers layer,Imageholder imageholder) {
		this.images.put(layer,imageholder);
	}
	
	public void addImage(Map<Layers,Imageholder> map) {
		images.putAll(map);
	}
	
	public void setPassable(Boolean passable) {
		this.passable = passable;
	}
	
	/**
	 * Wenn der Spieler mit dem Tile zu interagiert wird diese Methode aufgerufen
	 */
	public abstract void onInteract();

	/**
	 * Wird ausgelöst, wenn der Spieler das Tile betritt
	 */
	public abstract void onSteppedUpon();
}