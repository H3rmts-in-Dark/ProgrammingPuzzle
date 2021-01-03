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
	protected HashMap<Layers,HashMap<Animations,ArrayList<String>>> pictures = new HashMap<>();

	protected HashMap<Layers,Animations> actualanimation = new HashMap<>();
	protected HashMap<Layers,Integer> actualanimationcounter = new HashMap<>();
	protected HashMap<Layers,ChangeImageTask> tasks = new HashMap<>();

	protected Rotations rotation;

	protected World world;

	private Point position;

	protected Tile(Integer height,Boolean animated,int relativedrawX,int relativedrawY,Rotations rotation) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;

		loadAnimations();
		if (animated)
			triggerAnimation(Animations.defaultanimation);
	}

	public abstract void loadAnimations();

	/**
	 * This method is to be called when the tile is added to a world.
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
		position = world.getTilePoint(this);
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

	public Point getPosition() {
		return position;
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
		return getClass().getSimpleName();
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(drawimage,(int) position.getX() * TILEHEIGHTWIDHT,(int) position.getY() * TILEHEIGHTWIDHT,null);
	}
	
	public void triggerimageupdate() {
		new Thread() {
			@Override
			public void run() {
				updateimage();
			}
		}.start();
	}

	private void updateimage() {
		Graphics g;
		try {
			g = drawimage.getGraphics();
		} catch (NullPointerException e) {
			drawimage = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT * 2,
					BufferedImage.TYPE_4BYTE_ABGR);
			g = drawimage.getGraphics();
		}
		try {
			g.drawImage(Images.getImage(pictures.get(Layers.Floor).get(Animations.noanimation).get(0)),0,
					DEFAULTIMAGEWIDHTHEIGHT,null);
		} catch (NullPointerException e) {
		}

		try {
			g.drawImage(Images.getImage(pictures.get(Layers.Cable).get(actualanimation.get(Layers.Cable))
					.get(actualanimationcounter.get(Layers.Cable))),0,DEFAULTIMAGEWIDHTHEIGHT,null);
		} catch (NullPointerException e) {
		}

		try {
			g.drawImage(
					Images.getImage(animations.get(rotation).get(actualanimation.get(Layers.Objects))
							.get(actualanimationcounter.get(Layers.Objects))),
					relativedrawX,relativedrawY + DEFAULTIMAGEWIDHTHEIGHT,null);
		} catch (NullPointerException e) {
		}

		try {
			g.drawImage(Images.getImage(pictures.get(Layers.Effects).get(actualanimation.get(Layers.Effects))
					.get(actualanimationcounter.get(Layers.Effects))),0,DEFAULTIMAGEWIDHTHEIGHT,null);
		} catch (NullPointerException e) {
		}
	}

	public static void loadAnimation(Rotations rotation,Animations animation,Tile tile,boolean obj) {
		var add = new ArrayList<String>();
		for (File file : new File("rsc/" + (obj ? "objekt" : "entity") + " pictures/" + tile.getName() + "/"
				+ Rotations.toString(rotation) + animation).listFiles()) {
			add.add(file.getPath());
		}
		if (!tile.animations.containsKey(rotation)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.animations.put(rotation,addMap);
		}
		tile.animations.get(rotation).put(animation,add);
	}

	public static void loadPicture(Layers layer,Animations animation,Tile tile,String name) {
		var add = new ArrayList<String>();
		for (File file : new File("rsc/" + Layers.toString(layer) + name + "/" + (layer == Layers.Floor ? "" : animation))
				.listFiles()) {
			add.add(file.getPath());
		}
		if (!tile.pictures.containsKey(layer)) {
			var addMap = new HashMap<Animations,ArrayList<String>>();
			tile.pictures.put(layer,addMap);
		}

		tile.pictures.get(layer).put(animation,add);
	}

	public void nextimage(Layers layer) {
		actualanimationcounter.replace(layer,actualanimationcounter.get(layer) + 1);
	}

	public void triggerAnimation(Animations animation) {
		Layers layer = Animations.getLayer(animation);
		actualanimation.put(layer,animation);
		actualanimationcounter.put(layer,0);
		try {
			tasks.get(layer).end();
		} catch (NullPointerException e) {
		}
		if (layer == Layers.Objects) {
			tasks.put(layer,new ChangeImageTask(5,this,animations.get(rotation).get(animation).size() - 1,Layers.Objects));
		} else {
			tasks.put(layer,new ChangeImageTask(5,this,pictures.get(layer).get(animation).size() - 1,layer));
		}
	}

}
