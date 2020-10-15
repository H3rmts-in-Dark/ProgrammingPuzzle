package abstractclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Constants;
import logic.Rotation;
import world.Animation;
import world.World;

/**
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden,
 * muss das Objekt / Lebewesen sich bewegen können.
 */
public abstract class Entity implements Constants {

	private Rotation rotation;
	private Boolean interactable;
	private Integer height;
	private String description;
	private Point position;

	private Animation actualanimation;
	private HashMap<String, Animation> animations;

	private World world;

	protected Entity(Boolean interactable, Point position) {
		this.interactable = interactable;
		this.rotation = Rotation.right;
		this.position = position;
		this.description = "default description";
		this.world = null;

		animations = new HashMap<>();
		loadanimation();
		triggerdefaultanimation();
	}

	public abstract void loadanimation();

	/**
	 * is calles when added to a world
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	public Boolean getInteractable() {
		return interactable;
	}

	public BufferedImage getImage() {
		return actualanimation.getActualImg();
	}

	public void addAnimation(Animation animation, String identifier) {
		animations.put(identifier, animation);
	}

	public Animation getObjektanimation(String identifier) {
		try {
			return animations.get(identifier);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void triggerdefaultanimation() {
		triggerAnimation(getObjektanimation(defaultanimation));
	}

	public void triggerAnimation(Animation animation) {
		actualanimation = animation;
		animation.start();
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

	public void setPosition(Point newPosition, World world) {
		position = newPosition;
		world.getTile(getX(), getY()).onSteppedUpon(this);
	}

	public void interact() {
		Point pos = new Point(-1, -1);
		switch (rotation) {
		case down:
			if (position.y < world.getHeight() - 1)
				pos = new Point(position.x, position.y - 1);
			break;
		case left:
			if (position.x > 0)
				pos = new Point(position.x - 1, position.y);
			break;
		case right:
			if (position.x < world.getWidth() - 1)
				pos = new Point(position.x + 1, position.y);
			break;
		case up:
			if (position.y > 0)
				pos = new Point(position.x, position.y - 1);
			break;
		}
		Tile tile = world.getTile(pos.x, pos.y);
		if (tile != null)
			tile.onInteract(this);
		for (Entity e : world.getEntitys())
			if (e.getPosition().equals(pos))
				e.onInteract(this);
	}

	public abstract void onInteract(Entity entity);

	public String getName() {
		return this.getClass().getName().replace("tiles.", "");
	}

}
