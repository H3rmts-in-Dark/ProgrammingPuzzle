package abstractclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;

import logic.Constants;
import logic.Layers;
import world.Animation;
import world.Images;
import world.World;

/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das
 * Objekt sich nicht bewegen k�nnen und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private Integer height;
	private String description;

	private Integer relativedrawX;
	private Integer relativedrawY;
	
	private Boolean animated;

	/**
	 * exluding objektlayer
	 */
	private HashMap<Layers, String> images;

	private Animation actualAnimation;
	private HashMap<String, Animation> objektAnimations;

	protected World world;

	protected Tile(Integer height, Boolean animated, Integer relativedrawX, Integer relativedrawY) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.animated = animated;

		images = new HashMap<>();
		objektAnimations = new HashMap<>();
		loadAnimation();
	}

	public abstract void loadAnimation();

	/**
	 * This method is to be called when the tile is added to a world.
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void start() {
		if (animated)
			triggerAnimation(DEFAULTANIMATION);
	}

	public boolean hasLayer(Layers layer) {
		if (layer != Layers.Objects)
			return images.get(layer) != null;
		return actualAnimation != null;
	}

	public Boolean isPassable(Integer height) {
		return height >= this.height ? true : false;
	}

	public BufferedImage getImage(Layers layer) {
		return Images.getImage(images.get(layer));
	}

	public BufferedImage getObjektImage() {
		return actualAnimation.getActualImage();
	}

	public void addObjektAnimation(Entry<String, Animation> data) {
		objektAnimations.put(data.getKey(), data.getValue());
	}

	public void triggerAnimation(String animation) {
		triggerObjektAnimation(getObjektanimation(animation));
	}

	private void triggerObjektAnimation(Animation animation) {
		if (actualAnimation != null) {
			actualAnimation.stop();
		}
		animation.start();
		actualAnimation = animation;
	}

	/**
	 * @param layers exclusive objektlayer
	 * @param path
	 */
	public void setImage(Layers layers, String path) {
		images.put(layers, path);
	}

	public Animation getObjektanimation(String identifier) {
		return objektAnimations.get(identifier);
	}

	public Point getPosition() {
		return world.getTilePoint(this);
	}

	public Integer getDrawX(Integer relativedrawX) {
		return (int) getPosition().getX() * TILEHEIGHTWIDHT + relativedrawX;
	}
	public Integer getDrawY(Integer relativedrawY) {
		return (int) getPosition().getY() * TILEHEIGHTWIDHT + relativedrawY;
	}
	public Integer getRelativedrawX() {
		return relativedrawX;
	}
	public Integer getRelativedrawY() {
		return relativedrawY;
	}

	public String getDescription() {
		return description;
	}

	public Integer getHeight() {
		return height;
	}

	public World getWorld() {
		return world;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * Override
	 * 
	 * @param entity
	 */
	public void onInteract(Entity entity) {
	}

	/**
	 * Override
	 * 
	 * @param entity
	 */
	public void onSteppedUpon(Entity entity) {
	}

	public String getName() {
		return this.getClass().getName().replace("tiles.", "");
	}

}