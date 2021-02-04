package abstractclasses;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Cabletype;
import Enums.Height;
import Enums.Layer;
import Enums.Rotation;
import Enums.Signalcolor;
import logic.Constants;
import tasks.ChangeTileImageTask;
import world.Images;
import world.World;


/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das Objekt sich
 * nicht bewegen können und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private Height height;
	private int relativedrawX;
	private int relativedrawY;

	private Point position;
	private Rotation rotation;

	private Signalcolor color;
	private boolean activated;

	private int ticksperimagechange;

	private HashMap<Rotation,HashMap<Animation,ArrayList<String>>> objektanimations = new HashMap<>();
	private HashMap<Layer,HashMap<Animation,String>> pictures = new HashMap<>();

	protected HashMap<Layer,Animation> animations = new HashMap<>();

	protected int objectanimationcounter;
	protected ChangeTileImageTask objecttask;

	protected int effectsanimationcounter;
	protected ChangeTileImageTask effectstask;

	private World world;

	protected Tile(Height height,int relativedrawX,int relativedrawY,Signalcolor signalcolor,Cabletype cabletype) {
		this(height,relativedrawX,relativedrawY,Rotation.norotation,DEFAULTIMAGECHANGETICKDELAY,signalcolor,cabletype);
	}

	protected Tile(Height height,int relativedrawX,int relativedrawY,Rotation rotation,int ticksperimagechange,
			Signalcolor signalcolor,Cabletype cabletype) {
		this.height = height;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;
		this.ticksperimagechange = ticksperimagechange;
		this.color = signalcolor;
		
		loadAnimations();
		
		if(cabletype != Cabletype.notype) {
			World.load(Layer.Cable,Animation.onanimation,this,getColor(),cabletype);
			World.load(Layer.Cable,Animation.offanimation,this,getColor(),cabletype);
		}
			
		triggerAnimation(Animation.deactivatedanimation);
		triggerAnimation(Animation.offanimation);
		triggerAnimation(Animation.noanimation);
	}

	public abstract void loadAnimations();

	public abstract void getdata(LinkedHashMap<String,String> List);

	public Boolean isPassable(Height height) {
		return Height.getint(height) >= Height.getint(this.height);
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Point getPosition() {
		return position;
	}

	public int getRelativedrawX() {
		return relativedrawX;
	}

	public int getRelativedrawY() {
		return relativedrawY;
	}

	public BufferedImage getFloorImage() {
		try {
			return Images.getImage(pictures.get(Layer.Floor).get(animations.get(Layer.Floor)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			return null;
		}
	}

	public BufferedImage getCableImage() {
		try {
			return Images.getImage(pictures.get(Layer.Cable).get(animations.get(Layer.Cable)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public BufferedImage getObjektImage() {
		try {
			return Images.getImage(
					objektanimations.get(rotation).get(animations.get(Layer.Objects)).get(objectanimationcounter));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public BufferedImage getEffectsImage() {
		try {
			return Images.getImage(pictures.get(Layer.Effects).get(animations.get(Layer.Effects)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public Rotation getRotation() {
		return rotation;
	}
	
	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
		for (Animation ani : animations.values()) {
			triggerAnimation(ani);
		}
	}

	public Height getHeight() {
		return height;
	}

	public Signalcolor getColor() {
		return color;
	}

	public HashMap<Layer,HashMap<Animation,String>> getPictures() {
		return pictures;
	}

	public HashMap<Rotation,HashMap<Animation,ArrayList<String>>> getAnimations() {
		return objektanimations;
	}

	public Integer getActualanimationcounter() {
		return objectanimationcounter;
	}

	public World getWorld() {
		return world;
	}

	public int getTicksperimagechange() {
		return ticksperimagechange;
	}

	public void onInteract(Entity entity) {
	}

	public void onSteppedUpon(Entity entity) {
	}

	public void onEntityLeft(Entity entity) {
	}

	public void onSignal(Tile caller,boolean activate) {
	}

	public void onSignalRelayer(Tile caller,boolean activate) {
		if (activate == activated)
			return;
		activated = activate;
		if (activate) {
			triggerAnimation(Animation.activatedanimation);
			triggerAnimation(Animation.onanimation);
		} else {
			triggerAnimation(Animation.deactivatedanimation);
			triggerAnimation(Animation.offanimation);
		}
		if (color != Signalcolor.nocolor) {
			if (getPosition().x > 0)
				if (world.getTile(getPosition().x - 1,getPosition().y).getColor() == color)
					world.getTile(getPosition().x - 1,getPosition().y).onSignalRelayer(this,activate);
			if (getPosition().y > 0)
				if (world.getTile(getPosition().x,getPosition().y - 1).getColor() == color)
					world.getTile(getPosition().x,getPosition().y - 1).onSignalRelayer(this,activate);
			if (getPosition().x < world.getWidth() - 1)
				if (world.getTile(getPosition().x + 1,getPosition().y).getColor() == color)
					world.getTile(getPosition().x + 1,getPosition().y).onSignalRelayer(this,activate);
			if (getPosition().y < world.getHeight() - 1)
				if (world.getTile(getPosition().x,getPosition().y + 1).getColor() == color)
					world.getTile(getPosition().x,getPosition().y + 1).onSignalRelayer(this,activate);
		}
		onSignal(caller,activate);
	}

	public boolean getActivated() {
		return activated;
	}

	public void nextimage() {
		objectanimationcounter++;
	}

	public void triggerAnimation(Animation animation) {
		animations.put(Animation.getLayer(animation),animation);
		if (Animation.getLayer(animation) == Layer.Objects) {
			if (!(objektanimations.containsKey(rotation) && objektanimations.get(rotation).containsKey(animation)))
				return;
			objectanimationcounter = 0;
			try {
				objecttask.end();
			} catch (NullPointerException e) {
			}
			objecttask = new ChangeTileImageTask(ticksperimagechange,this,
					objektanimations.get(rotation).get(animation).size() - 1,animation);
		}
	}

}
