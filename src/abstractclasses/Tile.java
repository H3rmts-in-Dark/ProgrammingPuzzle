package abstractclasses;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import logic.Constants;
import tasks.ChangeImageTask;
import world.Animation;
import world.Images;
import world.World;
import world.World.Layers;

/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das
 * Objekt sich nicht bewegen können.
 */
public abstract class Tile implements Constants {

	private Integer height;
	private String description;

	/**
	 * exluding objektlayer
	 */
	private HashMap<World.Layers, String> images;

	private Animation actualanimation;
	private HashMap<String, Animation> objektanimations;

	protected World world;

	protected Tile(Integer height, Boolean animated) {
		this.height = height;
		this.description = "default description";
		this.world = null;

		images = new HashMap<>();
		objektanimations = new HashMap<>();
		loadanimation();
		if (animated)
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

	public boolean hasLayer(World.Layers layer) {
		if (layer != Layers.Objects)
			return images.get(layer) != null;
		return actualanimation != null;
	}

	public Boolean isPassable(Integer height) {
		return height >= this.height ? true : false;
	}

	public BufferedImage getImage(Layers layer) {
		return Images.getImage(images.get(layer));
	}

	public BufferedImage getObjektImage() {
		return actualanimation.getActualImg();
	}

	public void addObjektAnimation(Animation animation, String identifier) {
		objektanimations.put(identifier, animation);
	}

	public void triggerdefaultanimation() {
		triggerObjektAnimation(getObjektanimation(defaultanimation));
	}

	public void triggerObjektAnimation(Animation animation) {
		actualanimation = animation;
		System.out.println("trigger act:" + actualanimation);
		actualanimation.start();
	}

	/**
	 * 
	 * @param layers exclusive objektlayer
	 * @param path
	 */
	public void setImage(Layers layers, String path) {
		images.put(layers, path);
	}

	public Animation getObjektanimation(String identifier) {
		return objektanimations.get(identifier);
	}

	public void setPassable(Integer height) {
		this.height = height;
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