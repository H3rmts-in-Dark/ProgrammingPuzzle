package abstractclasses;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import logic.Constants;
import logic.Layers;
import logic.Rotation;
import tasks.ChangeImageTask;
import world.Animation;
import world.Images;
import world.World;


/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das Objekt sich
 * nicht bewegen k�nnen und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private int height;
	private String description;
	private int relativedrawX;
	private int relativedrawY;

	private Image drawimage;

	HashMap<String,ArrayList<String>> animations;
	String actualanimation;
	int actualpic;
	HashMap<Layers,String> pictures;

	private Rotation direction;

	protected World world;

	protected Tile(Integer height,Boolean animated,Integer relativedrawX,Integer relativedrawY,Rotation rotation) {
		this.height = height;
		this.description = "default description";
		this.world = null;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		if (animated) {
			new ChangeImageTask(5,this,-1);
		}
		this.direction = rotation;

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
		updateimage();
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

	public void addObjektAnimation(Entry<String,Animation> data) {
		objektAnimations.put(data.getKey(),data.getValue());
	}

	public void addDirectionAnimation(Rotation direction,Entry<String,Animation> data) {
		if (directionAnimations.containsKey(direction)) {
			directionAnimations.get(direction).put(data.getKey(),data.getValue());
		} else {
			HashMap<String,Animation> hm = new HashMap<>();
			directionAnimations.put(direction,hm);
			addDirectionAnimation(direction,data);
		}
	}

	public void setDirection(Rotation direction) {
		this.direction = direction;
		objektAnimations = directionAnimations.get(direction);
	}

	public Rotation getDirection() {
		return direction;
	}

	public void triggerAnimation(String animation) {
		triggerObjektAnimation(getObjektanimation(animation));
	}

	private void triggerObjektAnimation(Animation animation) {
		if (actualAnimation != null) {
			actualAnimation.stopAnimation();
		}
		animation.startAnimation();
		actualAnimation = animation;
	}

	/**
	 * @param layers exclusive objektlayer
	 * @param path
	 */
	public void setImage(Layers layers,String path) {
		images.put(layers,path);
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
		return this.getClass().getName().replace("tiles.","");
	}

	public void draw(Graphics2D g2,float zoom) {
		g2.drawImage(drawimage,(int) (getDrawX(0) * zoom),(int) (getDrawY(0) * zoom),null);
	}

	public void updateimage() {
		BufferedImage image = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = image.getGraphics();

		g.drawImage(getImage(Layers.Floor),0,0,null);
		if (hasLayer(Layers.Cable))
			g.drawImage(getImage(Layers.Cable),0,0,null);
		if (hasLayer(Layers.Objects))
			g.drawImage(getImage(Layers.Objects),0,0,null);
		if (hasLayer(Layers.Effects))
			g.drawImage(getImage(Layers.Effects),0,0,null);

		drawimage = image.getScaledInstance((int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),
				(int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),Scaler);

	}

	public static void loadAnimation(String Objekt,Rotation rotation,String name,Tile tile,boolean obj) {
		ArrayList<String> add = new ArrayList<>();
		for (String file : new File("rsc/" + (obj ? "objekt" : "entity") + " pictures/" + Objekt + "/" + Rotation.toString(rotation) + name).list()) {
			add.add(file);
		}
	}

	public void nextimage() {
		actualpic++;
	}

}
