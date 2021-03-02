
package abstractclasses;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Height;
import Enums.Layer;
import Enums.Rotation;
import logic.Constants;
import tasks.ChangeEntityImageTask;
import tasks.MoveEntityTask;
import world.Images;
import world.World;


/**
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden, muss das
 * Objekt / Lebewesen sich bewegen k�nnen.
 */

public abstract class Entity implements Constants {

	private Height height;
	private int relativedrawX;
	private int relativedrawY;

	private Point pixelposition;
	protected Rotation rotation;

	private int ticksperimagechange;

	public HashMap<Rotation,HashMap<Animation,ArrayList<String>>> animations = new HashMap<>();

	protected Animation actualanimation = null;
	protected int actualanimationcounter = 0;
	protected ChangeEntityImageTask task = null;

	protected MoveEntityTask movetask = null;

	private World world;

	protected Entity(Point position) {
		this(position,0,0);
	}

	protected Entity(Point position,int relativedrawX,int relativedrawY) {
		this(position,relativedrawX,relativedrawY,Rotation.norotation,DEFAULTIMAGECHANGETICKDELAY);
	}

	protected Entity(Point position,int relativedrawX,int relativedrawY,Rotation rotation,int ticksperimagechange) {
		setHeight(Height.UNPASSABLE);
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		setPosition(position);
		setRotation(rotation);
		this.ticksperimagechange = ticksperimagechange;

		loadAnimations();
		triggerAnimation(Animation.deactivatedanimation);
	}

	public abstract void loadAnimations();

	public abstract void getdata(LinkedHashMap<String,String> List);

	public void setPixelPosition(Point position) {
		this.pixelposition = position;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void setHeight(Height height) {
		this.height = height;
	}

	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	public Point getPixelPosition() {
		return pixelposition;
	}

	public void setPosition(Point position) {
		this.pixelposition = new Point(position.x * DEFAULTIMAGEWIDHTHEIGHT,position.y * DEFAULTIMAGEWIDHTHEIGHT);
	}

	public Point getPosition() {
		return new Point(pixelposition.x / DEFAULTIMAGEWIDHTHEIGHT,pixelposition.y / DEFAULTIMAGEWIDHTHEIGHT);
	}

	public int getRelativedrawX() {
		return relativedrawX;
	}

	public int getRelativedrawY() {
		return relativedrawY;
	}

	public World getWorld() {
		return world;
	}

	public void nextimage(Layer layer) {
		actualanimationcounter++;
	}

	public BufferedImage getImage() {
		try {
			return Images.getImage(animations.get(rotation).get(actualanimation).get(actualanimationcounter));
		} catch (NullPointerException e) {
		}
		return null;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Height getHeight() {
		return height;
	}

	public void onInteract(Entity entity) {
	}

	public void nextimage() {
		actualanimationcounter++;
	}

	public void startmove(int ticksperimagechange,Rotation rotation) {
		if (movetask == null || movetask.getEnded())
			movetask = new MoveEntityTask(ticksperimagechange,this,rotation,true);
	}

	public void move() {
		if (movetask != null)
			while (!movetask.getEnded()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		movetask = new MoveEntityTask(ticksperimagechange,this,rotation,false);
		while (!movetask.getEnded()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	public void move(Rotation direction) {
		switch (direction) {
			case down:
				pixelposition.y++;
			break;
			case left:
				pixelposition.x--;
			break;
			case right:
				pixelposition.x++;
			break;
			case up:
				pixelposition.y--;
			break;
			case norotation:
			break;
		}
	}

	public Tile getBlock() {
		if (getPosition().x > 0)
			return world.getTile(getPosition().x - 1,getPosition().y);
		if (getPosition().y > 0)
			return world.getTile(getPosition().x,getPosition().y - 1);
		if (getPosition().x < world.getWidth() - 1)
			return world.getTile(getPosition().x + 1,getPosition().y);
		if (getPosition().y < world.getHeight() - 1)
			return world.getTile(getPosition().x,getPosition().y + 1);
		return null;
	}

	public void triggerAnimation(Animation animation) {
		actualanimation = animation;
		actualanimationcounter = 0;
		try {
			task.end();
		} catch (NullPointerException e) {
		}
		task = new ChangeEntityImageTask(ticksperimagechange,this,animations.get(rotation).get(animation).size() - 1,
			animation);
	}

}
