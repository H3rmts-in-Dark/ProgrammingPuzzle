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

	private Animation actualAnimation;
	private HashMap<String, Animation> animations;

	private World world;

	protected Entity(Boolean interactable, Point position) {
		this.interactable = interactable;
		this.rotation = Rotation.right;
		this.position = position;
		this.description = "default description";
		this.world = null;

		animations = new HashMap<>();
		loadAnimation();
		triggerDefaultAnimation();
	}

	public abstract void loadAnimation();

	/**
	 * This Method is to be called when added to a world.
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
		return actualAnimation.getActualImage();
	}

	public void addAnimation(Animation animation, String identifier) {
		animations.put(identifier, animation);
	}

	public Animation getObjektAnimation(String identifier) {
		try {
			return animations.get(identifier);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void triggerDefaultAnimation() {
		triggerAnimation(getObjektAnimation(DEFAULTANIMATION));
	}

	public void triggerAnimation(Animation animation) {
		actualAnimation = animation;
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
		try {
			world.getTile(getX(), getY()).onSteppedUpon(this);
		} catch (IndexOutOfBoundsException ioobe) {
			System.out.println("Stepped out of Bounds.");
		}
	}

	public void interact() {
		Tile tile = getTileInFront();
		if (tile != null)
			tile.onInteract(this);
		for (Entity e : world.getEntitys())
			if (e.getPosition().equals(tile.getPosition()))
				e.onInteract(this);
	}

	/**
	 * Gibt das Tile vor dem Entity zurück (in die Richtung, in die es schaut). Wenn
	 * es keine Blickrichtung hat, wird automatsch oben genommen.
	 * 
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public Tile getTileInFront() throws IndexOutOfBoundsException {
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
		default:
			if (position.y > 0)
				tile = world.getTile(position.x, position.y - 1);
			break;
		}
		return tile;
	}

	public abstract void onInteract(Entity entity);

	public String getName() {
		return this.getClass().getName().replace("tiles.", "");
	}

}
