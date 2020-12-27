
package abstractclasses;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;

import logic.Constants;
import logic.Rotations;
import tasks.MoveEntityTask;
import world.World;

/**
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden,
 * muss das Objekt / Lebewesen sich bewegen k�nnen.
 */

public abstract class Entity implements Constants {

	private Rotations rotation;
	private Boolean interactable;
	private Integer height;
	private String description;
	private Point pixelposition;

	private Integer relativedrawX;
	private Integer relativedrawY;

	private Animation actualAnimation;
	private HashMap<String, Animation> animations;

	private World world;

	protected Entity(Boolean interactable, Point position, Integer relativedrawX, Integer relativedrawY, Rotations r) {
		this.interactable = interactable;
		this.rotation = r;
		this.pixelposition = new Point(position.x * TILEHEIGHTWIDHT, position.y * TILEHEIGHTWIDHT);
		this.description = "default description";
		this.world = null;
		this.height = FLOORHEIGHT;

		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;

		animations = new HashMap<>();
		loadAnimation();
		triggerAnimation(DEFAULTANIMATION);
	}

	public abstract void loadAnimation();

	/**
	 * This Method is to be called when added to a world.
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
		height = UNPASSABLE;// getTilePosition(0, 0).getHeight();
	}

	public Boolean getInteractable() {
		return interactable;
	}

	public BufferedImage getImage() {
		return actualAnimation.getActualImage();
	}

	public void addAnimation(Entry<String, Animation> data) {
		animations.put(data.getKey(), data.getValue());
	}

	public void triggerAnimation(String animation) {
		triggerAnimation(getanimation(animation));
	}

	private void triggerAnimation(Animation animation) {
		if (actualAnimation != null) {
			actualAnimation.stopAnimation();
		}
		animation.startAnimation();
		actualAnimation = animation;
	}

	public Animation getanimation(String identifier) {
		return animations.get(identifier);
	}
	
	public Tile getTilePosition(Integer xoffset, Integer yoffset) {
		return world.getTile(getPosition().x - xoffset, getPosition().y - yoffset);
	}

	public Point getPixelposition() {
		return pixelposition;
	}

	public Integer getDrawX(Integer relativedrawX) {
		return (int) getPixelposition().getX() + relativedrawX;
	}

	public Integer getDrawY(Integer relativedrawY) {
		return (int) getPixelposition().getY() + relativedrawY;
	}

	public Integer getRelativedrawX() {
		return relativedrawX;
	}

	public Integer getRelativedrawY() {
		return relativedrawY;
	}

	public Point getPosition() {
		return new Point(pixelposition.x / TILEHEIGHTWIDHT, pixelposition.x / TILEHEIGHTWIDHT);
	}

	public Integer getHeight() {
		return height;
	}

	public String getDescription() {
		return description;
	}

	public World getWorld() {
		return world;
	}

	public Rotations getRotation() {
		return rotation;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void move() {
		if (getTileInFront().getHeight() <= getHeight())
			new MoveEntityTask(2, this);
	}

	public void changePixelpositionY(int y) {
		System.out.println("pixel positionY" + y);
		pixelposition.y = y;
	}
	
	public void changePixelpositionX(int x) {
		System.out.println("pixel positionX" + x);
		pixelposition.x = x;
	}

	/**
	 * Gibt das Tile vor dem Entity zur�ck (in die Richtung, in die es schaut).
	 * 
	 * @return
	 */
	public Tile getTileInFront() {
		switch (rotation) {
		case up:
			return getTilePosition(0, -1);
		case right:
			return getTilePosition(1, 0);
		case down:
			return getTilePosition(0, 1);
		case left:
			return getTilePosition(-1, 0);
		default:
			return null;
		}
	}

	public abstract void onInteract(Entity entity);

	public String getName() {
		return this.getClass().getName().replace("tiles.", "");
	}

	public void draw(Graphics2D g2, Float zoom) {
		g2.drawImage(
				getImage().getScaledInstance((int) (TILEHEIGHTWIDHT * zoom),
						(int) (TILEHEIGHTWIDHT * zoom), Scaler),
				(int) (getDrawX(getRelativedrawX()) * zoom),
				(int) (getDrawY(getRelativedrawY()) * zoom), null);
	}

}
