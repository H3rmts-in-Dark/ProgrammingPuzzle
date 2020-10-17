package abstractclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;

import logic.Constants;
import world.Animation;
import world.Images;
import world.World;
import world.World.Layers;

/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das
 * Objekt sich nicht bewegen können und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private Integer height;
	private String description;

	/**
	 * exluding objektlayer
	 */
	private HashMap<World.Layers, String> images;

	private Animation actualAnimation;
	private HashMap<String, Animation> objektAnimations;

	protected World world;

	protected Tile(Integer height, Boolean animated) {
		this.height = height;
		this.description = "default description";
		this.world = null;

		images = new HashMap<>();
		objektAnimations = new HashMap<>();
		loadAnimation();
		if (animated)
			triggerAnimation(DEFAULTANIMATION);
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

	public boolean hasLayer(World.Layers layer) {
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

	public Integer getX() {
		return (int) getPosition().getX();
	}

	public Integer getY() {
		return (int) getPosition().getY();
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