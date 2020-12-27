package abstractclasses;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import logic.Animations;
import logic.Constants;
import logic.Layers;
import logic.Rotations;
import tasks.ChangeImageTask;
import world.Images;
import world.World;


/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das Objekt sich
 * nicht bewegen können und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private int height;
	private String description;
	private int relativedrawX;
	private int relativedrawY;

	protected Image drawimage;

	protected HashMap<Rotations,HashMap<Animations,ArrayList<String>>> animations = new HashMap<>();
	protected HashMap<Layers,Animations> actualanimation = new HashMap<>();
	protected HashMap<Layers,Integer> actualanimationcunter = new HashMap<>();

	protected HashMap<Layers,HashMap<Animations,ArrayList<String>>> pictures = new HashMap<>();

	protected Rotations rotation;

	protected World world;

	protected Tile(Integer height,Boolean animated,Integer relativedrawX,Integer relativedrawY,Rotations rotation) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;

		loadAnimations();
	}

	public abstract void loadAnimations();

	/**
	 * This method is to be called when the tile is added to a world.
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
		updateimage();
	}

	public Boolean isPassable(Integer height) {
		return height >= this.height;
	}

	public void setRotation(Rotations direction) {
		this.rotation = direction;
	}

	public Rotations getRotation() {
		return rotation;
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
		return getClass().getSimpleName();// this.getClass().getName().replace("tiles.","");
	}

	public void draw(Graphics2D g2,float zoom) {
		g2.drawImage(drawimage,(int) (getDrawX(0) * zoom),(int) (getDrawY(0) * zoom),null);
	}

	public void updateimage() {
		BufferedImage image = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = image.getGraphics();

		try {
			g.drawImage(Images.getImage(pictures.get(Layers.Floor).get(Animations.noanimation).get(0)),0,0,null);
		} catch (NullPointerException e) {
		}

		try {
			g.drawImage(Images.getImage(pictures.get(Layers.Cable).get(actualanimation.get(Layers.Cable))
					.get(actualanimationcunter.get(Layers.Cable))),0,0,null);
		} catch (NullPointerException e) {
		}

		try {
			g.drawImage(Images.getImage(animations.get(rotation).get(actualanimation.get(Layers.Objects))
					.get(actualanimationcunter.get(Layers.Objects))),0,0,null);
		} catch (NullPointerException e) {
		}

		try {
			g.drawImage(Images.getImage(pictures.get(Layers.Effects).get(actualanimation.get(Layers.Effects))
					.get(actualanimationcunter.get(Layers.Effects))),0,0,null);
		} catch (NullPointerException e) {
		}

		drawimage = image.getScaledInstance((int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),
				(int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),Scaler);

	}

	public static void loadAnimation(Rotations rotation,Animations animation,Tile tile,boolean obj) {
		var add = new ArrayList<String>();
		System.out.println("rsc/" + (obj ? "objekt" : "entity") + " pictures/" + tile.getName() + "/"
				+ Rotations.toString(rotation) + animation);
		for (String file : new File("rsc/" + (obj ? "objekt" : "entity") + " pictures/" + tile.getName() + "/"
				+ Rotations.toString(rotation) + animation).list()) {
			add.add(file);
		}
		if (!tile.animations.containsKey(rotation)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.animations.put(rotation,addMap);
		}
		tile.animations.get(rotation).put(animation,add);
	}

	public static void loadPicture(Layers layer,Animations animation,Tile tile,String name) {
		var add = new ArrayList<String>();
		for (String file : new File("rsc/" + Layers.toString(layer) + name + "/" + (layer == Layers.Floor ? "" : animation)).list()) {
			add.add(file);
		}
		if (!tile.pictures.containsKey(layer)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.pictures.put(layer,addMap);
		}
		tile.pictures.get(layer).put(animation,add);
	}

	public void nextimage(Layers layer) {
		actualanimationcunter.replace(layer,actualanimationcunter.get(layer) + 1);
	}

	public void triggerAnimation(Animations animation) {
		Layers layer = Animations.getLayer(animation);
		if (actualanimation.containsKey(layer)) {
			actualanimation.replace(layer,animation);
			actualanimationcunter.replace(layer,0);
		} else {
			actualanimation.put(layer,animation);
			actualanimationcunter.put(layer,0);
		}
		if (layer == Layers.Objects) {
			new ChangeImageTask(5,this,animations.get(rotation).get(animation).size(),Layers.Objects);
		} else {
			new ChangeImageTask(5,this,pictures.get(layer).get(animation).size(),layer);
		}
	}

}
