package abstractclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import logic.Constants;
import tasks.ChangeImageTask;
import world.Animation;
import world.World;
import world.World.Layers;

/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das
 * Objekt sich nicht bewegen können.
 */
public abstract class Tile implements Constants {

	private Integer height;
	private String description;

	private HashMap<World.Layers, Animation> images;
	private ArrayList<Animation> objektanimations;
	private ChangeImageTask objekttask;

	protected World world;

	protected Tile(Integer height, Boolean animated) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		images = new HashMap<>();
		objektanimations = new ArrayList<>();
		if (animated)
			objekttask = new ChangeImageTask(10, this, Layers.Objects, -1);
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
		return images.get(layer) != null;
	}

	public Boolean isPassable(Integer height) {
		return height >= this.height ? true : false;
	}

	public BufferedImage getImage(Layers layer) {
		return images.get(layer).getActualImg();
	}

	public void addObjektAnimation(Animation animation) {
		objektanimations.add(animation);
	}

	public void setLayeranimation(Layers layer, Animation animation) {
		if (images.containsKey(layer))
			images.replace(layer, animation.start(this));
		else
			images.put(layer, animation.start(this));
	}

	public void nextImage(Layers layer) {
		try {
			images.get(layer).nextImage();
		} catch (NullPointerException e) {
			return;
		}
	}

	public void triggerdefaultanimation() {
		triggerObjektAnimation(getObjektanimation(defaultanimation));
	}

	public void triggerObjektAnimation(Animation animation) {
		setLayeranimation(Layers.Objects, animation);
	}

	public Animation getObjektanimation(Integer index) {
		try {
			return objektanimations.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
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

	public void remove() {
		objekttask.end();
	}

}