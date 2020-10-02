package abstractclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import world.Imageholder;
import world.World;
import world.World.Layers;

/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das
 * Objekt sich nicht bewegen können.
 */
public abstract class Tile {

	private Boolean interactable;
	private Boolean passable;
	private String description;
	private HashMap<World.Layers, Imageholder> images;

	private World world;

	protected Tile(Boolean passable, Boolean interactable) {
		this.interactable = interactable;
		this.passable = passable;
		this.description = "default description";
		this.world = null;
		images = new HashMap<World.Layers, Imageholder>();
	}

	/**
	 * is calles when added to a world
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	public boolean hasLayer(World.Layers layer) {
		return !(images.get(layer) == null);
	}

	public Boolean getInteractable() {
		return interactable;
	}

	public Boolean getPassable() {
		return passable;
	}

	public BufferedImage getImage(World.Layers layer) {
		return images.get(layer).getActualImg();
	}

	public void addImage(Map<World.Layers, Imageholder> map) {
		images.putAll(map);
	}

	public void nextImage(Layers layer) {
		images.get(layer).nextImage();
	}

	public void setPassable(Boolean passable) {
		this.passable = passable;
	}

	public Point getPosition() {
		return world.getTilePoint(this);
	}

	public Integer getX() {
		return (int) getPosition().getX();
	}

	public Integer getY() {
		return (int) getPosition().getY();
	}

	public String getDescription() {
		return description;
	}

	public World getWorld() {
		return world;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public abstract void onInteract(Entity entity);

	public abstract void onSteppedUpon(Entity entity);

	public String getName() {
		return this.getClass().getName().replace("tiles.", "");
	}

}