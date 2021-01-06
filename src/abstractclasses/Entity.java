
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
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden, muss das
 * Objekt / Lebewesen sich bewegen k�nnen.
 */

public abstract class Entity implements Constants {

	public Rotations rotation;
	private Boolean interactable;
	private int height;
	private String description;
	private Point pixelposition;

	private int relativedrawX;
	private int relativedrawY;

	private World world;

	protected Image drawimage;

	public HashMap<Rotations,HashMap<Animations,ArrayList<String>>> animations = new HashMap<>();

	protected Animations actualanimation = null;
	protected int actualanimationcounter = 0;
	protected ChangeImageTask tasks = null;

	protected Entity(Boolean interactable,Point position,int relativedrawX,int relativedrawY,Rotations rotation) {
		this.interactable = interactable;
		this.rotation = rotation;
		this.pixelposition = new Point(position.x * TILEHEIGHTWIDHT,position.y * TILEHEIGHTWIDHT);
		this.description = "default description";
		this.world = null;
		this.height = FLOORHEIGHT;

		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;

		loadAnimation();

	}

	public abstract void loadAnimation();

	/**
	 * This Method is to be called when added to a world.
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
		height = UNPASSABLE;
	}

	public Boolean getInteractable() {
		return interactable;
	}

	public Point getPixelposition() {
		return pixelposition;
	}

	public Integer getRelativedrawX() {
		return relativedrawX;
	}

	public Integer getRelativedrawY() {
		return relativedrawY;
	}

	public Point getPosition() {
		return new Point(pixelposition.x / TILEHEIGHTWIDHT,pixelposition.x / TILEHEIGHTWIDHT);
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

	public void changePixelpositionY(int y) {
		pixelposition.y = y;
	}

	public void changePixelpositionX(int x) {
		pixelposition.x = x;
	}

	public abstract void onInteract(Entity entity);

	public void draw(Graphics2D g2) {
		g2.drawImage(drawimage,pixelposition.x,pixelposition.y,null);
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
		BufferedImage image = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT * 2,
				BufferedImage.TYPE_4BYTE_ABGR);

		image.getGraphics().drawImage(
				Images.getImage(animations.get(rotation).get(actualanimation).get(actualanimationcounter)),relativedrawX,
				relativedrawY + DEFAULTIMAGEWIDHTHEIGHT,null);

		drawimage = image;
	}

	public void nextimage(Layers layer) {
		actualanimationcounter++;
	}

	public void triggerAnimation(Animations animation) {
		actualanimation = animation;
		actualanimationcounter = 0;
		try {
			tasks.end();
		} catch (NullPointerException e) {
		}
		tasks = new ChangeImageTask(5,this,animations.get(rotation).get(animation).size() - 1);
	}

}
