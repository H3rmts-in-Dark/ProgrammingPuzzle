package abstractclasses;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
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

	private Point position;
	protected Rotations rotation;
	protected World world;

	private int ticksperimagechange;

	protected Image drawimage;

	public HashMap<Rotations,HashMap<Animations,ArrayList<String>>> animations = new HashMap<>();
	public HashMap<Layers,HashMap<Animations,ArrayList<String>>> pictures = new HashMap<>();

	protected HashMap<Layers,Animations> actualanimation = new HashMap<>();
	protected HashMap<Layers,Integer> actualanimationcounter = new HashMap<>();
	protected HashMap<Layers,ChangeImageTask> tasks = new HashMap<>();

	protected Tile(Integer height,Boolean animated,int relativedrawX,int relativedrawY,Rotations rotation,
			int ticksperimagechange) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;
		this.ticksperimagechange = ticksperimagechange;

		loadAnimations();
		if (animated)
			triggerAnimation(Animations.defaultanimation);
	}

	protected Tile(Integer height,Rotations rotation) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		this.relativedrawX = 0;
		this.relativedrawY = 0;
		this.rotation = rotation;
		this.ticksperimagechange = DEFAULTTPIC;

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

	public Image getDrawimage() {
		return drawimage;
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

		BufferedImage image = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT * 2,
				BufferedImage.TYPE_4BYTE_ABGR);
		g = image.getGraphics();

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
		
		drawimage = image;
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
			tasks.put(layer,new ChangeImageTask(ticksperimagechange,this,
					animations.get(rotation).get(animation).size() - 1,Layers.Objects));
		} else {
			tasks.put(layer,
					new ChangeImageTask(ticksperimagechange,this,pictures.get(layer).get(animation).size() - 1,layer));
		}
	}
	
	
	public void delete() {
		for (ChangeImageTask task : tasks.values()) {
			task.end();
		}
	}
}
