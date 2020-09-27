package world;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Map;

import world.World.Layers;

/**
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden,
 * muss das Objekt / Lebewesen sich bewegen können.
 */
public abstract class Entity implements Serializable {

	public static enum Rotation {up, down, left, right}

	private Rotation rotation;
	private Boolean interactable;
	private Boolean passable;
	private String description;
	private Point position;
	private Imageholder image;
	
	private World world;

	protected Entity(Boolean passable,Boolean interactable,Point position) {
		this.interactable = interactable;
		this.passable = passable;
		this.rotation = Rotation.right;
		this.position = position;
		this.description = "default description";
		this.world = null;
	}
	
	/**
	 * is calles when added to a world
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	public Boolean getInteractable() {
		return interactable;
	}

	public Boolean getPassable() {
		return passable;
	}

	public BufferedImage getImage() {
		return image.getActualImg();
	}

	public void setImage(Map<World.Layers, Imageholder> map) {
		image = map.get(Layers.Entitys);
	}

	public void nextImage() {
		image.nextImage();
	}
	
	public void setPassable(Boolean passable) {
		this.passable = passable;
	}

	public Point getPosition() {
		return position;
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
	
	public void setPosition(Point newPosition,World world) {
		position = newPosition;
		world.getTile(getX(),getY()).onSteppedUpon(this);
	}

	public void interact() {
		Tile tile = null;
		switch (rotation) {
		case down:
			if (position.y < world.getHeight() - 1)
				tile = world.getTile(position.x, position.y - 1);
			break;
		case left:
			if (position.x > 0)
				tile = world.getTile(position.x - 1, position.y);
			break;
		case right:
			if (position.x < world.getWidth() - 1)
				tile = world.getTile(position.x + 1, position.y);
			break;
		case up:
			if (position.y > 0)
				tile = world.getTile(position.x, position.y - 1);
			break;
		}
		if (tile != null)
			if (tile.getInteractable())
				tile.onInteract(this);
	}

	public abstract void onInteract(Entity entity);

}
